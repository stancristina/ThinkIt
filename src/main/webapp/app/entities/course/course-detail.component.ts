import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourse } from 'app/shared/model/course.model';
import { IChapter } from '../../shared/model/chapter.model';
import { HttpResponse } from '@angular/common/http';
import { CourseService } from './course.service';
import { ILesson } from '../../shared/model/lesson.model';
import { IUserDetailsLesson } from '../../shared/model/user-details-lesson.model';
import { IUserDetailsChapter } from '../../shared/model/user-details-chapter.model';
import { IUserDetailsCourse } from '../../shared/model/user-details-course.model';

@Component({
  selector: 'jhi-course-detail',
  templateUrl: './course-detail.component.html',
})
export class CourseDetailComponent implements OnInit {
  course: ICourse | null = null;
  chapters?: IChapter[];
  activeIds: string[] = [];
  activeLesson: ILesson | undefined;
  activeChapter: IChapter | undefined;

  userDetailsLessons: IUserDetailsLesson[] | undefined;
  userDetailsChapters: IUserDetailsChapter[] | undefined;
  userDetailsCourse: IUserDetailsCourse | undefined;

  constructor(protected activatedRoute: ActivatedRoute, protected courseService: CourseService) {}

  loadAllChapters(): void {
    if (this.course !== null) {
      this.courseService.queryChapter(this.course.id!).subscribe((res: HttpResponse<IChapter[]>) => {
        this.chapters = res.body || [];
        if (
          this.chapters.length > 0 &&
          this.chapters[0] !== undefined &&
          this.chapters[0].lessons !== undefined &&
          this.chapters[0].lessons.length > 0
        ) {
          this.activeIds = ['panel-1'];
          this.onLessonClicked(this.chapters[0].lessons[0]);
        }
      });
    }
  }

  onLessonClicked(selectedLesson: ILesson): void {
    if (selectedLesson === null || selectedLesson.url === undefined) {
      return;
    }
    const mainVideoContainer = document.getElementById('main-video-container') as HTMLVideoElement;

    mainVideoContainer.innerHTML =
      "<source src='../../../content/videos/" + selectedLesson.url + "' type='video/mp4'> Error, no video loaded";
    mainVideoContainer.load();
    mainVideoContainer.play();

    this.activeLesson = selectedLesson;
    for (let i = 0; i < this.chapters!.length; i++) {
      if (this.chapters![i] !== undefined && this.chapters![i].id === this.activeLesson.chapterId) {
        this.activeChapter = this.chapters![i];
      }
    }

    mainVideoContainer.addEventListener('ended', this.onLessonEnded);
  }

  sliceLessonTitle(lessonTitle: String): String {
    let toReturn = lessonTitle.slice(0, 40);
    if (lessonTitle.length > 40) {
      toReturn += '...';
    }
    return toReturn;
  }

  onLessonEnded(): any {
    alert('The lesson has been ended');
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.course = course;
      this.loadAllChapters();
    });
  }

  onChapterClicked(e: Event): void {
    const clickedIcon = e.target;
  }

  previousState(): void {
    window.history.back();
  }
}
