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

  checkIfAnswerIsCorrect(userAnswer: string, correctAnswer: string, questionType: Number): boolean {
    if (questionType === 0) {
      return userAnswer === correctAnswer;
    } else {
      const levDist = this.levenshteinDistance(userAnswer, correctAnswer);
      return levDist > 0.8;
    }
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
    const tokenObj = {
      authToken: '2761b90fec274c3db557e07448a942ab',
      region: 'eastus',
    };

    const speechConfig = SpeechConfig.fromAuthorizationToken(tokenObj.authToken, tokenObj.region);
    speechConfig.speechRecognitionLanguage = 'en-US';

    const audioConfig = AudioConfig.fromDefaultMicrophoneInput();
    const recognizer = new SpeechRecognizer(speechConfig, audioConfig);

    const inputField = document.getElementById('question-stt-ans-' + i) as HTMLInputElement;

    navigator.mediaDevices
      .getUserMedia({ audio: true })
      .then(function (stream): void {
        // console.log('You let me use your mic!')
        recognizer.recognizeOnceAsync((result: { reason: ResultReason; text: any }) => {
          let displayText;
          if (result.reason === ResultReason.RecognizedSpeech) {
            displayText = `${result.text}`;
          } else {
            displayText = 'ERROR: Speech was cancelled or could not be recognized. Ensure your microphone is working properly.';
          }
          inputField.value = displayText;
        });
      })
      .catch(function (err): void {
        // console.log('No mic for you!')
      });
  }

  levenshteinDistance(str1 = '', str2 = ''): Number {
    const track = Array(str2.length + 1)
      .fill(null)
      .map(() => Array(str1.length + 1).fill(null));
    for (let i = 0; i <= str1.length; i += 1) {
      track[0][i] = i;
    }
    for (let j = 0; j <= str2.length; j += 1) {
      track[j][0] = j;
    }
    for (let j = 1; j <= str2.length; j += 1) {
      for (let i = 1; i <= str1.length; i += 1) {
        const indicator = str1[i - 1] === str2[j - 1] ? 0 : 1;
        track[j][i] = Math.min(
          track[j][i - 1] + 1, // deletion
          track[j - 1][i] + 1, // insertion
          track[j - 1][i - 1] + indicator // substitution
        );
      }
    }
    return track[str2.length][str1.length];
  }
}
