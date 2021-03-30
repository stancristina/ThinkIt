package org.fmi.unibuc.web.rest;


import org.fmi.unibuc.service.ChapterService;
import org.fmi.unibuc.service.CustomService;
import org.fmi.unibuc.service.LessonService;
import org.fmi.unibuc.service.dto.ChapterDTO;
import org.fmi.unibuc.service.dto.ExtendedChapterDTO;
import org.fmi.unibuc.service.dto.LessonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);

    private final CustomService customService;

    public CustomResource(CustomService customService) {
        this.customService = customService;
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




}
