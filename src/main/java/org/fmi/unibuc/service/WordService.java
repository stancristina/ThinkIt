package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.WordDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.Word}.
 */
public interface WordService {

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save.
     * @return the persisted entity.
     */
    WordDTO save(WordDTO wordDTO);

    /**
     * Get all the words.
     *
     * @return the list of entities.
     */
    List<WordDTO> findAll();


    /**
     * Get the "id" word.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WordDTO> findOne(Long id);

    /**
     * Delete the "id" word.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
