package org.fmi.unibuc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class SimilarityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SimilarityDTO.class);
        SimilarityDTO similarityDTO1 = new SimilarityDTO();
        similarityDTO1.setId(1L);
        SimilarityDTO similarityDTO2 = new SimilarityDTO();
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
        similarityDTO2.setId(similarityDTO1.getId());
        assertThat(similarityDTO1).isEqualTo(similarityDTO2);
        similarityDTO2.setId(2L);
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
        similarityDTO1.setId(null);
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
    }
}
