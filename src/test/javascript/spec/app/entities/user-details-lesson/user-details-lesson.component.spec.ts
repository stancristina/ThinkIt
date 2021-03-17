import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsLessonComponent } from 'app/entities/user-details-lesson/user-details-lesson.component';
import { UserDetailsLessonService } from 'app/entities/user-details-lesson/user-details-lesson.service';
import { UserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

describe('Component Tests', () => {
  describe('UserDetailsLesson Management Component', () => {
    let comp: UserDetailsLessonComponent;
    let fixture: ComponentFixture<UserDetailsLessonComponent>;
    let service: UserDetailsLessonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsLessonComponent],
      })
        .overrideTemplate(UserDetailsLessonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsLessonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsLessonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserDetailsLesson(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userDetailsLessons && comp.userDetailsLessons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
