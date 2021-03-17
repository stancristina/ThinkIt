import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserDetailsChapter, UserDetailsChapter } from 'app/shared/model/user-details-chapter.model';
import { UserDetailsChapterService } from './user-details-chapter.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';

type SelectableEntity = IChapter | IAppUser;

@Component({
  selector: 'jhi-user-details-chapter-update',
  templateUrl: './user-details-chapter-update.component.html',
})
export class UserDetailsChapterUpdateComponent implements OnInit {
  isSaving = false;
  chapters: IChapter[] = [];
  appusers: IAppUser[] = [];

  editForm = this.fb.group({
    id: [],
    isStarted: [],
    isCompleted: [],
    chapterId: [],
    appUserId: [],
  });

  constructor(
    protected userDetailsChapterService: UserDetailsChapterService,
    protected chapterService: ChapterService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsChapter }) => {
      this.updateForm(userDetailsChapter);

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));

      this.appUserService.query().subscribe((res: HttpResponse<IAppUser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(userDetailsChapter: IUserDetailsChapter): void {
    this.editForm.patchValue({
      id: userDetailsChapter.id,
      isStarted: userDetailsChapter.isStarted,
      isCompleted: userDetailsChapter.isCompleted,
      chapterId: userDetailsChapter.chapterId,
      appUserId: userDetailsChapter.appUserId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userDetailsChapter = this.createFromForm();
    if (userDetailsChapter.id !== undefined) {
      this.subscribeToSaveResponse(this.userDetailsChapterService.update(userDetailsChapter));
    } else {
      this.subscribeToSaveResponse(this.userDetailsChapterService.create(userDetailsChapter));
    }
  }

  private createFromForm(): IUserDetailsChapter {
    return {
      ...new UserDetailsChapter(),
      id: this.editForm.get(['id'])!.value,
      isStarted: this.editForm.get(['isStarted'])!.value,
      isCompleted: this.editForm.get(['isCompleted'])!.value,
      chapterId: this.editForm.get(['chapterId'])!.value,
      appUserId: this.editForm.get(['appUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDetailsChapter>>): void {
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
