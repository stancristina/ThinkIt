package org.fmi.unibuc.service.dto;

import java.math.BigDecimal;

public class EvaluationCompletedDTO {

    private Long evaluationId;

    private BigDecimal grade;

    public EvaluationCompletedDTO() {
    }

    public EvaluationCompletedDTO(Long evaluationId, BigDecimal grade) {
        this.evaluationId = evaluationId;
        this.grade = grade;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }
}
