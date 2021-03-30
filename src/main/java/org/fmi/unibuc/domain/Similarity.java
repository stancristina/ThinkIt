package org.fmi.unibuc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Similarity.
 */
@Entity
@Table(name = "similarity")
public class Similarity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value", precision = 21, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JsonIgnoreProperties(value = "similarities", allowSetters = true)
    private Course courseA;

    @ManyToOne
    @JsonIgnoreProperties(value = "similarities", allowSetters = true)
    private Course courseB;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Similarity value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Course getCourseA() {
        return courseA;
    }

    public Similarity courseA(Course course) {
        this.courseA = course;
        return this;
    }

    public void setCourseA(Course course) {
        this.courseA = course;
    }

    public Course getCourseB() {
        return courseB;
    }

    public Similarity courseB(Course course) {
        this.courseB = course;
        return this;
    }

    public void setCourseB(Course course) {
        this.courseB = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Similarity)) {
            return false;
        }
        return id != null && id.equals(((Similarity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Similarity{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
