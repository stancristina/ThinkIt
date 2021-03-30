import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISimilarity } from 'app/shared/model/similarity.model';

type EntityResponseType = HttpResponse<ISimilarity>;
type EntityArrayResponseType = HttpResponse<ISimilarity[]>;

@Injectable({ providedIn: 'root' })
export class SimilarityService {
  public resourceUrl = SERVER_API_URL + 'api/similarities';

  constructor(protected http: HttpClient) {}

  create(similarity: ISimilarity): Observable<EntityResponseType> {
    return this.http.post<ISimilarity>(this.resourceUrl, similarity, { observe: 'response' });
  }

  update(similarity: ISimilarity): Observable<EntityResponseType> {
    return this.http.put<ISimilarity>(this.resourceUrl, similarity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISimilarity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISimilarity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
