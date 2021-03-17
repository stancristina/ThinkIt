export interface IEvaluation {
  id?: number;
  courseId?: number;
}

export class Evaluation implements IEvaluation {
  constructor(public id?: number, public courseId?: number) {}
}
