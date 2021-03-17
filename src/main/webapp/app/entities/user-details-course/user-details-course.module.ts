import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { UserDetailsCourseComponent } from './user-details-course.component';
import { UserDetailsCourseDetailComponent } from './user-details-course-detail.component';
import { UserDetailsCourseUpdateComponent } from './user-details-course-update.component';
import { UserDetailsCourseDeleteDialogComponent } from './user-details-course-delete-dialog.component';
import { userDetailsCourseRoute } from './user-details-course.route';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild(userDetailsCourseRoute)],
  declarations: [
    UserDetailsCourseComponent,
    UserDetailsCourseDetailComponent,
    UserDetailsCourseUpdateComponent,
    UserDetailsCourseDeleteDialogComponent,
  ],
  entryComponents: [UserDetailsCourseDeleteDialogComponent],
})
export class ThinkItUserDetailsCourseModule {}
