import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserDetailsChapter, UserDetailsChapter } from 'app/shared/model/user-details-chapter.model';
import { UserDetailsChapterService } from './user-details-chapter.service';
import { UserDetailsChapterComponent } from './user-details-chapter.component';
import { UserDetailsChapterDetailComponent } from './user-details-chapter-detail.component';
import { UserDetailsChapterUpdateComponent } from './user-details-chapter-update.component';

@Injectable({ providedIn: 'root' })
export class UserDetailsChapterResolve implements Resolve<IUserDetailsChapter> {
  constructor(private service: UserDetailsChapterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserDetailsChapter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userDetailsChapter: HttpResponse<UserDetailsChapter>) => {
          if (userDetailsChapter.body) {
            return of(userDetailsChapter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserDetailsChapter());
  }
}

export const userDetailsChapterRoute: Routes = [
  {
    path: '',
    component: UserDetailsChapterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsChapters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDetailsChapterDetailComponent,
    resolve: {
      userDetailsChapter: UserDetailsChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsChapters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserDetailsChapterUpdateComponent,
    resolve: {
      userDetailsChapter: UserDetailsChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsChapters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserDetailsChapterUpdateComponent,
    resolve: {
      userDetailsChapter: UserDetailsChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsChapters',
    },
    canActivate: [UserRouteAccessService],
  },
];
