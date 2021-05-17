import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { HttpResponse } from '@angular/common/http';
import { EvaluationService } from './evaluation.service';
import { IQuestion } from '../../shared/model/question.model';
import { ICourse } from '../../shared/model/course.model';
import { EvaluationCompleted } from '../../shared/model/evaluation-completed.model';

@Component({
  selector: 'jhi-evaluation-detail',
  templateUrl: './evaluation-detail.component.html',
})
export class EvaluationDetailComponent implements OnInit {
  evaluation: IEvaluation | null = null;
  questions: IQuestion[];
  testStarted = false;
  userAnswers: String[];
  isCorrectAnswer: boolean[];
  grade = 0;
  isTestFinished = false;
  courseId = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected evaluationService: EvaluationService) {
    this.questions = [];
    this.userAnswers = [];
    this.isCorrectAnswer = [];
  }

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
    this.activatedRoute.data.subscribe(({ evaluation }) => {
      this.evaluation = evaluation;

      this.evaluationService.getCourseByEvaluationId(this.evaluation!.id!).subscribe((res: HttpResponse<ICourse>) => {
        this.courseId = res.body?.id!;
      });
    });
    this.loadAllQuestions();
  }

  previousState(): void {
    window.history.back();
  }

  finishTest(): void {
    this.userAnswers = [];
    let correctAnswers = 0;
    for (let i = 0; i < this.questions.length; i++) {
      let userSelectedValue = '';
      const userAnswerA = document.getElementById('question-' + i + '-answerA') as HTMLInputElement;
      if (userAnswerA !== null && userAnswerA.checked === true) {
        userSelectedValue = userAnswerA.value;
      }

      const userAnswerB = document.getElementById('question-' + i + '-answerB') as HTMLInputElement;
      if (userAnswerB !== null && userAnswerB.checked === true) {
        userSelectedValue = userAnswerB.value;
      }

      const userAnswerC = document.getElementById('question-' + i + '-answerC') as HTMLInputElement;
      if (userAnswerC !== null && userAnswerC.checked === true) {
        userSelectedValue = userAnswerC.value;
      }

      this.userAnswers.push(userSelectedValue);
      const correctAnswer = this.questions[i].correctAnswer;
      if (correctAnswer === userSelectedValue) {
        correctAnswers++;
        this.isCorrectAnswer.push(true);
      } else {
        this.isCorrectAnswer.push(false);
      }
    }

    if (this.questions.length !== 0) {
      this.grade = (correctAnswers * 100) / this.questions.length;
    } else {
      this.grade = 100;
    }

    this.isTestFinished = true;

    const evaluationCompleted = new EvaluationCompleted();
    evaluationCompleted.evaluationId = this.evaluation!.id;
    evaluationCompleted.grade = this.grade;
    this.evaluationService.updateEvaluationGrade(evaluationCompleted).subscribe((res: HttpResponse<Boolean>) => {
      // nothing to do here
    });
  }
}
