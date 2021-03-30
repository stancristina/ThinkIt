package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.Similarity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Similarity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SimilarityRepository extends JpaRepository<Similarity, Long> {
}
