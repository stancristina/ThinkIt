import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISimilarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';

@Component({
  templateUrl: './similarity-delete-dialog.component.html',
})
export class SimilarityDeleteDialogComponent {
  similarity?: ISimilarity;

  constructor(
    protected similarityService: SimilarityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.similarityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('similarityListModification');
      this.activeModal.close();
    });
  }
}
