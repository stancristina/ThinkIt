import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { HttpResponse } from '@angular/common/http';
import { EvaluationService } from './evaluation.service';
import { IQuestion } from '../../shared/model/question.model';
import { ICourse } from '../../shared/model/course.model';
import { EvaluationCompleted } from '../../shared/model/evaluation-completed.model';
import { ResultReason, SpeechConfig, AudioConfig, SpeechRecognizer } from 'microsoft-cognitiveservices-speech-sdk';

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
  isEvaluationAvailable = false;

  constructor(protected activatedRoute: ActivatedRoute, protected evaluationService: EvaluationService) {
    this.questions = [];
    this.userAnswers = [];
    this.isCorrectAnswer = [];
  }

  loadAllQuestions(): void {
    if (this.evaluation !== null) {
      this.evaluationService.queryQuestion(this.evaluation.id!).subscribe((res: HttpResponse<IQuestion[]>) => {
        this.questions = res.body || [];
        this.isEvaluationAvailable = this.questions.length !== 0;
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

      if (this.questions[i].questionType === 0) {
        userSelectedValue = this.getAnswerForNormalQuestion(i);
      } else {
        userSelectedValue = this.getAnswerForSTTQuestion(i);
      }

      this.userAnswers.push(userSelectedValue);
      const correctAnswer = this.questions[i].correctAnswer;
      if (this.checkIfAnswerIsCorrect(userSelectedValue, correctAnswer!, this.questions[i].questionType!)) {
        correctAnswers++;
        this.isCorrectAnswer.push(true);
      } else {
        this.isCorrectAnswer.push(false);
      }
    }

    if (this.questions.length !== 0) {
      this.grade = Number((Math.round(correctAnswers * 100) / this.questions.length).toFixed(2));
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

  checkIfAnswerIsCorrect(userAnswer: string, correctAnswer: string, questionType: Number): boolean {
    if (questionType === 0) {
      return userAnswer === correctAnswer;
    } else {
      // user answer usually has a point after it
      const userAnsNorm = userAnswer !== undefined ? userAnswer : '';
      return this.compareSpeechToTextAnswer(correctAnswer, userAnswer);
    }
  }

  compareSpeechToTextAnswer(expected: string, actual: string): boolean {
    const s1 = expected
      .toLowerCase()
      .replace(/[.,/#?!$%^&*;:{}=\-_`~()]/g, '')
      .replace(' ', '');

    const s2 = actual
      .toLowerCase()
      .replace(/[.,/#?!$%^&*;:{}=\-_`~()]/g, '')
      .replace(' ', '');

    return s1 === s2;
  }

  getAnswerForNormalQuestion(i: Number): string {
    const userAnswerA = document.getElementById('question-' + i + '-answerA') as HTMLInputElement;
    if (userAnswerA !== null && userAnswerA.checked === true) {
      return userAnswerA.value;
    }

    const userAnswerB = document.getElementById('question-' + i + '-answerB') as HTMLInputElement;
    if (userAnswerB !== null && userAnswerB.checked === true) {
      return userAnswerB.value;
    }

    const userAnswerC = document.getElementById('question-' + i + '-answerC') as HTMLInputElement;
    if (userAnswerC !== null && userAnswerC.checked === true) {
      return userAnswerC.value;
    }

    return '';
  }

  getAnswerForSTTQuestion(i: Number): string {
    const inputField = document.getElementById('question-stt-ans-' + i) as HTMLInputElement;
    return inputField.value;
  }

  sttFromMic(question: IQuestion, i: Number): void {
    this.evaluationService.getToken().subscribe(response => {
      const tokenObj = {
        authToken: response,
        region: 'eastus',
      };

      const speechConfig = SpeechConfig.fromAuthorizationToken(tokenObj.authToken, tokenObj.region);
      speechConfig.speechRecognitionLanguage = 'en-US';

      const audioConfig = AudioConfig.fromDefaultMicrophoneInput();
      const recognizer = new SpeechRecognizer(speechConfig, audioConfig);

      const inputField = document.getElementById('question-stt-ans-' + i) as HTMLInputElement;
      inputField.value = 'Listening...';

      recognizer.recognizeOnceAsync((result: { reason: ResultReason; text: any }) => {
        let displayText;
        if (result.reason === ResultReason.RecognizedSpeech) {
          displayText = `${result.text}`;
        } else {
          displayText = 'ERROR: Speech was cancelled or could not be recognized. Ensure your microphone is working properly.';
        }
        inputField.value = displayText;
      });
    });
  }
}
