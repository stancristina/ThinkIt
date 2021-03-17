import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation/evaluation.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

type SelectableEntity = IEvaluation | ICategory;

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html',
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;
  evaluations: IEvaluation[] = [];
  categories: ICategory[] = [];
  releasedDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    difficulty: [],
    rating: [],
    released: [],
    thumbnailUrl: [],
    evaluationId: [],
    categoryId: [],
  });

  constructor(
    protected courseService: CourseService,
    protected evaluationService: EvaluationService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);

      this.evaluationService
        .query({ filter: 'course-is-null' })
        .pipe(
          map((res: HttpResponse<IEvaluation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEvaluation[]) => {
          if (!course.evaluationId) {
            this.evaluations = resBody;
          } else {
            this.evaluationService
              .find(course.evaluationId)
              .pipe(
                map((subRes: HttpResponse<IEvaluation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEvaluation[]) => (this.evaluations = concatRes));
          }
        });

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      title: course.title,
      description: course.description,
      difficulty: course.difficulty,
      rating: course.rating,
      released: course.released,
      thumbnailUrl: course.thumbnailUrl,
      evaluationId: course.evaluationId,
      categoryId: course.categoryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      difficulty: this.editForm.get(['difficulty'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      released: this.editForm.get(['released'])!.value,
      thumbnailUrl: this.editForm.get(['thumbnailUrl'])!.value,
      evaluationId: this.editForm.get(['evaluationId'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
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
