package org.fmi.unibuc.web.rest;

import org.fmi.unibuc.ThinkItApp;
import org.fmi.unibuc.domain.Question;
import org.fmi.unibuc.repository.QuestionRepository;
import org.fmi.unibuc.service.QuestionService;
import org.fmi.unibuc.service.dto.QuestionDTO;
import org.fmi.unibuc.service.mapper.QuestionMapper;

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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@SpringBootTest(classes = ThinkItApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_A = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_A = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_B = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_B = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_C = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_C = "BBBBBBBBBB";

    private static final String DEFAULT_CORRECT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_CORRECT_ANSWER = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUESTION_TYPE = 1;
    private static final Integer UPDATED_QUESTION_TYPE = 2;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionMockMvc;

    private Question question;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .question(DEFAULT_QUESTION)
            .answerA(DEFAULT_ANSWER_A)
            .answerB(DEFAULT_ANSWER_B)
            .answerC(DEFAULT_ANSWER_C)
            .correctAnswer(DEFAULT_CORRECT_ANSWER)
            .questionType(DEFAULT_QUESTION_TYPE);
        return question;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question question = new Question()
            .question(UPDATED_QUESTION)
            .answerA(UPDATED_ANSWER_A)
            .answerB(UPDATED_ANSWER_B)
            .answerC(UPDATED_ANSWER_C)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .questionType(UPDATED_QUESTION_TYPE);
        return question;
    }

    @BeforeEach
    public void initTest() {
        question = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();
        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestion.getAnswerA()).isEqualTo(DEFAULT_ANSWER_A);
        assertThat(testQuestion.getAnswerB()).isEqualTo(DEFAULT_ANSWER_B);
        assertThat(testQuestion.getAnswerC()).isEqualTo(DEFAULT_ANSWER_C);
        assertThat(testQuestion.getCorrectAnswer()).isEqualTo(DEFAULT_CORRECT_ANSWER);
        assertThat(testQuestion.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void createQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question with an existing ID
        question.setId(1L);
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answerA").value(hasItem(DEFAULT_ANSWER_A)))
            .andExpect(jsonPath("$.[*].answerB").value(hasItem(DEFAULT_ANSWER_B)))
            .andExpect(jsonPath("$.[*].answerC").value(hasItem(DEFAULT_ANSWER_C)))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER)))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE)));
    }
    
    @Test
    @Transactional
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answerA").value(DEFAULT_ANSWER_A))
            .andExpect(jsonPath("$.answerB").value(DEFAULT_ANSWER_B))
            .andExpect(jsonPath("$.answerC").value(DEFAULT_ANSWER_C))
            .andExpect(jsonPath("$.correctAnswer").value(DEFAULT_CORRECT_ANSWER))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion
            .question(UPDATED_QUESTION)
            .answerA(UPDATED_ANSWER_A)
            .answerB(UPDATED_ANSWER_B)
            .answerC(UPDATED_ANSWER_C)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .questionType(UPDATED_QUESTION_TYPE);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestion.getAnswerA()).isEqualTo(UPDATED_ANSWER_A);
        assertThat(testQuestion.getAnswerB()).isEqualTo(UPDATED_ANSWER_B);
        assertThat(testQuestion.getAnswerC()).isEqualTo(UPDATED_ANSWER_C);
        assertThat(testQuestion.getCorrectAnswer()).isEqualTo(UPDATED_CORRECT_ANSWER);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
