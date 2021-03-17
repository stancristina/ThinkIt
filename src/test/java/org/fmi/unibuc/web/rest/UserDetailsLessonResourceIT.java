package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.ThinkItApp;
import org.fmi.unibuc.domain.UserDetailsLesson;
import org.fmi.unibuc.repository.UserDetailsLessonRepository;
import org.fmi.unibuc.service.UserDetailsLessonService;
import org.fmi.unibuc.service.dto.UserDetailsLessonDTO;
import org.fmi.unibuc.service.mapper.UserDetailsLessonMapper;

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
 * Integration tests for the {@link UserDetailsLessonResource} REST controller.
 */
@SpringBootTest(classes = ThinkItApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserDetailsLessonResourceIT {

    private static final Boolean DEFAULT_IS_STARTED = false;
    private static final Boolean UPDATED_IS_STARTED = true;

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    @Autowired
    private UserDetailsLessonRepository userDetailsLessonRepository;

    @Autowired
    private UserDetailsLessonMapper userDetailsLessonMapper;

    @Autowired
    private UserDetailsLessonService userDetailsLessonService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDetailsLessonMockMvc;

    private UserDetailsLesson userDetailsLesson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsLesson createEntity(EntityManager em) {
        UserDetailsLesson userDetailsLesson = new UserDetailsLesson()
            .isStarted(DEFAULT_IS_STARTED)
            .isCompleted(DEFAULT_IS_COMPLETED);
        return userDetailsLesson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsLesson createUpdatedEntity(EntityManager em) {
        UserDetailsLesson userDetailsLesson = new UserDetailsLesson()
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED);
        return userDetailsLesson;
    }

    @BeforeEach
    public void initTest() {
        userDetailsLesson = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDetailsLesson() throws Exception {
        int databaseSizeBeforeCreate = userDetailsLessonRepository.findAll().size();
        // Create the UserDetailsLesson
        UserDetailsLessonDTO userDetailsLessonDTO = userDetailsLessonMapper.toDto(userDetailsLesson);
        restUserDetailsLessonMockMvc.perform(post("/api/user-details-lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsLessonDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDetailsLesson in the database
        List<UserDetailsLesson> userDetailsLessonList = userDetailsLessonRepository.findAll();
        assertThat(userDetailsLessonList).hasSize(databaseSizeBeforeCreate + 1);
        UserDetailsLesson testUserDetailsLesson = userDetailsLessonList.get(userDetailsLessonList.size() - 1);
        assertThat(testUserDetailsLesson.isIsStarted()).isEqualTo(DEFAULT_IS_STARTED);
        assertThat(testUserDetailsLesson.isIsCompleted()).isEqualTo(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void createUserDetailsLessonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDetailsLessonRepository.findAll().size();

        // Create the UserDetailsLesson with an existing ID
        userDetailsLesson.setId(1L);
        UserDetailsLessonDTO userDetailsLessonDTO = userDetailsLessonMapper.toDto(userDetailsLesson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDetailsLessonMockMvc.perform(post("/api/user-details-lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsLessonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsLesson in the database
        List<UserDetailsLesson> userDetailsLessonList = userDetailsLessonRepository.findAll();
        assertThat(userDetailsLessonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserDetailsLessons() throws Exception {
        // Initialize the database
        userDetailsLessonRepository.saveAndFlush(userDetailsLesson);

        // Get all the userDetailsLessonList
        restUserDetailsLessonMockMvc.perform(get("/api/user-details-lessons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDetailsLesson.getId().intValue())))
            .andExpect(jsonPath("$.[*].isStarted").value(hasItem(DEFAULT_IS_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserDetailsLesson() throws Exception {
        // Initialize the database
        userDetailsLessonRepository.saveAndFlush(userDetailsLesson);

        // Get the userDetailsLesson
        restUserDetailsLessonMockMvc.perform(get("/api/user-details-lessons/{id}", userDetailsLesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDetailsLesson.getId().intValue()))
            .andExpect(jsonPath("$.isStarted").value(DEFAULT_IS_STARTED.booleanValue()))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserDetailsLesson() throws Exception {
        // Get the userDetailsLesson
        restUserDetailsLessonMockMvc.perform(get("/api/user-details-lessons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDetailsLesson() throws Exception {
        // Initialize the database
        userDetailsLessonRepository.saveAndFlush(userDetailsLesson);

        int databaseSizeBeforeUpdate = userDetailsLessonRepository.findAll().size();

        // Update the userDetailsLesson
        UserDetailsLesson updatedUserDetailsLesson = userDetailsLessonRepository.findById(userDetailsLesson.getId()).get();
        // Disconnect from session so that the updates on updatedUserDetailsLesson are not directly saved in db
        em.detach(updatedUserDetailsLesson);
        updatedUserDetailsLesson
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED);
        UserDetailsLessonDTO userDetailsLessonDTO = userDetailsLessonMapper.toDto(updatedUserDetailsLesson);

        restUserDetailsLessonMockMvc.perform(put("/api/user-details-lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsLessonDTO)))
            .andExpect(status().isOk());

        // Validate the UserDetailsLesson in the database
        List<UserDetailsLesson> userDetailsLessonList = userDetailsLessonRepository.findAll();
        assertThat(userDetailsLessonList).hasSize(databaseSizeBeforeUpdate);
        UserDetailsLesson testUserDetailsLesson = userDetailsLessonList.get(userDetailsLessonList.size() - 1);
        assertThat(testUserDetailsLesson.isIsStarted()).isEqualTo(UPDATED_IS_STARTED);
        assertThat(testUserDetailsLesson.isIsCompleted()).isEqualTo(UPDATED_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDetailsLesson() throws Exception {
        int databaseSizeBeforeUpdate = userDetailsLessonRepository.findAll().size();

        // Create the UserDetailsLesson
        UserDetailsLessonDTO userDetailsLessonDTO = userDetailsLessonMapper.toDto(userDetailsLesson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDetailsLessonMockMvc.perform(put("/api/user-details-lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsLessonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsLesson in the database
        List<UserDetailsLesson> userDetailsLessonList = userDetailsLessonRepository.findAll();
        assertThat(userDetailsLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDetailsLesson() throws Exception {
        // Initialize the database
        userDetailsLessonRepository.saveAndFlush(userDetailsLesson);

        int databaseSizeBeforeDelete = userDetailsLessonRepository.findAll().size();

        // Delete the userDetailsLesson
        restUserDetailsLessonMockMvc.perform(delete("/api/user-details-lessons/{id}", userDetailsLesson.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDetailsLesson> userDetailsLessonList = userDetailsLessonRepository.findAll();
        assertThat(userDetailsLessonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
