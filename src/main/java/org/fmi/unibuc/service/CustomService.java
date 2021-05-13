package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.*;

import java.util.List;

public interface CustomService {

    /**
     * Get all the questions for an evaluation.
     *
     * @return the list of entities.
     */
    List<QuestionDTO> findAllQuestionByEvaluationId(long evaluationId);

    /**
     * Get all the lessons for a chapter.
     *
     * @return the list of entities.
     */
    List<LessonDTO> findAllLessonsByChapterId(long chapterId);

    /**
     * Get all the cha[ters for a course.
     *
     * @return the list of entities.
     */
    List<ExtendedChapterDTO> findAllChaptersByCourseId(long courseId);

    Boolean checkUserIsEnrolledInCourse(long courseId);

    Boolean enrollAppUserInCourse(long courseId);

    List<CourseDTO> getCourseRecommendationForLoggedUser();

    /**
     * Get all the currentUserDetailsCourse.
     *
     * @return the list of entities.
     */
    UserDetailsCourseDTO findOneByCurrentUserAndCourse(long courseId);

    /**
     * Get the currentUserDetailsChapter.
     *
     * @return the list of entities.
     */
    List<UserDetailsChapterDTO> findAllByCurrentUserAndChapter(long courseId);


    /**
     * Get all the currentUserDetailsLessons.
     *
     * @return the list of entities.
     */
    List<UserDetailsLessonDTO> findAllByCurrentUserAndLesson(long courseId);

    Boolean updateCompletedLesson(long lessonId);

}
