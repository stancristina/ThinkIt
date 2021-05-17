import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { IQuestion } from '../../shared/model/question.model';
import { ICourse } from '../../shared/model/course.model';
import { IEvaluationCompleted } from '../../shared/model/evaluation-completed.model';

type EntityResponseType = HttpResponse<IEvaluation>;
type EntityArrayResponseType = HttpResponse<IEvaluation[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationService {
  public customEvaluationUrl = SERVER_API_URL + 'api/custom/question-evaluation';
  public resourceUrl = SERVER_API_URL + 'api/evaluations';
  public customResourceUrl = SERVER_API_URL + 'api/custom';

  constructor(protected http: HttpClient) {}

  create(evaluation: IEvaluation): Observable<EntityResponseType> {
    return this.http.post<IEvaluation>(this.resourceUrl, evaluation, { observe: 'response' });
  }

  update(evaluation: IEvaluation): Observable<EntityResponseType> {
    return this.http.put<IEvaluation>(this.resourceUrl, evaluation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryQuestion(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IQuestion[]>(`${this.customEvaluationUrl}/${id}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCourseByEvaluationId(evaluationId: number): Observable<EntityResponseType> {
    return this.http.get<ICourse>(`${this.customResourceUrl}/get-course/${evaluationId}`, { observe: 'response' });
  }

  updateEvaluationGrade(evaluationCompleted: IEvaluationCompleted): Observable<HttpResponse<Boolean>> {
    return this.http.post<Boolean>(`${this.customResourceUrl}/update-evaluation-grade`, evaluationCompleted, { observe: 'response' });
  }
}
