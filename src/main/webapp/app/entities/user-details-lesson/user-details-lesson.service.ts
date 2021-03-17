import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

type EntityResponseType = HttpResponse<IUserDetailsLesson>;
type EntityArrayResponseType = HttpResponse<IUserDetailsLesson[]>;

@Injectable({ providedIn: 'root' })
export class UserDetailsLessonService {
  public resourceUrl = SERVER_API_URL + 'api/user-details-lessons';

  constructor(protected http: HttpClient) {}

  create(userDetailsLesson: IUserDetailsLesson): Observable<EntityResponseType> {
    return this.http.post<IUserDetailsLesson>(this.resourceUrl, userDetailsLesson, { observe: 'response' });
  }

  update(userDetailsLesson: IUserDetailsLesson): Observable<EntityResponseType> {
    return this.http.put<IUserDetailsLesson>(this.resourceUrl, userDetailsLesson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserDetailsLesson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserDetailsLesson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
