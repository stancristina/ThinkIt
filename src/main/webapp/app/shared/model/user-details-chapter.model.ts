export interface IUserDetailsChapter {
  id?: number;
  isStarted?: boolean;
  isCompleted?: boolean;
  chapterId?: number;
  appUserId?: number;
}

export class UserDetailsChapter implements IUserDetailsChapter {
  constructor(
    public id?: number,
    public isStarted?: boolean,
    public isCompleted?: boolean,
    public chapterId?: number,
    public appUserId?: number
  ) {
    this.isStarted = this.isStarted || false;
    this.isCompleted = this.isCompleted || false;
  }
}
