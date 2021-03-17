import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICourse } from 'app/shared/model/course.model';
import { IChapter } from 'app/shared/model/chapter.model';

type EntityResponseType = HttpResponse<ICourse>;
type EntityArrayResponseType = HttpResponse<ICourse[]>;

@Injectable({ providedIn: 'root' })
export class CourseService {
  public resourceUrl = SERVER_API_URL + 'api/courses';
  public customResourceUrl = SERVER_API_URL + 'api/custom/course-chapter';

  constructor(protected http: HttpClient) {}

  create(course: ICourse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(course);
    return this.http
      .post<ICourse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(course: ICourse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(course);
    return this.http
      .put<ICourse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICourse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  queryChapter(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IChapter[]>(`${this.customResourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICourse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(course: ICourse): ICourse {
    const copy: ICourse = Object.assign({}, course, {
      released: course.released && course.released.isValid() ? course.released.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.released = res.body.released ? moment(res.body.released) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((course: ICourse) => {
        course.released = course.released ? moment(course.released) : undefined;
      });
    }
    return res;
  }
}
