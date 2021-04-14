package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.recommandation.engine.RecommendationEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing {@link org.fmi.unibuc.domain.Lesson}.
 */
@RestController
@RequestMapping("/api")
public class RecommendationEngineResource {
    private final Logger log = LoggerFactory.getLogger(RecommendationEngineResource.class);

    @Autowired
    private RecommendationEngineService recommendationEngineService;

    /**
     * GET  /recalculate : recalculate recommendations.
     *
     * @return the ResponseEntity with status 200 (OK) and nothing in body
     */
    @GetMapping("/recommendation")
    public ResponseEntity<Boolean> getAllRoles() {
        log.debug("REST request to recalculate recommendations");

        recommendationEngineService.recalculateSimilarities();

        return ResponseEntity.ok().body(true);
    }
}
