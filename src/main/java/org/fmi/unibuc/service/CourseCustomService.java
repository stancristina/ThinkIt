package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.CourseCustomDTO;

import java.util.List;
import java.util.Optional;

public interface CourseCustomService {

    /**
     * Save a course.
     *
     * @param courseCustomDTO the entity to save.
     * @return the persisted entity.
     */
    CourseCustomDTO save(CourseCustomDTO courseCustomDTO);

    /**
     * Get all the courses.
     *
     * @return the list of entities.
     */
    List<CourseCustomDTO> findAll();


    /**
     * Get the "id" course.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseCustomDTO> findOne(Long id);

    /**
     * Delete the "id" course.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
