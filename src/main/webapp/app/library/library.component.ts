import { Component, OnInit } from '@angular/core';
import { Course, ICourse } from 'app/shared/model/course.model';
import { HttpResponse } from '@angular/common/http';
import { CourseService } from 'app/entities/course/course.service';
import { Category, ICategory } from 'app/shared/model/category.model';

@Component({
  selector: 'jhi-library',
  templateUrl: './library.component.html',
  styleUrls: ['library.component.scss'],
})
export class LibraryComponent implements OnInit {
  courses: ICourse[];
  coursesFiltered: ICourse[];
  coursesPerCategoryMap: Object;
  categories: ICategory[];

  dataset = ['MDB', 'Angular', 'Bootstrap', 'Framework', 'SPA', 'React', 'Vue'];
  searchText: string | undefined;

  suggestedCoursesAsSlides: ICourse[][];

  COURSES_PER_SLIDE = 6;

  constructor(protected courseService: CourseService) {
    this.courses = [];
    this.coursesFiltered = [];
    this.coursesPerCategoryMap = {};
    this.categories = [];
    this.suggestedCoursesAsSlides = [];
  }

  loadAll(): void {
    this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => {
      this.courses = res.body || [];
      this.coursesFiltered = this.courses;
      this.splitCoursesIntoSlides();
    });
  }

  loadAllSuggestedCourses(): void {
    this.courseService.queryRecommendedCourses().subscribe((res: HttpResponse<ICourse[]>) => {
      const suggestedCourses = res.body || [];

      this.suggestedCoursesAsSlides = this.chunk(suggestedCourses, this.COURSES_PER_SLIDE);
    });
  }

  filterCourses(): void {
    if (this.searchText === undefined || this.searchText === '') {
      this.coursesFiltered = this.courses;
    }

    const filterMap = {
      title: this.searchText,
      description: this.searchText,
      categoryTitle: this.searchText,
      chapters: this.searchText,
    };

    const filterKeys = Object.keys(filterMap);

    this.coursesFiltered = this.courses.filter(item => {
      return filterKeys.some(keyName => {
        if (filterMap[keyName] === '') {
          return true;
        }
        return new RegExp(filterMap[keyName], 'gi').test(item[keyName]);
      });
    });

    this.splitCoursesIntoSlides();
  }

  ngOnInit(): void {
    this.loadAll();
    this.loadAllSuggestedCourses();
  }

  splitCoursesIntoSlides(): void {
    this.coursesPerCategoryMap = {};
    this.categories = [];

    for (let it = 0; it < this.coursesFiltered.length; it++) {
      const _currentCourse = this.coursesFiltered[it];
      if (_currentCourse.categoryId === undefined) {
        continue;
      }

      if (this.coursesPerCategoryMap[_currentCourse.categoryId] === undefined) {
        this.coursesPerCategoryMap[_currentCourse.categoryId] = [];
        const _newCategory = new Category();
        _newCategory.id = _currentCourse.categoryId;
        _newCategory.title = _currentCourse.categoryTitle;
        this.categories.push(_newCategory);
      }

      this.coursesPerCategoryMap[_currentCourse.categoryId].push(_currentCourse);
    }

    for (let it = 0; it < this.categories.length; it++) {
      const _id = this.categories[it].id;
      if (_id !== undefined) {
        const coursesArray = this.coursesPerCategoryMap[_id];
        this.coursesPerCategoryMap[_id] = this.chunk(coursesArray, this.COURSES_PER_SLIDE);
      }
    }
  }

  chunk(arr: any, chunkSize: any): ICourse[][] {
    const R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }
}
