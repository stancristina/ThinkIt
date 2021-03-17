import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChapter } from 'app/shared/model/chapter.model';

type EntityResponseType = HttpResponse<IChapter>;
type EntityArrayResponseType = HttpResponse<IChapter[]>;

@Injectable({ providedIn: 'root' })
export class ChapterService {
  public resourceUrl = SERVER_API_URL + 'api/chapters';

  constructor(protected http: HttpClient) {}

  create(chapter: IChapter): Observable<EntityResponseType> {
    return this.http.post<IChapter>(this.resourceUrl, chapter, { observe: 'response' });
  }

  update(chapter: IChapter): Observable<EntityResponseType> {
    return this.http.put<IChapter>(this.resourceUrl, chapter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChapter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChapter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
