import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation/evaluation.service';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html',
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  evaluations: IEvaluation[] = [];

  editForm = this.fb.group({
    id: [],
    question: [],
    answerA: [],
    answerB: [],
    answerC: [],
    correctAnswer: [],
    evaluationId: [],
  });

  constructor(
    protected questionService: QuestionService,
    protected evaluationService: EvaluationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);

      this.evaluationService.query().subscribe((res: HttpResponse<IEvaluation[]>) => (this.evaluations = res.body || []));
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      question: question.question,
      answerA: question.answerA,
      answerB: question.answerB,
      answerC: question.answerC,
      correctAnswer: question.correctAnswer,
      evaluationId: question.evaluationId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      answerA: this.editForm.get(['answerA'])!.value,
      answerB: this.editForm.get(['answerB'])!.value,
      answerC: this.editForm.get(['answerC'])!.value,
      correctAnswer: this.editForm.get(['correctAnswer'])!.value,
      evaluationId: this.editForm.get(['evaluationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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

  trackById(index: number, item: IEvaluation): any {
    return item.id;
  }
}
