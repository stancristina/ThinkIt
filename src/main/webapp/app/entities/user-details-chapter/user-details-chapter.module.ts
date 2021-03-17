import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { UserDetailsChapterComponent } from './user-details-chapter.component';
import { UserDetailsChapterDetailComponent } from './user-details-chapter-detail.component';
import { UserDetailsChapterUpdateComponent } from './user-details-chapter-update.component';
import { UserDetailsChapterDeleteDialogComponent } from './user-details-chapter-delete-dialog.component';
import { userDetailsChapterRoute } from './user-details-chapter.route';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild(userDetailsChapterRoute)],
  declarations: [
    UserDetailsChapterComponent,
    UserDetailsChapterDetailComponent,
    UserDetailsChapterUpdateComponent,
    UserDetailsChapterDeleteDialogComponent,
  ],
  entryComponents: [UserDetailsChapterDeleteDialogComponent],
})
export class ThinkItUserDetailsChapterModule {}
