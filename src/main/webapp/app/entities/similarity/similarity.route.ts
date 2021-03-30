import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISimilarity, Similarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';
import { SimilarityComponent } from './similarity.component';
import { SimilarityDetailComponent } from './similarity-detail.component';
import { SimilarityUpdateComponent } from './similarity-update.component';

@Injectable({ providedIn: 'root' })
export class SimilarityResolve implements Resolve<ISimilarity> {
  constructor(private service: SimilarityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISimilarity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((similarity: HttpResponse<Similarity>) => {
          if (similarity.body) {
            return of(similarity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Similarity());
  }
}

export const similarityRoute: Routes = [
  {
    path: '',
    component: SimilarityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Similarities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SimilarityDetailComponent,
    resolve: {
      similarity: SimilarityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Similarities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SimilarityUpdateComponent,
    resolve: {
      similarity: SimilarityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Similarities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SimilarityUpdateComponent,
    resolve: {
      similarity: SimilarityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Similarities',
    },
    canActivate: [UserRouteAccessService],
  },
];
