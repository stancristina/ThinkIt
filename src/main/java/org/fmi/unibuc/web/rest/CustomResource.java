package org.fmi.unibuc.web.rest;


import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.fmi.unibuc.service.CustomService;
import org.fmi.unibuc.service.UserDetailsCourseService;
import org.fmi.unibuc.service.UserDetailsLessonService;
import org.fmi.unibuc.service.dto.*;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);

    private final CustomService customService;
    private final UserDetailsLessonService userDetailsLessonService;
    private final UserDetailsCourseService userDetailsCourseService;
    private static final String ENTITY_NAME = "evaluation";


    public CustomResource(CustomService customService, UserDetailsLessonService userDetailsLessonService, UserDetailsCourseService userDetailsCourseService) {
        this.customService = customService;
        this.userDetailsLessonService = userDetailsLessonService;
        this.userDetailsCourseService = userDetailsCourseService;
    }

    @GetMapping("/custom/question-evaluation/{evaluationId}")
    public List<QuestionDTO> getAllQuestionForEvaluation(@PathVariable Long evaluationId) {
        log.debug("REST request to get all questions for the evaluation with id {}", evaluationId);
        return customService.findAllQuestionByEvaluationId(evaluationId);
    }

    /**
     * {@code GET  /custom/lessons-chapter/:chapterId} : get all the lessons for a chapter.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lessons in body.
     */
    @GetMapping("/custom/lessons-chapter/{chapterId}")
    public List<LessonDTO> getAllLessonsForChapter(@PathVariable Long chapterId) {
        log.debug("REST request to get all Lessons for the chapter with id {}", chapterId);
        return customService.findAllLessonsByChapterId(chapterId);
    }

    /**
     * {@code GET  /custom/course-chapter/:courseId} : get all the lessons for a course.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lessons in body.
     */
    @GetMapping("/custom/course-chapter/{courseId}")
    public List<ExtendedChapterDTO> getAllChaptersForCourse(@PathVariable Long courseId) {
        log.debug("REST request to get all chapters for the course with id {}", courseId);
        return customService.findAllChaptersByCourseId(courseId);
    }


    /**
     * {@code GET  /custom/check-enroll-in-course/:courseId} : Check if user is enrolled in course.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} .
     */
    @GetMapping("/custom/check-enroll-in-course/{courseId}")
    public Boolean checkUserIsEnrolledInCourse(@PathVariable Long courseId) {
        log.debug("REST request to check if logged app user is enrolled in course with id {}", courseId);
        return customService.checkUserIsEnrolledInCourse(courseId);

    }

    /**
     * {@code GET  /custom/enroll-in-course/:courseId} : enroll user to course.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} .
     */
    @GetMapping("/custom/enroll-in-course/{courseId}")
    public Boolean enrollUserForCourse(@PathVariable Long courseId) {
        log.debug("REST request to enroll logged app user for the course with id {}", courseId);
        return customService.enrollAppUserInCourse(courseId);
    }

    /**
     * {@code GET  /currentUser-details-course} : get all the currentUserDetailsCourse.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentUserDetailsCourse in body.
     */
    @GetMapping("/custom/currentUser-details-course/{courseId}")
    public UserDetailsCourseDTO findOneByCurrentUserAndCourse(@PathVariable long courseId) {
        log.debug("REST request to get  currentUserDetailsCourse");
        return customService.findOneByCurrentUserAndCourse(courseId);
    }

    /**
     * {@code GET  /currentUser-details-chapter} : get all the currentUserDetailsChapter.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentUserDetailsCourse in body.
     */
    @GetMapping("/custom/currentUser-details-chapter/{courseId}")
    public List<UserDetailsChapterDTO> findOneByCurrentUserAndChapter(@PathVariable long courseId) {
        log.debug("REST request to get  currentUserDetailsChapter");
        List<UserDetailsChapterDTO> userDetailsChapterDTOS = customService.findAllByCurrentUserAndChapter(courseId);
        return userDetailsChapterDTOS;
    }

    /**
     * {@code GET  /currentUser-details-lesson} : get all the currentUserDetailsLesson.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentUserDetailsCourse in body.
     */
    @GetMapping("/custom/currentUser-details-lesson/{courseId}")
    public List<UserDetailsLessonDTO> findOneByCurrentUserAndLesson(@PathVariable long courseId) {
        log.debug("REST request to get  currentUserDetailsLesson");
        List<UserDetailsLessonDTO> userDetailsLessonDTOS = customService.findAllByCurrentUserAndLesson(courseId);
        return userDetailsLessonDTOS;
    }

    /**
     * {@code GET  /custom/recommended-courses} : get all the recommended courses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/custom/recommended-courses")
    public List<CourseDTO> getAllRecommendedCourses() {
        log.debug("REST request to get all recommendation courses");
        return customService.getCourseRecommendationForLoggedUser();
    }

    @PutMapping("/custom/user-completed-lesson/{lessonId}")
    public Boolean updateCompletedLesson(@PathVariable long lessonId) {
        log.debug("REST request to update completed lesson : {}", lessonId);
        return customService.updateCompletedLesson(lessonId);
    }

    /**
     * {@code POST  /custom/update-evaluation-grade} : update the evaluation grade on user course details page.
     *
     * @return the {@link boolean} with status {@code 200 (OK)}
     */
    @PostMapping("/custom/update-evaluation-grade")
    public Boolean updateEvaluationGrade(@RequestBody EvaluationCompletedDTO evaluationCompletedDTO) {
        log.debug("REST request to update the evaluation grade.");
        return customService.updateDetailsCourseEvaluation(evaluationCompletedDTO);
    }

    /**
     * {@code GET  /custom/get-course/{evaluationId} : get course by evaluation id
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/custom/get-course/{evaluationId}")
    public ResponseEntity<CourseDTO> getCourseByEvaluationId(@PathVariable long evaluationId) {
        log.debug("REST request to get  currentUserDetailsLesson");
        Optional<CourseDTO> courseDTO = customService.getCourseByEvaluationId(evaluationId);
        return ResponseUtil.wrapOrNotFound(courseDTO);
    }

}
