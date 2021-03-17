import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDetailsChapter } from 'app/shared/model/user-details-chapter.model';

@Component({
  selector: 'jhi-user-details-chapter-detail',
  templateUrl: './user-details-chapter-detail.component.html',
})
export class UserDetailsChapterDetailComponent implements OnInit {
  userDetailsChapter: IUserDetailsChapter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsChapter }) => (this.userDetailsChapter = userDetailsChapter));
  }

  previousState(): void {
    window.history.back();
  }
}
