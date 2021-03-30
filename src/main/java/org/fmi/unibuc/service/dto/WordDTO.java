package org.fmi.unibuc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.fmi.unibuc.domain.Word} entity.
 */
public class WordDTO implements Serializable {
    
    private Long id;

    private String value;

    private Integer frequency;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordDTO)) {
            return false;
        }

        return id != null && id.equals(((WordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", frequency=" + getFrequency() +
            "}";
    }
}
