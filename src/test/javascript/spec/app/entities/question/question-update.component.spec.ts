import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { QuestionUpdateComponent } from 'app/entities/question/question-update.component';
import { QuestionService } from 'app/entities/question/question.service';
import { Question } from 'app/shared/model/question.model';

describe('Component Tests', () => {
  describe('Question Management Update Component', () => {
    let comp: QuestionUpdateComponent;
    let fixture: ComponentFixture<QuestionUpdateComponent>;
    let service: QuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [QuestionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Question(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Question();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
