import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILesson } from 'app/shared/model/lesson.model';

type EntityResponseType = HttpResponse<ILesson>;
type EntityArrayResponseType = HttpResponse<ILesson[]>;

@Injectable({ providedIn: 'root' })
export class LessonService {
  public resourceUrl = SERVER_API_URL + 'api/lessons';

  constructor(protected http: HttpClient) {}

  create(lesson: ILesson): Observable<EntityResponseType> {
    return this.http.post<ILesson>(this.resourceUrl, lesson, { observe: 'response' });
  }

  update(lesson: ILesson): Observable<EntityResponseType> {
    return this.http.put<ILesson>(this.resourceUrl, lesson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILesson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILesson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
