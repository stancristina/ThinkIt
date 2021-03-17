package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.Chapter;

import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.domain.Lesson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Chapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByCourseOrderByOrderNrAsc(Course course);
}
