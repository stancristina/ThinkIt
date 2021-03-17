import { ILesson } from 'app/shared/model/lesson.model';

export interface IChapter {
  id?: number;
  title?: string;
  description?: string;
  orderNr?: number;
  xp?: number;
  lessons?: ILesson[];
  courseId?: number;
}

export class Chapter implements IChapter {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public orderNr?: number,
    public xp?: number,
    public lessons?: ILesson[],
    public courseId?: number
  ) {}
}
