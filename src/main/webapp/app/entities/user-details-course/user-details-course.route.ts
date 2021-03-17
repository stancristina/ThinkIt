import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserDetailsCourse, UserDetailsCourse } from 'app/shared/model/user-details-course.model';
import { UserDetailsCourseService } from './user-details-course.service';
import { UserDetailsCourseComponent } from './user-details-course.component';
import { UserDetailsCourseDetailComponent } from './user-details-course-detail.component';
import { UserDetailsCourseUpdateComponent } from './user-details-course-update.component';

@Injectable({ providedIn: 'root' })
export class UserDetailsCourseResolve implements Resolve<IUserDetailsCourse> {
  constructor(private service: UserDetailsCourseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserDetailsCourse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userDetailsCourse: HttpResponse<UserDetailsCourse>) => {
          if (userDetailsCourse.body) {
            return of(userDetailsCourse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserDetailsCourse());
  }
}

export const userDetailsCourseRoute: Routes = [
  {
    path: '',
    component: UserDetailsCourseComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsCourses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDetailsCourseDetailComponent,
    resolve: {
      userDetailsCourse: UserDetailsCourseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsCourses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserDetailsCourseUpdateComponent,
    resolve: {
      userDetailsCourse: UserDetailsCourseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsCourses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserDetailsCourseUpdateComponent,
    resolve: {
      userDetailsCourse: UserDetailsCourseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserDetailsCourses',
    },
    canActivate: [UserRouteAccessService],
  },
];
