import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

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
  isUserEnrolledToCourse: boolean | undefined;
  lessonCompletedInfo: Map<number, boolean> = new Map<number, boolean>();
  percentCompleted = 0;

  userDetailsLessons: IUserDetailsLesson[] = [];
  userDetailsChapters: IUserDetailsChapter[] = [];
  userDetailsCourse: IUserDetailsCourse | undefined;

  constructor(protected activatedRoute: ActivatedRoute, protected courseService: CourseService, private router: Router) {}

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
          this.loadAllDetailsForCurrentUser();
        }
      });
      this.courseService.checkUserIsEnrolledInCourse(this.course.id!).subscribe((res: HttpResponse<boolean>) => {
        if (res.body !== null) {
          this.isUserEnrolledToCourse = res.body;
        }
      });
    }
  }

  loadAllDetailsForCurrentUser(): void {
    if (this.course !== null) {
      this.courseService.getUserDetailCourse(this.course.id!).subscribe((res: HttpResponse<IUserDetailsCourse>) => {
        if (res.body !== null) {
          this.userDetailsCourse = res.body;
        }
      });

      this.courseService.getUserDetailChapters(this.course.id!).subscribe((res: HttpResponse<IUserDetailsChapter[]>) => {
        if (res.body !== null) {
          this.userDetailsChapters = res.body || [];
        }
      });

      this.courseService.getUserDetailLessons(this.course.id!).subscribe((res: HttpResponse<IUserDetailsLesson[]>) => {
        if (res.body !== null) {
          this.userDetailsLessons = res.body || [];
        }

        this.lessonCompletedInfo.clear();
        let lastLesson = undefined;
        let firstNotCompletedLesson = undefined;
        let completedCourses = 0;
        let totalLessons = 0;
        if (this.chapters !== undefined) {
          for (const chapter of this.chapters) {
            if (chapter.lessons !== undefined) {
              for (const lesson of chapter.lessons) {
                // search it's status in the userDetailLessons array
                let isCompleted = false;
                for (const userDetailLesson of this.userDetailsLessons) {
                  if (userDetailLesson.lessonId === lesson.id && userDetailLesson.isCompleted === true) {
                    isCompleted = true;
                  }
                }
                this.lessonCompletedInfo[lesson.id!] = isCompleted;

                if (isCompleted === true) {
                  completedCourses = completedCourses + 1;
                }
                totalLessons = totalLessons + 1;

                if (isCompleted === false && firstNotCompletedLesson === undefined) {
                  firstNotCompletedLesson = lesson;
                }

                lastLesson = lesson;
              }
            }
          }
        }

        if (firstNotCompletedLesson === undefined) {
          firstNotCompletedLesson = lastLesson;
        }

        if (firstNotCompletedLesson !== undefined) {
          this.onLessonClicked(firstNotCompletedLesson);
        }

        this.percentCompleted = totalLessons / completedCourses;
      });
    }
  }

  enrollUserToCourse(): void {
    if (this.course !== null) {
      this.courseService.enrollUserInCourse(this.course.id!).subscribe((res: HttpResponse<boolean>) => {
        const currentUrl = this.router.url;
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate([currentUrl]);
        });
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

    this.activeLesson = selectedLesson;
    // localStorage.setItem("activeLesson", JSON.stringify(selectedLesson));

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

  onLessonEnded = () => {
    // apel de updatat lessonul activeLesson
    // this.activeLesson.id
    if (this.activeLesson === undefined) {
      return;
    }

    // Faci subscribe pe acel apel, exact cum e in functia enrollUserToCourse, iar daca rezultatul este true,
    // atunci apelezi, in callback-ul ala de subscribe functia loadAllDetailsForCurrentUser ca sa sincronizezi informatia

    this.courseService.updateCompletedLesson(this.activeLesson.id!).subscribe((res: HttpResponse<boolean>) => {
      if (res.body === true) {
        this.loadAllDetailsForCurrentUser();
      }
    });
  };

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
