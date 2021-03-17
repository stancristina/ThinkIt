import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILesson } from 'app/shared/model/lesson.model';
import { LessonService } from './lesson.service';
import { LessonDeleteDialogComponent } from './lesson-delete-dialog.component';

@Component({
  selector: 'jhi-lesson',
  templateUrl: './lesson.component.html',
})
export class LessonComponent implements OnInit, OnDestroy {
  lessons?: ILesson[];
  eventSubscriber?: Subscription;

  constructor(protected lessonService: LessonService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lessonService.query().subscribe((res: HttpResponse<ILesson[]>) => (this.lessons = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLessons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILesson): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLessons(): void {
    this.eventSubscriber = this.eventManager.subscribe('lessonListModification', () => this.loadAll());
  }

  delete(lesson: ILesson): void {
    const modalRef = this.modalService.open(LessonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lesson = lesson;
  }
}
