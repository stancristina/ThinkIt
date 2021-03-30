package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.SimilarityService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.SimilarityDTO;

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
 * REST controller for managing {@link org.fmi.unibuc.domain.Similarity}.
 */
@RestController
@RequestMapping("/api")
public class SimilarityResource {

    private final Logger log = LoggerFactory.getLogger(SimilarityResource.class);

    private static final String ENTITY_NAME = "similarity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SimilarityService similarityService;

    public SimilarityResource(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    /**
     * {@code POST  /similarities} : Create a new similarity.
     *
     * @param similarityDTO the similarityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new similarityDTO, or with status {@code 400 (Bad Request)} if the similarity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/similarities")
    public ResponseEntity<SimilarityDTO> createSimilarity(@RequestBody SimilarityDTO similarityDTO) throws URISyntaxException {
        log.debug("REST request to save Similarity : {}", similarityDTO);
        if (similarityDTO.getId() != null) {
            throw new BadRequestAlertException("A new similarity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SimilarityDTO result = similarityService.save(similarityDTO);
        return ResponseEntity.created(new URI("/api/similarities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /similarities} : Updates an existing similarity.
     *
     * @param similarityDTO the similarityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated similarityDTO,
     * or with status {@code 400 (Bad Request)} if the similarityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the similarityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/similarities")
    public ResponseEntity<SimilarityDTO> updateSimilarity(@RequestBody SimilarityDTO similarityDTO) throws URISyntaxException {
        log.debug("REST request to update Similarity : {}", similarityDTO);
        if (similarityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SimilarityDTO result = similarityService.save(similarityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, similarityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /similarities} : get all the similarities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of similarities in body.
     */
    @GetMapping("/similarities")
    public List<SimilarityDTO> getAllSimilarities() {
        log.debug("REST request to get all Similarities");
        return similarityService.findAll();
    }

    /**
     * {@code GET  /similarities/:id} : get the "id" similarity.
     *
     * @param id the id of the similarityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the similarityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/similarities/{id}")
    public ResponseEntity<SimilarityDTO> getSimilarity(@PathVariable Long id) {
        log.debug("REST request to get Similarity : {}", id);
        Optional<SimilarityDTO> similarityDTO = similarityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(similarityDTO);
    }

    /**
     * {@code DELETE  /similarities/:id} : delete the "id" similarity.
     *
     * @param id the id of the similarityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/similarities/{id}")
    public ResponseEntity<Void> deleteSimilarity(@PathVariable Long id) {
        log.debug("REST request to delete Similarity : {}", id);
        similarityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
