package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.EvaluationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.Evaluation}.
 */
public interface EvaluationService {

    /**
     * Save a evaluation.
     *
     * @param evaluationDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationDTO save(EvaluationDTO evaluationDTO);

    /**
     * Get all the evaluations.
     *
     * @return the list of entities.
     */
    List<EvaluationDTO> findAll();
    /**
     * Get all the EvaluationDTO where Course is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EvaluationDTO> findAllWhereCourseIsNull();

    /**
     * Get the "id" evaluation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationDTO> findOne(Long id);

    /**
     * Delete the "id" evaluation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
