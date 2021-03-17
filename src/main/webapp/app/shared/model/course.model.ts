import { Moment } from 'moment';
import { IChapter } from 'app/shared/model/chapter.model';
import { CourseDifficulty } from 'app/shared/model/enumerations/course-difficulty.model';

export interface ICourse {
  id?: number;
  title?: string;
  description?: string;
  difficulty?: CourseDifficulty;
  rating?: number;
  released?: Moment;
  thumbnailUrl?: string;
  evaluationId?: number;
  chapters?: IChapter[];
  categoryId?: number;
  categoryTitle?: string;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public difficulty?: CourseDifficulty,
    public rating?: number,
    public released?: Moment,
    public thumbnailUrl?: string,
    public evaluationId?: number,
    public chapters?: IChapter[],
    public categoryId?: number,
    public categoryTitle?: string
  ) {}
}
