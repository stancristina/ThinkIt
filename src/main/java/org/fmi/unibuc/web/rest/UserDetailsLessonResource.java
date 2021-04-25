package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.UserDetailsLessonService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.UserDetailsLessonDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.fmi.unibuc.domain.UserDetailsLesson}.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsLessonResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsLessonResource.class);

    private static final String ENTITY_NAME = "userDetailsLesson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDetailsLessonService userDetailsLessonService;

    public UserDetailsLessonResource(UserDetailsLessonService userDetailsLessonService) {
        this.userDetailsLessonService = userDetailsLessonService;
    }

    /**
     * {@code POST  /user-details-lessons} : Create a new userDetailsLesson.
     *
     * @param userDetailsLessonDTO the userDetailsLessonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDetailsLessonDTO, or with status {@code 400 (Bad Request)} if the userDetailsLesson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-details-lessons")
    public ResponseEntity<UserDetailsLessonDTO> createUserDetailsLesson(@RequestBody UserDetailsLessonDTO userDetailsLessonDTO) throws URISyntaxException {
        log.debug("REST request to save UserDetailsLesson : {}", userDetailsLessonDTO);
        if (userDetailsLessonDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDetailsLesson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetailsLessonDTO result = userDetailsLessonService.save(userDetailsLessonDTO);
        return ResponseEntity.created(new URI("/api/user-details-lessons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-details-lessons} : Updates an existing userDetailsLesson.
     *
     * @param userDetailsLessonDTO the userDetailsLessonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDetailsLessonDTO,
     * or with status {@code 400 (Bad Request)} if the userDetailsLessonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDetailsLessonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-details-lessons")
    public ResponseEntity<UserDetailsLessonDTO> updateUserDetailsLesson(@RequestBody UserDetailsLessonDTO userDetailsLessonDTO) throws URISyntaxException {
        log.debug("REST request to update UserDetailsLesson : {}", userDetailsLessonDTO);
        if (userDetailsLessonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetailsLessonDTO result = userDetailsLessonService.save(userDetailsLessonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userDetailsLessonDTO.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /user-details-lessons} : get all the userDetailsLessons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDetailsLessons in body.
     */
    @GetMapping("/user-details-lessons")
    public List<UserDetailsLessonDTO> getAllUserDetailsLessons() {
        log.debug("REST request to get all UserDetailsLessons");
        return userDetailsLessonService.findAll();
    }

    /**
     * {@code GET  /user-details-lessons/:id} : get the "id" userDetailsLesson.
     *
     * @param id the id of the userDetailsLessonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDetailsLessonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-details-lessons/{id}")
    public ResponseEntity<UserDetailsLessonDTO> getUserDetailsLesson(@PathVariable Long id) {
        log.debug("REST request to get UserDetailsLesson : {}", id);
        Optional<UserDetailsLessonDTO> userDetailsLessonDTO = userDetailsLessonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDetailsLessonDTO);
    }

    /**
     * {@code DELETE  /user-details-lessons/:id} : delete the "id" userDetailsLesson.
     *
     * @param id the id of the userDetailsLessonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-details-lessons/{id}")
    public ResponseEntity<Void> deleteUserDetailsLesson(@PathVariable Long id) {
        log.debug("REST request to delete UserDetailsLesson : {}", id);
        userDetailsLessonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
