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
import { IUserDetailsCourse } from 'app/shared/model/user-details-course.model';
import { IUserDetailsChapter } from 'app/shared/model/user-details-chapter.model';
import { IUserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

type EntityResponseType = HttpResponse<ICourse>;
type EntityArrayResponseType = HttpResponse<ICourse[]>;

@Injectable({ providedIn: 'root' })
export class CourseService {
  public resourceUrl = SERVER_API_URL + 'api/courses';
  public customResourceUrl = SERVER_API_URL + 'api/custom/course-chapter';
  public customBaseResourceUrl = SERVER_API_URL + 'api/custom';
  public recommendedCoursesUrl = SERVER_API_URL + 'api/custom/recommended-courses';
  public updateCompletedLessonUrl = SERVER_API_URL + 'api/custom/user-completed-lesson';

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

  updateCompletedLesson(lessonId: number): Observable<HttpResponse<boolean>> {
    return this.http.put<boolean>(`${this.updateCompletedLessonUrl}/${lessonId}`, {}, { observe: 'response' });
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

  queryRecommendedCourses(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICourse[]>(this.recommendedCoursesUrl, { params: options, observe: 'response' })
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

  checkUserIsEnrolledInCourse(courseId: number): Observable<HttpResponse<boolean>> {
    return this.http.get<boolean>(`${this.customBaseResourceUrl}/check-enroll-in-course/${courseId}`, { observe: 'response' });
  }

  enrollUserInCourse(courseId: number): Observable<HttpResponse<boolean>> {
    return this.http.get<boolean>(`${this.customBaseResourceUrl}/enroll-in-course/${courseId}`, { observe: 'response' });
  }

  getUserDetailCourse(courseId: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserDetailsCourse>(`${this.customBaseResourceUrl}/currentUser-details-course/${courseId}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  getUserDetailChapters(courseId: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IUserDetailsChapter[]>(`${this.customBaseResourceUrl}/currentUser-details-chapter/${courseId}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  getUserDetailLessons(courseId: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IUserDetailsLesson[]>(`${this.customBaseResourceUrl}/currentUser-details-lesson/${courseId}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
}
