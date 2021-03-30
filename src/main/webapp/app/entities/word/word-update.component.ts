import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWord, Word } from 'app/shared/model/word.model';
import { WordService } from './word.service';

@Component({
  selector: 'jhi-word-update',
  templateUrl: './word-update.component.html',
})
export class WordUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    value: [],
    frequency: [],
  });

  constructor(protected wordService: WordService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ word }) => {
      this.updateForm(word);
    });
  }

  updateForm(word: IWord): void {
    this.editForm.patchValue({
      id: word.id,
      value: word.value,
      frequency: word.frequency,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const word = this.createFromForm();
    if (word.id !== undefined) {
      this.subscribeToSaveResponse(this.wordService.update(word));
    } else {
      this.subscribeToSaveResponse(this.wordService.create(word));
    }
  }

  private createFromForm(): IWord {
    return {
      ...new Word(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      frequency: this.editForm.get(['frequency'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWord>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
