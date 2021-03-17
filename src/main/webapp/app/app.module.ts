import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ThinkItSharedModule } from 'app/shared/shared.module';
import { ThinkItCoreModule } from 'app/core/core.module';
import { ThinkItAppRoutingModule } from './app-routing.module';
import { ThinkItHomeModule } from './home/home.module';
import { ThinkItEntityModule } from './entities/entity.module';
import { LicentaLibraryModule } from './library/library.module';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ThinkItSharedModule,
    ThinkItCoreModule,
    ThinkItHomeModule,
    LicentaLibraryModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ThinkItEntityModule,
    ThinkItAppRoutingModule,
    MDBBootstrapModule.forRoot(),
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ThinkItAppModule {}
