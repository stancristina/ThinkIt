import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';
import { CardsModule, CarouselModule } from 'angular-bootstrap-md';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild([LIBRARY_ROUTE]), CarouselModule, CardsModule],
  declarations: [LibraryComponent],
})
export class LicentaLibraryModule {}
