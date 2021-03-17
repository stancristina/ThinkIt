import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsCourseComponent } from 'app/entities/user-details-course/user-details-course.component';
import { UserDetailsCourseService } from 'app/entities/user-details-course/user-details-course.service';
import { UserDetailsCourse } from 'app/shared/model/user-details-course.model';

describe('Component Tests', () => {
  describe('UserDetailsCourse Management Component', () => {
    let comp: UserDetailsCourseComponent;
    let fixture: ComponentFixture<UserDetailsCourseComponent>;
    let service: UserDetailsCourseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsCourseComponent],
      })
        .overrideTemplate(UserDetailsCourseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsCourseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsCourseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserDetailsCourse(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userDetailsCourses && comp.userDetailsCourses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
