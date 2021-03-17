import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { UserDetailsLessonComponent } from './user-details-lesson.component';
import { UserDetailsLessonDetailComponent } from './user-details-lesson-detail.component';
import { UserDetailsLessonUpdateComponent } from './user-details-lesson-update.component';
import { UserDetailsLessonDeleteDialogComponent } from './user-details-lesson-delete-dialog.component';
import { userDetailsLessonRoute } from './user-details-lesson.route';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild(userDetailsLessonRoute)],
  declarations: [
    UserDetailsLessonComponent,
    UserDetailsLessonDetailComponent,
    UserDetailsLessonUpdateComponent,
    UserDetailsLessonDeleteDialogComponent,
  ],
  entryComponents: [UserDetailsLessonDeleteDialogComponent],
})
export class ThinkItUserDetailsLessonModule {}
