import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ThinkItSharedModule } from 'app/shared/shared.module';
import { ThinkItCoreModule } from 'app/core/core.module';
import { ThinkItAppRoutingModule } from './app-routing.module';
import { ThinkItHomeModule } from './home/home.module';
import { ThinkItEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ThinkItSharedModule,
    ThinkItCoreModule,
    ThinkItHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ThinkItEntityModule,
    ThinkItAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ThinkItAppModule {}
