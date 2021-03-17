package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.UserDetailsLessonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.UserDetailsLesson}.
 */
public interface UserDetailsLessonService {

    /**
     * Save a userDetailsLesson.
     *
     * @param userDetailsLessonDTO the entity to save.
     * @return the persisted entity.
     */
    UserDetailsLessonDTO save(UserDetailsLessonDTO userDetailsLessonDTO);

    /**
     * Get all the userDetailsLessons.
     *
     * @return the list of entities.
     */
    List<UserDetailsLessonDTO> findAll();


    /**
     * Get the "id" userDetailsLesson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDetailsLessonDTO> findOne(Long id);

    /**
     * Delete the "id" userDetailsLesson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
