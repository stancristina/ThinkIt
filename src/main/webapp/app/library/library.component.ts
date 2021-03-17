import { Component, OnInit } from '@angular/core';
import { ICourse } from 'app/shared/model/course.model';
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
  coursesPerCategoryMap: Object;
  categories: ICategory[];

  COURSES_PER_SLIDE = 4;

  constructor(protected courseService: CourseService) {
    this.courses = [];
    this.coursesPerCategoryMap = {};
    this.categories = [];
  }

  loadAll(): void {
    this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => {
      this.courses = res.body || [];
      this.coursesPerCategoryMap = {};

      for (let it = 0; it < this.courses.length; it++) {
        const _currentCourse = this.courses[it];
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
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  chunk(arr: any, chunkSize: any): ICourse[][] {
    const R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }
}
