import { IQuestion } from 'app/shared/model/question.model';

export interface IEvaluation {
  id?: number;
  questions?: IQuestion[];
  courseId?: number;
}

export class Evaluation implements IEvaluation {
  constructor(public id?: number, public questions?: IQuestion[], public courseId?: number) {}
}
