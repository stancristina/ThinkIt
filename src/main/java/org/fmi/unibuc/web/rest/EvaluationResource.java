package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.EvaluationService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.EvaluationDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.fmi.unibuc.domain.Evaluation}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationResource.class);

    private static final String ENTITY_NAME = "evaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationService evaluationService;

    public EvaluationResource(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * {@code POST  /evaluations} : Create a new evaluation.
     *
     * @param evaluationDTO the evaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationDTO, or with status {@code 400 (Bad Request)} if the evaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluations")
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) throws URISyntaxException {
        log.debug("REST request to save Evaluation : {}", evaluationDTO);
        if (evaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationDTO result = evaluationService.save(evaluationDTO);
        return ResponseEntity.created(new URI("/api/evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluations} : Updates an existing evaluation.
     *
     * @param evaluationDTO the evaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluations")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@RequestBody EvaluationDTO evaluationDTO) throws URISyntaxException {
        log.debug("REST request to update Evaluation : {}", evaluationDTO);
        if (evaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationDTO result = evaluationService.save(evaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evaluations} : get all the evaluations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluations in body.
     */
    @GetMapping("/evaluations")
    public List<EvaluationDTO> getAllEvaluations(@RequestParam(required = false) String filter) {
        if ("course-is-null".equals(filter)) {
            log.debug("REST request to get all Evaluations where course is null");
            return evaluationService.findAllWhereCourseIsNull();
        }
        log.debug("REST request to get all Evaluations");
        return evaluationService.findAll();
    }

    /**
     * {@code GET  /evaluations/:id} : get the "id" evaluation.
     *
     * @param id the id of the evaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluations/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluation(@PathVariable Long id) {
        log.debug("REST request to get Evaluation : {}", id);
        Optional<EvaluationDTO> evaluationDTO = evaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationDTO);
    }

    /**
     * {@code DELETE  /evaluations/:id} : delete the "id" evaluation.
     *
     * @param id the id of the evaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluations/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete Evaluation : {}", id);
        evaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
