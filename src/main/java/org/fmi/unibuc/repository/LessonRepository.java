package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.Category;
import org.fmi.unibuc.domain.Chapter;
import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.domain.Lesson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Lesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByChapterOrderByOrderAsc(Chapter chapter);

}
