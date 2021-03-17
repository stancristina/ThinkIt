import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserDetailsChapter } from 'app/shared/model/user-details-chapter.model';
import { UserDetailsChapterService } from './user-details-chapter.service';

@Component({
  templateUrl: './user-details-chapter-delete-dialog.component.html',
})
export class UserDetailsChapterDeleteDialogComponent {
  userDetailsChapter?: IUserDetailsChapter;

  constructor(
    protected userDetailsChapterService: UserDetailsChapterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userDetailsChapterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userDetailsChapterListModification');
      this.activeModal.close();
    });
  }
}
