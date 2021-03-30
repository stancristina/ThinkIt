import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ThinkItTestModule } from '../../../test.module';
import { SimilarityComponent } from 'app/entities/similarity/similarity.component';
import { SimilarityService } from 'app/entities/similarity/similarity.service';
import { Similarity } from 'app/shared/model/similarity.model';

describe('Component Tests', () => {
  describe('Similarity Management Component', () => {
    let comp: SimilarityComponent;
    let fixture: ComponentFixture<SimilarityComponent>;
    let service: SimilarityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [SimilarityComponent],
      })
        .overrideTemplate(SimilarityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SimilarityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SimilarityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Similarity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.similarities && comp.similarities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
