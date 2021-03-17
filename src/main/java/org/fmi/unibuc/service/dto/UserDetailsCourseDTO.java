package org.fmi.unibuc.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.UserDetailsCourse} entity.
 */
public class UserDetailsCourseDTO implements Serializable {
    
    private Long id;

    private Boolean isStarted;

    private Boolean isCompleted;

    private Boolean evaluationCompleted;

    private BigDecimal evaluationGrade;


    private Long courseId;

    private Long appUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsStarted() {
        return isStarted;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean isEvaluationCompleted() {
        return evaluationCompleted;
    }

    public void setEvaluationCompleted(Boolean evaluationCompleted) {
        this.evaluationCompleted = evaluationCompleted;
    }

    public BigDecimal getEvaluationGrade() {
        return evaluationGrade;
    }

    public void setEvaluationGrade(BigDecimal evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDetailsCourseDTO)) {
            return false;
        }

        return id != null && id.equals(((UserDetailsCourseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDetailsCourseDTO{" +
            "id=" + getId() +
            ", isStarted='" + isIsStarted() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            ", evaluationCompleted='" + isEvaluationCompleted() + "'" +
            ", evaluationGrade=" + getEvaluationGrade() +
            ", courseId=" + getCourseId() +
            ", appUserId=" + getAppUserId() +
            "}";
    }
}
