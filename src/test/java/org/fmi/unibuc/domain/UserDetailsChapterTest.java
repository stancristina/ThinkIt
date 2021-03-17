package org.fmi.unibuc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsChapterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsChapter.class);
        UserDetailsChapter userDetailsChapter1 = new UserDetailsChapter();
        userDetailsChapter1.setId(1L);
        UserDetailsChapter userDetailsChapter2 = new UserDetailsChapter();
        userDetailsChapter2.setId(userDetailsChapter1.getId());
        assertThat(userDetailsChapter1).isEqualTo(userDetailsChapter2);
        userDetailsChapter2.setId(2L);
        assertThat(userDetailsChapter1).isNotEqualTo(userDetailsChapter2);
        userDetailsChapter1.setId(null);
        assertThat(userDetailsChapter1).isNotEqualTo(userDetailsChapter2);
    }
}
