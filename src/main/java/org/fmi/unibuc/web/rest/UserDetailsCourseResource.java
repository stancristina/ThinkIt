package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.UserDetailsCourseService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;

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
 * REST controller for managing {@link org.fmi.unibuc.domain.UserDetailsCourse}.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsCourseResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsCourseResource.class);

    private static final String ENTITY_NAME = "userDetailsCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDetailsCourseService userDetailsCourseService;

    public UserDetailsCourseResource(UserDetailsCourseService userDetailsCourseService) {
        this.userDetailsCourseService = userDetailsCourseService;
    }

    /**
     * {@code POST  /user-details-courses} : Create a new userDetailsCourse.
     *
     * @param userDetailsCourseDTO the userDetailsCourseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDetailsCourseDTO, or with status {@code 400 (Bad Request)} if the userDetailsCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-details-courses")
    public ResponseEntity<UserDetailsCourseDTO> createUserDetailsCourse(@RequestBody UserDetailsCourseDTO userDetailsCourseDTO) throws URISyntaxException {
        log.debug("REST request to save UserDetailsCourse : {}", userDetailsCourseDTO);
        if (userDetailsCourseDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDetailsCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetailsCourseDTO result = userDetailsCourseService.save(userDetailsCourseDTO);
        return ResponseEntity.created(new URI("/api/user-details-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-details-courses} : Updates an existing userDetailsCourse.
     *
     * @param userDetailsCourseDTO the userDetailsCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDetailsCourseDTO,
     * or with status {@code 400 (Bad Request)} if the userDetailsCourseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDetailsCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-details-courses")
    public ResponseEntity<UserDetailsCourseDTO> updateUserDetailsCourse(@RequestBody UserDetailsCourseDTO userDetailsCourseDTO) throws URISyntaxException {
        log.debug("REST request to update UserDetailsCourse : {}", userDetailsCourseDTO);
        if (userDetailsCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetailsCourseDTO result = userDetailsCourseService.save(userDetailsCourseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userDetailsCourseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-details-courses} : get all the userDetailsCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDetailsCourses in body.
     */
    @GetMapping("/user-details-courses")
    public List<UserDetailsCourseDTO> getAllUserDetailsCourses() {
        log.debug("REST request to get all UserDetailsCourses");
        return userDetailsCourseService.findAll();
    }

    /**
     * {@code GET  /user-details-courses/:id} : get the "id" userDetailsCourse.
     *
     * @param id the id of the userDetailsCourseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDetailsCourseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-details-courses/{id}")
    public ResponseEntity<UserDetailsCourseDTO> getUserDetailsCourse(@PathVariable Long id) {
        log.debug("REST request to get UserDetailsCourse : {}", id);
        Optional<UserDetailsCourseDTO> userDetailsCourseDTO = userDetailsCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDetailsCourseDTO);
    }

    /**
     * {@code DELETE  /user-details-courses/:id} : delete the "id" userDetailsCourse.
     *
     * @param id the id of the userDetailsCourseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-details-courses/{id}")
    public ResponseEntity<Void> deleteUserDetailsCourse(@PathVariable Long id) {
        log.debug("REST request to delete UserDetailsCourse : {}", id);
        userDetailsCourseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
