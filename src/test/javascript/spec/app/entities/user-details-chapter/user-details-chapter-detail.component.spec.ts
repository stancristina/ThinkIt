import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsChapterDetailComponent } from 'app/entities/user-details-chapter/user-details-chapter-detail.component';
import { UserDetailsChapter } from 'app/shared/model/user-details-chapter.model';

describe('Component Tests', () => {
  describe('UserDetailsChapter Management Detail Component', () => {
    let comp: UserDetailsChapterDetailComponent;
    let fixture: ComponentFixture<UserDetailsChapterDetailComponent>;
    const route = ({ data: of({ userDetailsChapter: new UserDetailsChapter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsChapterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserDetailsChapterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserDetailsChapterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userDetailsChapter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userDetailsChapter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
