import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourse } from 'app/shared/model/course.model';
import { IChapter } from '../../shared/model/chapter.model';
import { HttpResponse } from '@angular/common/http';
import { CourseService } from './course.service';

@Component({
  selector: 'jhi-course-detail',
  templateUrl: './course-detail.component.html',
})
export class CourseDetailComponent implements OnInit {
  course: ICourse | null = null;
  chapters?: IChapter[];

  constructor(protected activatedRoute: ActivatedRoute, protected courseService: CourseService) {}

  loadAllChapters(): void {
    if (this.course !== null) {
      this.courseService.queryChapter(this.course.id!).subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.course = course;
      this.loadAllChapters();
    });
  }

  previousState(): void {
    window.history.back();
  }
}
