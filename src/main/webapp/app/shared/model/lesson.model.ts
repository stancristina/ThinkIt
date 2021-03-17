export interface ILesson {
  id?: number;
  title?: string;
  order?: number;
  url?: string;
  chapterId?: number;
}

export class Lesson implements ILesson {
  constructor(public id?: number, public title?: string, public order?: number, public url?: string, public chapterId?: number) {}
}
