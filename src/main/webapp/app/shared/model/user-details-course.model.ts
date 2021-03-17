export interface IUserDetailsCourse {
  id?: number;
  isStarted?: boolean;
  isCompleted?: boolean;
  evaluationCompleted?: boolean;
  evaluationGrade?: number;
  courseId?: number;
  appUserId?: number;
}

export class UserDetailsCourse implements IUserDetailsCourse {
  constructor(
    public id?: number,
    public isStarted?: boolean,
    public isCompleted?: boolean,
    public evaluationCompleted?: boolean,
    public evaluationGrade?: number,
    public courseId?: number,
    public appUserId?: number
  ) {
    this.isStarted = this.isStarted || false;
    this.isCompleted = this.isCompleted || false;
    this.evaluationCompleted = this.evaluationCompleted || false;
  }
}
