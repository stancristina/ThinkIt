import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDetailsCourse } from 'app/shared/model/user-details-course.model';
import { UserDetailsCourseService } from './user-details-course.service';
import { UserDetailsCourseDeleteDialogComponent } from './user-details-course-delete-dialog.component';

@Component({
  selector: 'jhi-user-details-course',
  templateUrl: './user-details-course.component.html',
})
export class UserDetailsCourseComponent implements OnInit, OnDestroy {
  userDetailsCourses?: IUserDetailsCourse[];
  eventSubscriber?: Subscription;

  constructor(
    protected userDetailsCourseService: UserDetailsCourseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userDetailsCourseService
      .query()
      .subscribe((res: HttpResponse<IUserDetailsCourse[]>) => (this.userDetailsCourses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserDetailsCourses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserDetailsCourse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserDetailsCourses(): void {
    this.eventSubscriber = this.eventManager.subscribe('userDetailsCourseListModification', () => this.loadAll());
  }

  delete(userDetailsCourse: IUserDetailsCourse): void {
    const modalRef = this.modalService.open(UserDetailsCourseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userDetailsCourse = userDetailsCourse;
  }
}
