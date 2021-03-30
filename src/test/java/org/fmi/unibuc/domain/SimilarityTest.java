package org.fmi.unibuc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class SimilarityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Similarity.class);
        Similarity similarity1 = new Similarity();
        similarity1.setId(1L);
        Similarity similarity2 = new Similarity();
        similarity2.setId(similarity1.getId());
        assertThat(similarity1).isEqualTo(similarity2);
        similarity2.setId(2L);
        assertThat(similarity1).isNotEqualTo(similarity2);
        similarity1.setId(null);
        assertThat(similarity1).isNotEqualTo(similarity2);
    }
}
