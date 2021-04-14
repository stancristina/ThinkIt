package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.domain.Similarity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Similarity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SimilarityRepository extends JpaRepository<Similarity, Long> {

    List<Similarity> findAllByCourseA(Course course);

}
