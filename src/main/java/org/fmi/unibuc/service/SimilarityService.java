package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.SimilarityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.Similarity}.
 */
public interface SimilarityService {

    /**
     * Save a similarity.
     *
     * @param similarityDTO the entity to save.
     * @return the persisted entity.
     */
    SimilarityDTO save(SimilarityDTO similarityDTO);

    /**
     * Get all the similarities.
     *
     * @return the list of entities.
     */
    List<SimilarityDTO> findAll();


    /**
     * Get the "id" similarity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SimilarityDTO> findOne(Long id);

    /**
     * Delete the "id" similarity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
