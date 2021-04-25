package org.fmi.unibuc.service;

import org.fmi.unibuc.service.dto.UserDetailsChapterDTO;
import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.fmi.unibuc.domain.UserDetailsChapter}.
 */
public interface UserDetailsChapterService {

    /**
     * Save a userDetailsChapter.
     *
     * @param userDetailsChapterDTO the entity to save.
     * @return the persisted entity.
     */
    UserDetailsChapterDTO save(UserDetailsChapterDTO userDetailsChapterDTO);

    /**
     * Get all the userDetailsChapters.
     *
     * @return the list of entities.
     */
    List<UserDetailsChapterDTO> findAll();

    /**
     * Get the "id" userDetailsChapter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDetailsChapterDTO> findOne(Long id);

    /**
     * Delete the "id" userDetailsChapter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
