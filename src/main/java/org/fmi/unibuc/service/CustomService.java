package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.ExtendedChapterDTO;
import org.fmi.unibuc.service.dto.LessonDTO;

import java.util.List;

public interface CustomService {

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

}
