import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ThinkItTestModule } from '../../../test.module';
import { UserDetailsChapterComponent } from 'app/entities/user-details-chapter/user-details-chapter.component';
import { UserDetailsChapterService } from 'app/entities/user-details-chapter/user-details-chapter.service';
import { UserDetailsChapter } from 'app/shared/model/user-details-chapter.model';

describe('Component Tests', () => {
  describe('UserDetailsChapter Management Component', () => {
    let comp: UserDetailsChapterComponent;
    let fixture: ComponentFixture<UserDetailsChapterComponent>;
    let service: UserDetailsChapterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [UserDetailsChapterComponent],
      })
        .overrideTemplate(UserDetailsChapterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDetailsChapterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDetailsChapterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserDetailsChapter(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userDetailsChapters && comp.userDetailsChapters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
