export interface IEvaluationCompleted {
  evaluationId?: number;
  grade?: number;
}

export class EvaluationCompleted implements IEvaluationCompleted {
  constructor(public evaluationId?: number, public grade?: number) {}
}
