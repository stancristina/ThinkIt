import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISimilarity } from 'app/shared/model/similarity.model';

@Component({
  selector: 'jhi-similarity-detail',
  templateUrl: './similarity-detail.component.html',
})
export class SimilarityDetailComponent implements OnInit {
  similarity: ISimilarity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ similarity }) => (this.similarity = similarity));
  }

  previousState(): void {
    window.history.back();
  }
}
