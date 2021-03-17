package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.UserDetailsCourse}.
 */
public interface UserDetailsCourseService {

    /**
     * Save a userDetailsCourse.
     *
     * @param userDetailsCourseDTO the entity to save.
     * @return the persisted entity.
     */
    UserDetailsCourseDTO save(UserDetailsCourseDTO userDetailsCourseDTO);

    /**
     * Get all the userDetailsCourses.
     *
     * @return the list of entities.
     */
    List<UserDetailsCourseDTO> findAll();


    /**
     * Get the "id" userDetailsCourse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDetailsCourseDTO> findOne(Long id);

    /**
     * Delete the "id" userDetailsCourse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
