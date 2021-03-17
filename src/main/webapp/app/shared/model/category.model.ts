import { ICourse } from 'app/shared/model/course.model';

export interface ICategory {
  id?: number;
  title?: string;
  description?: string;
  courses?: ICourse[];
}

export class Category implements ICategory {
  constructor(public id?: number, public title?: string, public description?: string, public courses?: ICourse[]) {}
}
