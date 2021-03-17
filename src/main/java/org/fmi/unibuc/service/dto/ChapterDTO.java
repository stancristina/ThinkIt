package org.fmi.unibuc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.Chapter} entity.
 */
public class ChapterDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;

    private Integer orderNr;

    private Integer xp;


    private Long courseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Integer orderNr) {
        this.orderNr = orderNr;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChapterDTO)) {
            return false;
        }

        return id != null && id.equals(((ChapterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChapterDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderNr=" + getOrderNr() +
            ", xp=" + getXp() +
            ", courseId=" + getCourseId() +
            "}";
    }
}
