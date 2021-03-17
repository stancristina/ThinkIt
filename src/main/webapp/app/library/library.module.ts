import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LicentaSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';
import { CardsModule, CarouselModule } from 'angular-bootstrap-md';

@NgModule({
  imports: [LicentaSharedModule, RouterModule.forChild([LIBRARY_ROUTE]), CarouselModule, CardsModule],
  declarations: [LibraryComponent],
})
export class LicentaLibraryModule {}
