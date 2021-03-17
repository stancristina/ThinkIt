import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsLessonDetailComponent } from 'app/entities/user-details-lesson/user-details-lesson-detail.component';
import { UserDetailsLesson } from 'app/shared/model/user-details-lesson.model';

describe('Component Tests', () => {
  describe('UserDetailsLesson Management Detail Component', () => {
    let comp: UserDetailsLessonDetailComponent;
    let fixture: ComponentFixture<UserDetailsLessonDetailComponent>;
    const route = ({ data: of({ userDetailsLesson: new UserDetailsLesson(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsLessonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserDetailsLessonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserDetailsLessonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userDetailsLesson on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userDetailsLesson).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
