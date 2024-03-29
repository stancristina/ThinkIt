import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThinkItTestModule } from '../../../test.module';
import { QuestionDetailComponent } from 'app/entities/question/question-detail.component';
import { Question } from 'app/shared/model/question.model';

describe('Component Tests', () => {
  describe('Question Management Detail Component', () => {
    let comp: QuestionDetailComponent;
    let fixture: ComponentFixture<QuestionDetailComponent>;
    const route = ({ data: of({ question: new Question(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThinkItTestModule],
        declarations: [QuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load question on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.question).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
