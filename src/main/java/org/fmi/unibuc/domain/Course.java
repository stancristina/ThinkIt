package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.fmi.unibuc.domain.enumeration.CourseDifficulty;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private CourseDifficulty difficulty;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "released")
    private LocalDate released;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private Evaluation evaluation;

    @OneToMany(mappedBy = "course")
    private Set<Chapter> chapters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private Category category;

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

    public Course title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Course description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseDifficulty getDifficulty() {
        return difficulty;
    }

    public Course difficulty(CourseDifficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(CourseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getRating() {
        return rating;
    }

    public Course rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getReleased() {
        return released;
    }

    public Course released(LocalDate released) {
        this.released = released;
        return this;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Course thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Course evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public Course chapters(Set<Chapter> chapters) {
        this.chapters = chapters;
        return this;
    }

    public Course addChapters(Chapter chapter) {
        this.chapters.add(chapter);
        chapter.setCourse(this);
        return this;
    }

    public Course removeChapters(Chapter chapter) {
        this.chapters.remove(chapter);
        chapter.setCourse(null);
        return this;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Category getCategory() {
        return category;
    }

    public Course category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", rating=" + getRating() +
            ", released='" + getReleased() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            "}";
    }
}
