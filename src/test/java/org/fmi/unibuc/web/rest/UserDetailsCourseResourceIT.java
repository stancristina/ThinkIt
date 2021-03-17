package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.ThinkItApp;
import org.fmi.unibuc.domain.UserDetailsCourse;
import org.fmi.unibuc.repository.UserDetailsCourseRepository;
import org.fmi.unibuc.service.UserDetailsCourseService;
import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;
import org.fmi.unibuc.service.mapper.UserDetailsCourseMapper;

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
 * Integration tests for the {@link UserDetailsCourseResource} REST controller.
 */
@SpringBootTest(classes = ThinkItApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserDetailsCourseResourceIT {

    private static final Boolean DEFAULT_IS_STARTED = false;
    private static final Boolean UPDATED_IS_STARTED = true;

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    private static final Boolean DEFAULT_EVALUATION_COMPLETED = false;
    private static final Boolean UPDATED_EVALUATION_COMPLETED = true;

    private static final BigDecimal DEFAULT_EVALUATION_GRADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EVALUATION_GRADE = new BigDecimal(2);

    @Autowired
    private UserDetailsCourseRepository userDetailsCourseRepository;

    @Autowired
    private UserDetailsCourseMapper userDetailsCourseMapper;

    @Autowired
    private UserDetailsCourseService userDetailsCourseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDetailsCourseMockMvc;

    private UserDetailsCourse userDetailsCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsCourse createEntity(EntityManager em) {
        UserDetailsCourse userDetailsCourse = new UserDetailsCourse()
            .isStarted(DEFAULT_IS_STARTED)
            .isCompleted(DEFAULT_IS_COMPLETED)
            .evaluationCompleted(DEFAULT_EVALUATION_COMPLETED)
            .evaluationGrade(DEFAULT_EVALUATION_GRADE);
        return userDetailsCourse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetailsCourse createUpdatedEntity(EntityManager em) {
        UserDetailsCourse userDetailsCourse = new UserDetailsCourse()
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED)
            .evaluationCompleted(UPDATED_EVALUATION_COMPLETED)
            .evaluationGrade(UPDATED_EVALUATION_GRADE);
        return userDetailsCourse;
    }

    @BeforeEach
    public void initTest() {
        userDetailsCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDetailsCourse() throws Exception {
        int databaseSizeBeforeCreate = userDetailsCourseRepository.findAll().size();
        // Create the UserDetailsCourse
        UserDetailsCourseDTO userDetailsCourseDTO = userDetailsCourseMapper.toDto(userDetailsCourse);
        restUserDetailsCourseMockMvc.perform(post("/api/user-details-courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsCourseDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDetailsCourse in the database
        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findAll();
        assertThat(userDetailsCourseList).hasSize(databaseSizeBeforeCreate + 1);
        UserDetailsCourse testUserDetailsCourse = userDetailsCourseList.get(userDetailsCourseList.size() - 1);
        assertThat(testUserDetailsCourse.isIsStarted()).isEqualTo(DEFAULT_IS_STARTED);
        assertThat(testUserDetailsCourse.isIsCompleted()).isEqualTo(DEFAULT_IS_COMPLETED);
        assertThat(testUserDetailsCourse.isEvaluationCompleted()).isEqualTo(DEFAULT_EVALUATION_COMPLETED);
        assertThat(testUserDetailsCourse.getEvaluationGrade()).isEqualTo(DEFAULT_EVALUATION_GRADE);
    }

    @Test
    @Transactional
    public void createUserDetailsCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDetailsCourseRepository.findAll().size();

        // Create the UserDetailsCourse with an existing ID
        userDetailsCourse.setId(1L);
        UserDetailsCourseDTO userDetailsCourseDTO = userDetailsCourseMapper.toDto(userDetailsCourse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDetailsCourseMockMvc.perform(post("/api/user-details-courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsCourseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsCourse in the database
        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findAll();
        assertThat(userDetailsCourseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserDetailsCourses() throws Exception {
        // Initialize the database
        userDetailsCourseRepository.saveAndFlush(userDetailsCourse);

        // Get all the userDetailsCourseList
        restUserDetailsCourseMockMvc.perform(get("/api/user-details-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDetailsCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].isStarted").value(hasItem(DEFAULT_IS_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].evaluationCompleted").value(hasItem(DEFAULT_EVALUATION_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].evaluationGrade").value(hasItem(DEFAULT_EVALUATION_GRADE.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserDetailsCourse() throws Exception {
        // Initialize the database
        userDetailsCourseRepository.saveAndFlush(userDetailsCourse);

        // Get the userDetailsCourse
        restUserDetailsCourseMockMvc.perform(get("/api/user-details-courses/{id}", userDetailsCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDetailsCourse.getId().intValue()))
            .andExpect(jsonPath("$.isStarted").value(DEFAULT_IS_STARTED.booleanValue()))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.evaluationCompleted").value(DEFAULT_EVALUATION_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.evaluationGrade").value(DEFAULT_EVALUATION_GRADE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserDetailsCourse() throws Exception {
        // Get the userDetailsCourse
        restUserDetailsCourseMockMvc.perform(get("/api/user-details-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDetailsCourse() throws Exception {
        // Initialize the database
        userDetailsCourseRepository.saveAndFlush(userDetailsCourse);

        int databaseSizeBeforeUpdate = userDetailsCourseRepository.findAll().size();

        // Update the userDetailsCourse
        UserDetailsCourse updatedUserDetailsCourse = userDetailsCourseRepository.findById(userDetailsCourse.getId()).get();
        // Disconnect from session so that the updates on updatedUserDetailsCourse are not directly saved in db
        em.detach(updatedUserDetailsCourse);
        updatedUserDetailsCourse
            .isStarted(UPDATED_IS_STARTED)
            .isCompleted(UPDATED_IS_COMPLETED)
            .evaluationCompleted(UPDATED_EVALUATION_COMPLETED)
            .evaluationGrade(UPDATED_EVALUATION_GRADE);
        UserDetailsCourseDTO userDetailsCourseDTO = userDetailsCourseMapper.toDto(updatedUserDetailsCourse);

        restUserDetailsCourseMockMvc.perform(put("/api/user-details-courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsCourseDTO)))
            .andExpect(status().isOk());

        // Validate the UserDetailsCourse in the database
        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findAll();
        assertThat(userDetailsCourseList).hasSize(databaseSizeBeforeUpdate);
        UserDetailsCourse testUserDetailsCourse = userDetailsCourseList.get(userDetailsCourseList.size() - 1);
        assertThat(testUserDetailsCourse.isIsStarted()).isEqualTo(UPDATED_IS_STARTED);
        assertThat(testUserDetailsCourse.isIsCompleted()).isEqualTo(UPDATED_IS_COMPLETED);
        assertThat(testUserDetailsCourse.isEvaluationCompleted()).isEqualTo(UPDATED_EVALUATION_COMPLETED);
        assertThat(testUserDetailsCourse.getEvaluationGrade()).isEqualTo(UPDATED_EVALUATION_GRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDetailsCourse() throws Exception {
        int databaseSizeBeforeUpdate = userDetailsCourseRepository.findAll().size();

        // Create the UserDetailsCourse
        UserDetailsCourseDTO userDetailsCourseDTO = userDetailsCourseMapper.toDto(userDetailsCourse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDetailsCourseMockMvc.perform(put("/api/user-details-courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsCourseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetailsCourse in the database
        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findAll();
        assertThat(userDetailsCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDetailsCourse() throws Exception {
        // Initialize the database
        userDetailsCourseRepository.saveAndFlush(userDetailsCourse);

        int databaseSizeBeforeDelete = userDetailsCourseRepository.findAll().size();

        // Delete the userDetailsCourse
        restUserDetailsCourseMockMvc.perform(delete("/api/user-details-courses/{id}", userDetailsCourse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findAll();
        assertThat(userDetailsCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
