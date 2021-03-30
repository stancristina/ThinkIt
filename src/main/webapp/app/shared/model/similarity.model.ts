export interface ISimilarity {
  id?: number;
  value?: number;
  courseAId?: number;
  courseBId?: number;
}

export class Similarity implements ISimilarity {
  constructor(public id?: number, public value?: number, public courseAId?: number, public courseBId?: number) {}
}
