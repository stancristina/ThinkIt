package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "order_nr")
    private Integer orderNr;

    @Column(name = "xp")
    private Integer xp;

    @OneToMany(mappedBy = "chapter")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "chapters", allowSetters = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Chapter title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Chapter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderNr() {
        return orderNr;
    }

    public Chapter orderNr(Integer orderNr) {
        this.orderNr = orderNr;
        return this;
    }

    public void setOrderNr(Integer orderNr) {
        this.orderNr = orderNr;
    }

    public Integer getXp() {
        return xp;
    }

    public Chapter xp(Integer xp) {
        this.xp = xp;
        return this;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Chapter lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Chapter addLessons(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setChapter(this);
        return this;
    }

    public Chapter removeLessons(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setChapter(null);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Course getCourse() {
        return course;
    }

    public Chapter course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chapter)) {
            return false;
        }
        return id != null && id.equals(((Chapter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderNr=" + getOrderNr() +
            ", xp=" + getXp() +
            "}";
    }
}
