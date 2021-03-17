import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDetailsCourse } from 'app/shared/model/user-details-course.model';

@Component({
  selector: 'jhi-user-details-course-detail',
  templateUrl: './user-details-course-detail.component.html',
})
export class UserDetailsCourseDetailComponent implements OnInit {
  userDetailsCourse: IUserDetailsCourse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDetailsCourse }) => (this.userDetailsCourse = userDetailsCourse));
  }

  previousState(): void {
    window.history.back();
  }
}
