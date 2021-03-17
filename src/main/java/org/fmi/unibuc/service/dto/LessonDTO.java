package org.fmi.unibuc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.Lesson} entity.
 */
public class LessonDTO implements Serializable {
    
    private Long id;

    private String title;

    private Integer order;

    private String url;


    private Long chapterId;
    
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LessonDTO)) {
            return false;
        }

        return id != null && id.equals(((LessonDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LessonDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", order=" + getOrder() +
            ", url='" + getUrl() + "'" +
            ", chapterId=" + getChapterId() +
            "}";
    }
}
