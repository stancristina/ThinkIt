import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { LessonUpdateComponent } from 'app/entities/lesson/lesson-update.component';
import { LessonService } from 'app/entities/lesson/lesson.service';
import { Lesson } from 'app/shared/model/lesson.model';

describe('Component Tests', () => {
  describe('Lesson Management Update Component', () => {
    let comp: LessonUpdateComponent;
    let fixture: ComponentFixture<LessonUpdateComponent>;
    let service: LessonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [LessonUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LessonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LessonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LessonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lesson(123);
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
        const entity = new Lesson();
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
