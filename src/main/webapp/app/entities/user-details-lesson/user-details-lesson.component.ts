import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDetailsLesson } from 'app/shared/model/user-details-lesson.model';
import { UserDetailsLessonService } from './user-details-lesson.service';
import { UserDetailsLessonDeleteDialogComponent } from './user-details-lesson-delete-dialog.component';

@Component({
  selector: 'jhi-user-details-lesson',
  templateUrl: './user-details-lesson.component.html',
})
export class UserDetailsLessonComponent implements OnInit, OnDestroy {
  userDetailsLessons?: IUserDetailsLesson[];
  eventSubscriber?: Subscription;

  constructor(
    protected userDetailsLessonService: UserDetailsLessonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userDetailsLessonService
      .query()
      .subscribe((res: HttpResponse<IUserDetailsLesson[]>) => (this.userDetailsLessons = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserDetailsLessons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserDetailsLesson): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserDetailsLessons(): void {
    this.eventSubscriber = this.eventManager.subscribe('userDetailsLessonListModification', () => this.loadAll());
  }

  delete(userDetailsLesson: IUserDetailsLesson): void {
    const modalRef = this.modalService.open(UserDetailsLessonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userDetailsLesson = userDetailsLesson;
  }
}
