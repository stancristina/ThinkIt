import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserDetailsLesson, UserDetailsLesson } from 'app/shared/model/user-details-lesson.model';
import { UserDetailsLessonService } from './user-details-lesson.service';
import { ILesson } from 'app/shared/model/lesson.model';
import { LessonService } from 'app/entities/lesson/lesson.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';

type SelectableEntity = ILesson | IAppUser;

@Component({
  selector: 'jhi-user-details-lesson-update',
  templateUrl: './user-details-lesson-update.component.html',
})
export class UserDetailsLessonUpdateComponent implements OnInit {
  isSaving = false;
  lessons: ILesson[] = [];
  appusers: IAppUser[] = [];

  editForm = this.fb.group({
    id: [],
    isStarted: [],
    isCompleted: [],
    lessonId: [],
    appUserId: [],
  });

  constructor(
    protected userDetailsLessonService: UserDetailsLessonService,
    protected lessonService: LessonService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsLesson }) => {
      this.updateForm(userDetailsLesson);

      this.lessonService.query().subscribe((res: HttpResponse<ILesson[]>) => (this.lessons = res.body || []));

      this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(userDetailsLesson: IUserDetailsLesson): void {
    this.editForm.patchValue({
      id: userDetailsLesson.id,
      isStarted: userDetailsLesson.isStarted,
      isCompleted: userDetailsLesson.isCompleted,
      lessonId: userDetailsLesson.lessonId,
      appUserId: userDetailsLesson.appUserId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userDetailsLesson = this.createFromForm();
    if (userDetailsLesson.id !== undefined) {
      this.subscribeToSaveResponse(this.userDetailsLessonService.update(userDetailsLesson));
    } else {
      this.subscribeToSaveResponse(this.userDetailsLessonService.create(userDetailsLesson));
    }
  }

  private createFromForm(): IUserDetailsLesson {
    return {
      ...new UserDetailsLesson(),
      id: this.editForm.get(['id'])!.value,
      isStarted: this.editForm.get(['isStarted'])!.value,
      isCompleted: this.editForm.get(['isCompleted'])!.value,
      lessonId: this.editForm.get(['lessonId'])!.value,
      appUserId: this.editForm.get(['appUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDetailsLesson>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
