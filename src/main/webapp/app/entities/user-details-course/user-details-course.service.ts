import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserDetailsCourse } from 'app/shared/model/user-details-course.model';

type EntityResponseType = HttpResponse<IUserDetailsCourse>;
type EntityArrayResponseType = HttpResponse<IUserDetailsCourse[]>;

@Injectable({ providedIn: 'root' })
export class UserDetailsCourseService {
  public resourceUrl = SERVER_API_URL + 'api/user-details-courses';

  constructor(protected http: HttpClient) {}

  create(userDetailsCourse: IUserDetailsCourse): Observable<EntityResponseType> {
    return this.http.post<IUserDetailsCourse>(this.resourceUrl, userDetailsCourse, { observe: 'response' });
  }

  update(userDetailsCourse: IUserDetailsCourse): Observable<EntityResponseType> {
    return this.http.put<IUserDetailsCourse>(this.resourceUrl, userDetailsCourse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserDetailsCourse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserDetailsCourse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
