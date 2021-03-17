package org.fmi.unibuc.service.dto;

import java.io.Serializable;

public class CourseCustomDTO implements Serializable {

    private Long id;

    private String thumbnailUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            "}";
    }
}
