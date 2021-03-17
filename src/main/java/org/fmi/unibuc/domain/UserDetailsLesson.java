package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserDetailsLesson.
 */
@Entity
@Table(name = "user_details_lesson")
public class UserDetailsLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_started")
    private Boolean isStarted;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "userDetailsLessons", allowSetters = true)
    private Lesson lesson;

    @ManyToOne
    @JsonIgnoreProperties(value = "userDetailsLessons", allowSetters = true)
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

    public UserDetailsLesson isStarted(Boolean isStarted) {
        this.isStarted = isStarted;
        return this;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public UserDetailsLesson isCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public UserDetailsLesson lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public UserDetailsLesson appUser(AppUser appUser) {
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
        if (!(o instanceof UserDetailsLesson)) {
            return false;
        }
        return id != null && id.equals(((UserDetailsLesson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDetailsLesson{" +
            "id=" + getId() +
            ", isStarted='" + isIsStarted() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            "}";
    }
}
