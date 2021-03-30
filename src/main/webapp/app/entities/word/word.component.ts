import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWord } from 'app/shared/model/word.model';
import { WordService } from './word.service';
import { WordDeleteDialogComponent } from './word-delete-dialog.component';

@Component({
  selector: 'jhi-word',
  templateUrl: './word.component.html',
})
export class WordComponent implements OnInit, OnDestroy {
  words?: IWord[];
  eventSubscriber?: Subscription;

  constructor(protected wordService: WordService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.wordService.query().subscribe((res: HttpResponse<IWord[]>) => (this.words = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWords(): void {
    this.eventSubscriber = this.eventManager.subscribe('wordListModification', () => this.loadAll());
  }

  delete(word: IWord): void {
    const modalRef = this.modalService.open(WordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.word = word;
  }
}
