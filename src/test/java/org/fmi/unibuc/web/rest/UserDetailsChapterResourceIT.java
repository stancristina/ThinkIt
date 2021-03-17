package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.ThinkItApp;
import org.fmi.unibuc.domain.UserDetailsChapter;
import org.fmi.unibuc.repository.UserDetailsChapterRepository;
import org.fmi.unibuc.service.UserDetailsChapterService;
import org.fmi.unibuc.service.dto.UserDetailsChapterDTO;
import org.fmi.unibuc.service.mapper.UserDetailsChapterMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserDetailsChapterResource} REST controller.
 */
@SpringBootTest(classes = ThinkItApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserDetailsChapterResourceIT {

    private static final Boolean DEFAULT_IS_STARTED = false;
    private static final Boolean UPDATED_IS_STARTED = true;

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    @Autowired
    private UserDetailsChapterRepository userDetailsChapterRepository;

    @Autowired
    private UserDetailsChapterMapper userDetailsChapterMapper;

    @Autowired
    private UserDetailsChapterService userDetailsChapterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDetailsChapterMockMvc;

    private UserDetailsChapter userDetailsChapter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsChapter createEntity(EntityManager em) {
        UserDetailsChapter userDetailsChapter = new UserDetailsChapter()
            .isStarted(DEFAULT_IS_STARTED)
            .isCompleted(DEFAULT_IS_COMPLETED);
        return userDetailsChapter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsChapter createUpdatedEntity(EntityManager em) {
        UserDetailsChapter userDetailsChapter = new UserDetailsChapter()
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED);
        return userDetailsChapter;
    }

    @BeforeEach
    public void initTest() {
        userDetailsChapter = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDetailsChapter() throws Exception {
        int databaseSizeBeforeCreate = userDetailsChapterRepository.findAll().size();
        // Create the UserDetailsChapter
        UserDetailsChapterDTO userDetailsChapterDTO = userDetailsChapterMapper.toDto(userDetailsChapter);
        restUserDetailsChapterMockMvc.perform(post("/api/user-details-chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsChapterDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDetailsChapter in the database
        List<UserDetailsChapter> userDetailsChapterList = userDetailsChapterRepository.findAll();
        assertThat(userDetailsChapterList).hasSize(databaseSizeBeforeCreate + 1);
        UserDetailsChapter testUserDetailsChapter = userDetailsChapterList.get(userDetailsChapterList.size() - 1);
        assertThat(testUserDetailsChapter.isIsStarted()).isEqualTo(DEFAULT_IS_STARTED);
        assertThat(testUserDetailsChapter.isIsCompleted()).isEqualTo(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void createUserDetailsChapterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDetailsChapterRepository.findAll().size();

        // Create the UserDetailsChapter with an existing ID
        userDetailsChapter.setId(1L);
        UserDetailsChapterDTO userDetailsChapterDTO = userDetailsChapterMapper.toDto(userDetailsChapter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDetailsChapterMockMvc.perform(post("/api/user-details-chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsChapterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsChapter in the database
        List<UserDetailsChapter> userDetailsChapterList = userDetailsChapterRepository.findAll();
        assertThat(userDetailsChapterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserDetailsChapters() throws Exception {
        // Initialize the database
        userDetailsChapterRepository.saveAndFlush(userDetailsChapter);

        // Get all the userDetailsChapterList
        restUserDetailsChapterMockMvc.perform(get("/api/user-details-chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDetailsChapter.getId().intValue())))
            .andExpect(jsonPath("$.[*].isStarted").value(hasItem(DEFAULT_IS_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserDetailsChapter() throws Exception {
        // Initialize the database
        userDetailsChapterRepository.saveAndFlush(userDetailsChapter);

        // Get the userDetailsChapter
        restUserDetailsChapterMockMvc.perform(get("/api/user-details-chapters/{id}", userDetailsChapter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDetailsChapter.getId().intValue()))
            .andExpect(jsonPath("$.isStarted").value(DEFAULT_IS_STARTED.booleanValue()))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserDetailsChapter() throws Exception {
        // Get the userDetailsChapter
        restUserDetailsChapterMockMvc.perform(get("/api/user-details-chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDetailsChapter() throws Exception {
        // Initialize the database
        userDetailsChapterRepository.saveAndFlush(userDetailsChapter);

        int databaseSizeBeforeUpdate = userDetailsChapterRepository.findAll().size();

        // Update the userDetailsChapter
        UserDetailsChapter updatedUserDetailsChapter = userDetailsChapterRepository.findById(userDetailsChapter.getId()).get();
        // Disconnect from session so that the updates on updatedUserDetailsChapter are not directly saved in db
        em.detach(updatedUserDetailsChapter);
        updatedUserDetailsChapter
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED);
        UserDetailsChapterDTO userDetailsChapterDTO = userDetailsChapterMapper.toDto(updatedUserDetailsChapter);

        restUserDetailsChapterMockMvc.perform(put("/api/user-details-chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsChapterDTO)))
            .andExpect(status().isOk());

        // Validate the UserDetailsChapter in the database
        List<UserDetailsChapter> userDetailsChapterList = userDetailsChapterRepository.findAll();
        assertThat(userDetailsChapterList).hasSize(databaseSizeBeforeUpdate);
        UserDetailsChapter testUserDetailsChapter = userDetailsChapterList.get(userDetailsChapterList.size() - 1);
        assertThat(testUserDetailsChapter.isIsStarted()).isEqualTo(UPDATED_IS_STARTED);
        assertThat(testUserDetailsChapter.isIsCompleted()).isEqualTo(UPDATED_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDetailsChapter() throws Exception {
        int databaseSizeBeforeUpdate = userDetailsChapterRepository.findAll().size();

        // Create the UserDetailsChapter
        UserDetailsChapterDTO userDetailsChapterDTO = userDetailsChapterMapper.toDto(userDetailsChapter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDetailsChapterMockMvc.perform(put("/api/user-details-chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsChapterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsChapter in the database
        List<UserDetailsChapter> userDetailsChapterList = userDetailsChapterRepository.findAll();
        assertThat(userDetailsChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDetailsChapter() throws Exception {
        // Initialize the database
        userDetailsChapterRepository.saveAndFlush(userDetailsChapter);

        int databaseSizeBeforeDelete = userDetailsChapterRepository.findAll().size();

        // Delete the userDetailsChapter
        restUserDetailsChapterMockMvc.perform(delete("/api/user-details-chapters/{id}", userDetailsChapter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDetailsChapter> userDetailsChapterList = userDetailsChapterRepository.findAll();
        assertThat(userDetailsChapterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
