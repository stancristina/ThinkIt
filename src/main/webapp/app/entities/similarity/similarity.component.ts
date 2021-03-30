import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISimilarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';
import { SimilarityDeleteDialogComponent } from './similarity-delete-dialog.component';

@Component({
  selector: 'jhi-similarity',
  templateUrl: './similarity.component.html',
})
export class SimilarityComponent implements OnInit, OnDestroy {
  similarities?: ISimilarity[];
  eventSubscriber?: Subscription;

  constructor(protected similarityService: SimilarityService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.similarityService.query().subscribe((res: HttpResponse<ISimilarity[]>) => (this.similarities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSimilarities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISimilarity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSimilarities(): void {
    this.eventSubscriber = this.eventManager.subscribe('similarityListModification', () => this.loadAll());
  }

  delete(similarity: ISimilarity): void {
    const modalRef = this.modalService.open(SimilarityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.similarity = similarity;
  }
}
