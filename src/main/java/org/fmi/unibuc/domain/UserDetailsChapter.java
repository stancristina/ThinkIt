package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserDetailsChapter.
 */
@Entity
@Table(name = "user_details_chapter")
public class UserDetailsChapter implements Serializable {

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
    @JsonIgnoreProperties(value = "userDetailsChapters", allowSetters = true)
    private Chapter chapter;

    @ManyToOne
    @JsonIgnoreProperties(value = "userDetailsChapters", allowSetters = true)
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

    public UserDetailsChapter isStarted(Boolean isStarted) {
        this.isStarted = isStarted;
        return this;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public UserDetailsChapter isCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public UserDetailsChapter chapter(Chapter chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public UserDetailsChapter appUser(AppUser appUser) {
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
        if (!(o instanceof UserDetailsChapter)) {
            return false;
        }
        return id != null && id.equals(((UserDetailsChapter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDetailsChapter{" +
            "id=" + getId() +
            ", isStarted='" + isIsStarted() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            "}";
    }
}
