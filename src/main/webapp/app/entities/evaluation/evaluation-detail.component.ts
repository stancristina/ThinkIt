import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { HttpResponse } from '@angular/common/http';
import { IChapter } from '../../shared/model/chapter.model';
import { EvaluationService } from './evaluation.service';
import { IQuestion } from '../../shared/model/question.model';

@Component({
  selector: 'jhi-evaluation-detail',
  templateUrl: './evaluation-detail.component.html',
})
export class EvaluationDetailComponent implements OnInit {
  evaluation: IEvaluation | null = null;
  questions?: IQuestion[];
  testStarted = false;

  constructor(protected activatedRoute: ActivatedRoute, protected evaluationService: EvaluationService) {}

  loadAllQuestions(): void {
    if (this.evaluation !== null) {
      this.evaluationService.queryQuestion(this.evaluation.id!).subscribe((res: HttpResponse<IQuestion[]>) => {
        this.questions = res.body || [];
      });
    }
  }

  setTestStarted(): void {
    this.testStarted = true;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluation }) => (this.evaluation = evaluation));
    this.loadAllQuestions();
  }

  previousState(): void {
    window.history.back();
  }
}
