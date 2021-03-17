import { Route } from '@angular/router';

import { LibraryComponent } from './library.component';

export const LIBRARY_ROUTE: Route = {
  path: 'library',
  component: LibraryComponent,
  data: {
    authorities: [],
    pageTitle: 'library.title',
  },
};
