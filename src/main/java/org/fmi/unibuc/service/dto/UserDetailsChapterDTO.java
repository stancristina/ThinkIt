package org.fmi.unibuc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.UserDetailsChapter} entity.
 */
public class UserDetailsChapterDTO implements Serializable {
    
    private Long id;

    private Boolean isStarted;

    private Boolean isCompleted;


    private Long chapterId;

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

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
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
        if (!(o instanceof UserDetailsChapterDTO)) {
            return false;
        }

        return id != null && id.equals(((UserDetailsChapterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDetailsChapterDTO{" +
            "id=" + getId() +
            ", isStarted='" + isIsStarted() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            ", chapterId=" + getChapterId() +
            ", appUserId=" + getAppUserId() +
            "}";
    }
}
