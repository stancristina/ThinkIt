export interface IUserDetailsLesson {
  id?: number;
  isStarted?: boolean;
  isCompleted?: boolean;
  lessonId?: number;
  appUserId?: number;
}

export class UserDetailsLesson implements IUserDetailsLesson {
  constructor(
    public id?: number,
    public isStarted?: boolean,
    public isCompleted?: boolean,
    public lessonId?: number,
    public appUserId?: number
  ) {
    this.isStarted = this.isStarted || false;
    this.isCompleted = this.isCompleted || false;
  }
}
