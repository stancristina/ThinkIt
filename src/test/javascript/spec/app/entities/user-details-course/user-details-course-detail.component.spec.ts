import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsCourseDetailComponent } from 'app/entities/user-details-course/user-details-course-detail.component';
import { UserDetailsCourse } from 'app/shared/model/user-details-course.model';

describe('Component Tests', () => {
  describe('UserDetailsCourse Management Detail Component', () => {
    let comp: UserDetailsCourseDetailComponent;
    let fixture: ComponentFixture<UserDetailsCourseDetailComponent>;
    const route = ({ data: of({ userDetailsCourse: new UserDetailsCourse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsCourseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserDetailsCourseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserDetailsCourseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userDetailsCourse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userDetailsCourse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
