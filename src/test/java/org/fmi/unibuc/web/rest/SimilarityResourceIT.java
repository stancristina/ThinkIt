package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.ThinkItApp;
import org.fmi.unibuc.domain.Similarity;
import org.fmi.unibuc.repository.SimilarityRepository;
import org.fmi.unibuc.service.SimilarityService;
import org.fmi.unibuc.service.dto.SimilarityDTO;
import org.fmi.unibuc.service.mapper.SimilarityMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SimilarityResource} REST controller.
 */
@SpringBootTest(classes = ThinkItApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SimilarityResourceIT {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Autowired
    private SimilarityRepository similarityRepository;

    @Autowired
    private SimilarityMapper similarityMapper;

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSimilarityMockMvc;

    private Similarity similarity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Similarity createEntity(EntityManager em) {
        Similarity similarity = new Similarity()
            .value(DEFAULT_VALUE);
        return similarity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Similarity createUpdatedEntity(EntityManager em) {
        Similarity similarity = new Similarity()
            .value(UPDATED_VALUE);
        return similarity;
    }

    @BeforeEach
    public void initTest() {
        similarity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSimilarity() throws Exception {
        int databaseSizeBeforeCreate = similarityRepository.findAll().size();
        // Create the Similarity
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);
        restSimilarityMockMvc.perform(post("/api/similarities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isCreated());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeCreate + 1);
        Similarity testSimilarity = similarityList.get(similarityList.size() - 1);
        assertThat(testSimilarity.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSimilarityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = similarityRepository.findAll().size();

        // Create the Similarity with an existing ID
        similarity.setId(1L);
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSimilarityMockMvc.perform(post("/api/similarities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSimilarities() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        // Get all the similarityList
        restSimilarityMockMvc.perform(get("/api/similarities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(similarity.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        // Get the similarity
        restSimilarityMockMvc.perform(get("/api/similarities/{id}", similarity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(similarity.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSimilarity() throws Exception {
        // Get the similarity
        restSimilarityMockMvc.perform(get("/api/similarities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        int databaseSizeBeforeUpdate = similarityRepository.findAll().size();

        // Update the similarity
        Similarity updatedSimilarity = similarityRepository.findById(similarity.getId()).get();
        // Disconnect from session so that the updates on updatedSimilarity are not directly saved in db
        em.detach(updatedSimilarity);
        updatedSimilarity
            .value(UPDATED_VALUE);
        SimilarityDTO similarityDTO = similarityMapper.toDto(updatedSimilarity);

        restSimilarityMockMvc.perform(put("/api/similarities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isOk());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeUpdate);
        Similarity testSimilarity = similarityList.get(similarityList.size() - 1);
        assertThat(testSimilarity.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSimilarity() throws Exception {
        int databaseSizeBeforeUpdate = similarityRepository.findAll().size();

        // Create the Similarity
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSimilarityMockMvc.perform(put("/api/similarities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        int databaseSizeBeforeDelete = similarityRepository.findAll().size();

        // Delete the similarity
        restSimilarityMockMvc.perform(delete("/api/similarities/{id}", similarity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
