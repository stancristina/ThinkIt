import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILesson, Lesson } from 'app/shared/model/lesson.model';
import { LessonService } from './lesson.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';

@Component({
  selector: 'jhi-lesson-update',
  templateUrl: './lesson-update.component.html',
})
export class LessonUpdateComponent implements OnInit {
  isSaving = false;
  chapters: IChapter[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    order: [],
    url: [],
    chapterId: [],
  });

  constructor(
    protected lessonService: LessonService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lesson }) => {
      this.updateForm(lesson);

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    });
  }

  updateForm(lesson: ILesson): void {
    this.editForm.patchValue({
      id: lesson.id,
      title: lesson.title,
      order: lesson.order,
      url: lesson.url,
      chapterId: lesson.chapterId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lesson = this.createFromForm();
    if (lesson.id !== undefined) {
      this.subscribeToSaveResponse(this.lessonService.update(lesson));
    } else {
      this.subscribeToSaveResponse(this.lessonService.create(lesson));
    }
  }

  private createFromForm(): ILesson {
    return {
      ...new Lesson(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      order: this.editForm.get(['order'])!.value,
      url: this.editForm.get(['url'])!.value,
      chapterId: this.editForm.get(['chapterId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILesson>>): void {
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

  trackById(index: number, item: IChapter): any {
    return item.id;
  }
}
