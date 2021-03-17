import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserDetailsCourseService } from 'app/entities/user-details-course/user-details-course.service';
import { IUserDetailsCourse, UserDetailsCourse } from 'app/shared/model/user-details-course.model';

describe('Service Tests', () => {
  describe('UserDetailsCourse Service', () => {
    let injector: TestBed;
    let service: UserDetailsCourseService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserDetailsCourse;
    let expectedResult: IUserDetailsCourse | IUserDetailsCourse[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserDetailsCourseService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserDetailsCourse(0, false, false, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserDetailsCourse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserDetailsCourse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserDetailsCourse', () => {
        const returnedFromService = Object.assign(
          {
            isStarted: true,
            isCompleted: true,
            evaluationCompleted: true,
            evaluationGrade: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserDetailsCourse', () => {
        const returnedFromService = Object.assign(
          {
            isStarted: true,
            isCompleted: true,
            evaluationCompleted: true,
            evaluationGrade: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserDetailsCourse', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
