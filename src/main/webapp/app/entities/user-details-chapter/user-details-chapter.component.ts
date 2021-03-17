import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDetailsChapter } from 'app/shared/model/user-details-chapter.model';
import { UserDetailsChapterService } from './user-details-chapter.service';
import { UserDetailsChapterDeleteDialogComponent } from './user-details-chapter-delete-dialog.component';

@Component({
  selector: 'jhi-user-details-chapter',
  templateUrl: './user-details-chapter.component.html',
})
export class UserDetailsChapterComponent implements OnInit, OnDestroy {
  userDetailsChapters?: IUserDetailsChapter[];
  eventSubscriber?: Subscription;

  constructor(
    protected userDetailsChapterService: UserDetailsChapterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userDetailsChapterService
      .query()
      .subscribe((res: HttpResponse<IUserDetailsChapter[]>) => (this.userDetailsChapters = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserDetailsChapters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserDetailsChapter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserDetailsChapters(): void {
    this.eventSubscriber = this.eventManager.subscribe('userDetailsChapterListModification', () => this.loadAll());
  }

  delete(userDetailsChapter: IUserDetailsChapter): void {
    const modalRef = this.modalService.open(UserDetailsChapterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userDetailsChapter = userDetailsChapter;
  }
}
