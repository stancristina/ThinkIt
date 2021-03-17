import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserDetailsChapter } from 'app/shared/model/user-details-chapter.model';

type EntityResponseType = HttpResponse<IUserDetailsChapter>;
type EntityArrayResponseType = HttpResponse<IUserDetailsChapter[]>;

@Injectable({ providedIn: 'root' })
export class UserDetailsChapterService {
  public resourceUrl = SERVER_API_URL + 'api/user-details-chapters';

  constructor(protected http: HttpClient) {}

  create(userDetailsChapter: IUserDetailsChapter): Observable<EntityResponseType> {
    return this.http.post<IUserDetailsChapter>(this.resourceUrl, userDetailsChapter, { observe: 'response' });
  }

  update(userDetailsChapter: IUserDetailsChapter): Observable<EntityResponseType> {
    return this.http.put<IUserDetailsChapter>(this.resourceUrl, userDetailsChapter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserDetailsChapter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserDetailsChapter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
