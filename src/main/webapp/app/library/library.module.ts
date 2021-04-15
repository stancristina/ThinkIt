import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThinkItSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';
import { CardsModule, CarouselModule, MDBBootstrapModule } from 'angular-bootstrap-md';
import { FilterPipe } from 'app/filter.pipe';

@NgModule({
  imports: [ThinkItSharedModule, RouterModule.forChild([LIBRARY_ROUTE]), CarouselModule, CardsModule, MDBBootstrapModule],
  declarations: [LibraryComponent, FilterPipe],
})
export class LicentaLibraryModule {}
