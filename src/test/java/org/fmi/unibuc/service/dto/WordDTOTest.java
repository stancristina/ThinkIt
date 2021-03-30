package org.fmi.unibuc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class WordDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordDTO.class);
        WordDTO wordDTO1 = new WordDTO();
        wordDTO1.setId(1L);
        WordDTO wordDTO2 = new WordDTO();
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO2.setId(wordDTO1.getId());
        assertThat(wordDTO1).isEqualTo(wordDTO2);
        wordDTO2.setId(2L);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO1.setId(null);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
    }
}
