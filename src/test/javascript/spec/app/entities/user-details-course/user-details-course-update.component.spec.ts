import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsCourseUpdateComponent } from 'app/entities/user-details-course/user-details-course-update.component';
import { UserDetailsCourseService } from 'app/entities/user-details-course/user-details-course.service';
import { UserDetailsCourse } from 'app/shared/model/user-details-course.model';

describe('Component Tests', () => {
  describe('UserDetailsCourse Management Update Component', () => {
    let comp: UserDetailsCourseUpdateComponent;
    let fixture: ComponentFixture<UserDetailsCourseUpdateComponent>;
    let service: UserDetailsCourseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsCourseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserDetailsCourseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsCourseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsCourseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserDetailsCourse(123);
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
        const entity = new UserDetailsCourse();
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
