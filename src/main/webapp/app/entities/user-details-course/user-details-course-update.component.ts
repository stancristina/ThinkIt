import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserDetailsCourse, UserDetailsCourse } from 'app/shared/model/user-details-course.model';
import { UserDetailsCourseService } from './user-details-course.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';

type SelectableEntity = ICourse | IAppUser;

@Component({
  selector: 'jhi-user-details-course-update',
  templateUrl: './user-details-course-update.component.html',
})
export class UserDetailsCourseUpdateComponent implements OnInit {
  isSaving = false;
  courses: ICourse[] = [];
  appusers: IAppUser[] = [];

  editForm = this.fb.group({
    id: [],
    isStarted: [],
    isCompleted: [],
    evaluationCompleted: [],
    evaluationGrade: [],
    courseId: [],
    appUserId: [],
  });

  constructor(
    protected userDetailsCourseService: UserDetailsCourseService,
    protected courseService: CourseService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsCourse }) => {
      this.updateForm(userDetailsCourse);

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));

      this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(userDetailsCourse: IUserDetailsCourse): void {
    this.editForm.patchValue({
      id: userDetailsCourse.id,
      isStarted: userDetailsCourse.isStarted,
      isCompleted: userDetailsCourse.isCompleted,
      evaluationCompleted: userDetailsCourse.evaluationCompleted,
      evaluationGrade: userDetailsCourse.evaluationGrade,
      courseId: userDetailsCourse.courseId,
      appUserId: userDetailsCourse.appUserId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userDetailsCourse = this.createFromForm();
    if (userDetailsCourse.id !== undefined) {
      this.subscribeToSaveResponse(this.userDetailsCourseService.update(userDetailsCourse));
    } else {
      this.subscribeToSaveResponse(this.userDetailsCourseService.create(userDetailsCourse));
    }
  }

  private createFromForm(): IUserDetailsCourse {
    return {
      ...new UserDetailsCourse(),
      id: this.editForm.get(['id'])!.value,
      isStarted: this.editForm.get(['isStarted'])!.value,
      isCompleted: this.editForm.get(['isCompleted'])!.value,
      evaluationCompleted: this.editForm.get(['evaluationCompleted'])!.value,
      evaluationGrade: this.editForm.get(['evaluationGrade'])!.value,
      courseId: this.editForm.get(['courseId'])!.value,
      appUserId: this.editForm.get(['appUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDetailsCourse>>): void {
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
