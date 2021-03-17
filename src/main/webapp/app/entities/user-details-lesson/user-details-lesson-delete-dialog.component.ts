import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserDetailsLesson } from 'app/shared/model/user-details-lesson.model';
import { UserDetailsLessonService } from './user-details-lesson.service';

@Component({
  templateUrl: './user-details-lesson-delete-dialog.component.html',
})
export class UserDetailsLessonDeleteDialogComponent {
  userDetailsLesson?: IUserDetailsLesson;

  constructor(
    protected userDetailsLessonService: UserDetailsLessonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userDetailsLessonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userDetailsLessonListModification');
      this.activeModal.close();
    });
  }
}
