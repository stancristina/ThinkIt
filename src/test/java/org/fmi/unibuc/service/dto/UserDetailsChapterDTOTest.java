package org.fmi.unibuc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsChapterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsChapterDTO.class);
        UserDetailsChapterDTO userDetailsChapterDTO1 = new UserDetailsChapterDTO();
        userDetailsChapterDTO1.setId(1L);
        UserDetailsChapterDTO userDetailsChapterDTO2 = new UserDetailsChapterDTO();
        assertThat(userDetailsChapterDTO1).isNotEqualTo(userDetailsChapterDTO2);
        userDetailsChapterDTO2.setId(userDetailsChapterDTO1.getId());
        assertThat(userDetailsChapterDTO1).isEqualTo(userDetailsChapterDTO2);
        userDetailsChapterDTO2.setId(2L);
        assertThat(userDetailsChapterDTO1).isNotEqualTo(userDetailsChapterDTO2);
        userDetailsChapterDTO1.setId(null);
        assertThat(userDetailsChapterDTO1).isNotEqualTo(userDetailsChapterDTO2);
    }
}
