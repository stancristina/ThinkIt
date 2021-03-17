package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A UserDetailsCourse.
 */
@Entity
@Table(name = "user_details_course")
public class UserDetailsCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_started")
    private Boolean isStarted;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "evaluation_completed")
    private Boolean evaluationCompleted;

    @Column(name = "evaluation_grade", precision = 21, scale = 2)
    private BigDecimal evaluationGrade;

    @ManyToOne
    @JsonIgnoreProperties(value = "userDetailsCourses", allowSetters = true)
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = "userDetailsCourses", allowSetters = true)
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsStarted() {
        return isStarted;
    }

    public UserDetailsCourse isStarted(Boolean isStarted) {
        this.isStarted = isStarted;
        return this;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public UserDetailsCourse isCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean isEvaluationCompleted() {
        return evaluationCompleted;
    }

    public UserDetailsCourse evaluationCompleted(Boolean evaluationCompleted) {
        this.evaluationCompleted = evaluationCompleted;
        return this;
    }

    public void setEvaluationCompleted(Boolean evaluationCompleted) {
        this.evaluationCompleted = evaluationCompleted;
    }

    public BigDecimal getEvaluationGrade() {
        return evaluationGrade;
    }

    public UserDetailsCourse evaluationGrade(BigDecimal evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
        return this;
    }

    public void setEvaluationGrade(BigDecimal evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
    }

    public Course getCourse() {
        return course;
    }

    public UserDetailsCourse course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public UserDetailsCourse appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDetailsCourse)) {
            return false;
        }
        return id != null && id.equals(((UserDetailsCourse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDetailsCourse{" +
            "id=" + getId() +
            ", isStarted='" + isIsStarted() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            ", evaluationCompleted='" + isEvaluationCompleted() + "'" +
            ", evaluationGrade=" + getEvaluationGrade() +
            "}";
    }
}
