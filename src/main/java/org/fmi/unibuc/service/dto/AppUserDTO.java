package org.fmi.unibuc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.AppUser} entity.
 */
public class AppUserDTO implements Serializable {
    
    private Long id;

    private Integer xp;


    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        return id != null && id.equals(((AppUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", xp=" + getXp() +
            ", userId=" + getUserId() +
            "}";
    }
}
