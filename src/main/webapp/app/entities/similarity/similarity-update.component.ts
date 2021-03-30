import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISimilarity, Similarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';

@Component({
  selector: 'jhi-similarity-update',
  templateUrl: './similarity-update.component.html',
})
export class SimilarityUpdateComponent implements OnInit {
  isSaving = false;
  courses: ICourse[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    courseAId: [],
    courseBId: [],
  });

  constructor(
    protected similarityService: SimilarityService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ similarity }) => {
      this.updateForm(similarity);

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));
    });
  }

  updateForm(similarity: ISimilarity): void {
    this.editForm.patchValue({
      id: similarity.id,
      value: similarity.value,
      courseAId: similarity.courseAId,
      courseBId: similarity.courseBId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const similarity = this.createFromForm();
    if (similarity.id !== undefined) {
      this.subscribeToSaveResponse(this.similarityService.update(similarity));
    } else {
      this.subscribeToSaveResponse(this.similarityService.create(similarity));
    }
  }

  private createFromForm(): ISimilarity {
    return {
      ...new Similarity(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      courseAId: this.editForm.get(['courseAId'])!.value,
      courseBId: this.editForm.get(['courseBId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISimilarity>>): void {
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

  trackById(index: number, item: ICourse): any {
    return item.id;
  }
}
