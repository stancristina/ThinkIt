import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { SimilarityComponent } from './similarity.component';
import { SimilarityDetailComponent } from './similarity-detail.component';
import { SimilarityUpdateComponent } from './similarity-update.component';
import { SimilarityDeleteDialogComponent } from './similarity-delete-dialog.component';
import { similarityRoute } from './similarity.route';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild(similarityRoute)],
  declarations: [SimilarityComponent, SimilarityDetailComponent, SimilarityUpdateComponent, SimilarityDeleteDialogComponent],
  entryComponents: [SimilarityDeleteDialogComponent],
})
export class ThinkItSimilarityModule {}
