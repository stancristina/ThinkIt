import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsChapterUpdateComponent } from 'app/entities/user-details-chapter/user-details-chapter-update.component';
import { UserDetailsChapterService } from 'app/entities/user-details-chapter/user-details-chapter.service';
import { UserDetailsChapter } from 'app/shared/model/user-details-chapter.model';

describe('Component Tests', () => {
  describe('UserDetailsChapter Management Update Component', () => {
    let comp: UserDetailsChapterUpdateComponent;
    let fixture: ComponentFixture<UserDetailsChapterUpdateComponent>;
    let service: UserDetailsChapterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsChapterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserDetailsChapterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsChapterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsChapterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserDetailsChapter(123);
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
        const entity = new UserDetailsChapter();
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
