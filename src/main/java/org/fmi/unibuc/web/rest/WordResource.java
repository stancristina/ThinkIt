package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.service.WordService;
import org.fmi.unibuc.web.rest.errors.BadRequestAlertException;
import org.fmi.unibuc.service.dto.WordDTO;

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
 * REST controller for managing {@link org.fmi.unibuc.domain.Word}.
 */
@RestController
@RequestMapping("/api")
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordService wordService;

    public WordResource(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * {@code POST  /words} : Create a new word.
     *
     * @param wordDTO the wordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordDTO, or with status {@code 400 (Bad Request)} if the word has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/words")
    public ResponseEntity<WordDTO> createWord(@RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to save Word : {}", wordDTO);
        if (wordDTO.getId() != null) {
            throw new BadRequestAlertException("A new word cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /words} : Updates an existing word.
     *
     * @param wordDTO the wordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordDTO,
     * or with status {@code 400 (Bad Request)} if the wordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/words")
    public ResponseEntity<WordDTO> updateWord(@RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to update Word : {}", wordDTO);
        if (wordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /words} : get all the words.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of words in body.
     */
    @GetMapping("/words")
    public List<WordDTO> getAllWords() {
        log.debug("REST request to get all Words");
        return wordService.findAll();
    }

    /**
     * {@code GET  /words/:id} : get the "id" word.
     *
     * @param id the id of the wordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/words/{id}")
    public ResponseEntity<WordDTO> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Optional<WordDTO> wordDTO = wordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordDTO);
    }

    /**
     * {@code DELETE  /words/:id} : delete the "id" word.
     *
     * @param id the id of the wordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);
        wordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
