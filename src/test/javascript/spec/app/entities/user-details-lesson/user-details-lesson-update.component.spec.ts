import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsLessonUpdateComponent } from 'app/entities/user-details-lesson/user-details-lesson-update.component';
import { UserDetailsLessonService } from 'app/entities/user-details-lesson/user-details-lesson.service';
import { UserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

describe('Component Tests', () => {
  describe('UserDetailsLesson Management Update Component', () => {
    let comp: UserDetailsLessonUpdateComponent;
    let fixture: ComponentFixture<UserDetailsLessonUpdateComponent>;
    let service: UserDetailsLessonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsLessonUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserDetailsLessonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsLessonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsLessonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserDetailsLesson(123);
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
        const entity = new UserDetailsLesson();
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
