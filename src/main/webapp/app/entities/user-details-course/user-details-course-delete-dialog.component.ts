import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserDetailsCourse } from 'app/shared/model/user-details-course.model';
import { UserDetailsCourseService } from './user-details-course.service';

@Component({
  templateUrl: './user-details-course-delete-dialog.component.html',
})
export class UserDetailsCourseDeleteDialogComponent {
  userDetailsCourse?: IUserDetailsCourse;

  constructor(
    protected userDetailsCourseService: UserDetailsCourseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userDetailsCourseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userDetailsCourseListModification');
      this.activeModal.close();
    });
  }
}
