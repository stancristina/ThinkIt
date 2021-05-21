package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_a")
    private String answerA;

    @Column(name = "answer_b")
    private String answerB;

    @Column(name = "answer_c")
    private String answerC;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "question_type")
    private Integer questionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private Evaluation evaluation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Question question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public Question answerA(String answerA) {
        this.answerA = answerA;
        return this;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public Question answerB(String answerB) {
        this.answerB = answerB;
        return this;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public Question answerC(String answerC) {
        this.answerC = answerC;
        return this;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Question correctAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public Question questionType(Integer questionType) {
        this.questionType = questionType;
        return this;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Question evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", answerA='" + getAnswerA() + "'" +
            ", answerB='" + getAnswerB() + "'" +
            ", answerC='" + getAnswerC() + "'" +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", questionType=" + getQuestionType() +
            "}";
    }
}
