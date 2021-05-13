export interface IQuestion {
  id?: number;
  question?: string;
  answerA?: string;
  answerB?: string;
  answerC?: string;
  correctAnswer?: string;
  evaluationId?: number;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string,
    public answerA?: string,
    public answerB?: string,
    public answerC?: string,
    public correctAnswer?: string,
    public evaluationId?: number
  ) {}
}
