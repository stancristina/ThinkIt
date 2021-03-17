import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

@Component({
  selector: 'jhi-user-details-lesson-detail',
  templateUrl: './user-details-lesson-detail.component.html',
})
export class UserDetailsLessonDetailComponent implements OnInit {
  userDetailsLesson: IUserDetailsLesson | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsLesson }) => (this.userDetailsLesson = userDetailsLesson));
  }

  previousState(): void {
    window.history.back();
  }
}
