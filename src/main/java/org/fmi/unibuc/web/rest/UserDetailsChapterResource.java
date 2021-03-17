package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.UserDetailsChapterService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.UserDetailsChapterDTO;

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
 * REST controller for managing {@link org.fmi.unibuc.domain.UserDetailsChapter}.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsChapterResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsChapterResource.class);

    private static final String ENTITY_NAME = "userDetailsChapter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDetailsChapterService userDetailsChapterService;

    public UserDetailsChapterResource(UserDetailsChapterService userDetailsChapterService) {
        this.userDetailsChapterService = userDetailsChapterService;
    }

    /**
     * {@code POST  /user-details-chapters} : Create a new userDetailsChapter.
     *
     * @param userDetailsChapterDTO the userDetailsChapterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDetailsChapterDTO, or with status {@code 400 (Bad Request)} if the userDetailsChapter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-details-chapters")
    public ResponseEntity<UserDetailsChapterDTO> createUserDetailsChapter(@RequestBody UserDetailsChapterDTO userDetailsChapterDTO) throws URISyntaxException {
        log.debug("REST request to save UserDetailsChapter : {}", userDetailsChapterDTO);
        if (userDetailsChapterDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDetailsChapter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetailsChapterDTO result = userDetailsChapterService.save(userDetailsChapterDTO);
        return ResponseEntity.created(new URI("/api/user-details-chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-details-chapters} : Updates an existing userDetailsChapter.
     *
     * @param userDetailsChapterDTO the userDetailsChapterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDetailsChapterDTO,
     * or with status {@code 400 (Bad Request)} if the userDetailsChapterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDetailsChapterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-details-chapters")
    public ResponseEntity<UserDetailsChapterDTO> updateUserDetailsChapter(@RequestBody UserDetailsChapterDTO userDetailsChapterDTO) throws URISyntaxException {
        log.debug("REST request to update UserDetailsChapter : {}", userDetailsChapterDTO);
        if (userDetailsChapterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetailsChapterDTO result = userDetailsChapterService.save(userDetailsChapterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userDetailsChapterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-details-chapters} : get all the userDetailsChapters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDetailsChapters in body.
     */
    @GetMapping("/user-details-chapters")
    public List<UserDetailsChapterDTO> getAllUserDetailsChapters() {
        log.debug("REST request to get all UserDetailsChapters");
        return userDetailsChapterService.findAll();
    }

    /**
     * {@code GET  /user-details-chapters/:id} : get the "id" userDetailsChapter.
     *
     * @param id the id of the userDetailsChapterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDetailsChapterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-details-chapters/{id}")
    public ResponseEntity<UserDetailsChapterDTO> getUserDetailsChapter(@PathVariable Long id) {
        log.debug("REST request to get UserDetailsChapter : {}", id);
        Optional<UserDetailsChapterDTO> userDetailsChapterDTO = userDetailsChapterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDetailsChapterDTO);
    }

    /**
     * {@code DELETE  /user-details-chapters/:id} : delete the "id" userDetailsChapter.
     *
     * @param id the id of the userDetailsChapterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-details-chapters/{id}")
    public ResponseEntity<Void> deleteUserDetailsChapter(@PathVariable Long id) {
        log.debug("REST request to delete UserDetailsChapter : {}", id);
        userDetailsChapterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
