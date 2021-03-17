package org.fmi.unibuc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsLessonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsLesson.class);
        UserDetailsLesson userDetailsLesson1 = new UserDetailsLesson();
        userDetailsLesson1.setId(1L);
        UserDetailsLesson userDetailsLesson2 = new UserDetailsLesson();
        userDetailsLesson2.setId(userDetailsLesson1.getId());
        assertThat(userDetailsLesson1).isEqualTo(userDetailsLesson2);
        userDetailsLesson2.setId(2L);
        assertThat(userDetailsLesson1).isNotEqualTo(userDetailsLesson2);
        userDetailsLesson1.setId(null);
        assertThat(userDetailsLesson1).isNotEqualTo(userDetailsLesson2);
    }
}
