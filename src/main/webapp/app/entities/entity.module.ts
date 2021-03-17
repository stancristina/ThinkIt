import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.ThinkItCategoryModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.ThinkItCourseModule),
      },
      {
        path: 'chapter',
        loadChildren: () => import('./chapter/chapter.module').then(m => m.ThinkItChapterModule),
      },
      {
        path: 'lesson',
        loadChildren: () => import('./lesson/lesson.module').then(m => m.ThinkItLessonModule),
      },
      {
        path: 'evaluation',
        loadChildren: () => import('./evaluation/evaluation.module').then(m => m.ThinkItEvaluationModule),
      },
      {
        path: 'user-details-lesson',
        loadChildren: () => import('./user-details-lesson/user-details-lesson.module').then(m => m.ThinkItUserDetailsLessonModule),
      },
      {
        path: 'user-details-chapter',
        loadChildren: () => import('./user-details-chapter/user-details-chapter.module').then(m => m.ThinkItUserDetailsChapterModule),
      },
      {
        path: 'user-details-course',
        loadChildren: () => import('./user-details-course/user-details-course.module').then(m => m.ThinkItUserDetailsCourseModule),
      },
      {
        path: 'app-user',
        loadChildren: () => import('./app-user/app-user.module').then(m => m.ThinkItAppUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ThinkItEntityModule {}
