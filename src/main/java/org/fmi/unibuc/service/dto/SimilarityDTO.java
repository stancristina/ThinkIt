package org.fmi.unibuc.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.Similarity} entity.
 */
public class SimilarityDTO implements Serializable {
    
    private Long id;

    private BigDecimal value;


    private Long courseAId;

    private Long courseBId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getCourseAId() {
        return courseAId;
    }

    public void setCourseAId(Long courseId) {
        this.courseAId = courseId;
    }

    public Long getCourseBId() {
        return courseBId;
    }

    public void setCourseBId(Long courseId) {
        this.courseBId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimilarityDTO)) {
            return false;
        }

        return id != null && id.equals(((SimilarityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SimilarityDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", courseAId=" + getCourseAId() +
            ", courseBId=" + getCourseBId() +
            "}";
    }
}
