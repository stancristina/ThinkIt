package org.fmi.unibuc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsCourseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsCourse.class);
        UserDetailsCourse userDetailsCourse1 = new UserDetailsCourse();
        userDetailsCourse1.setId(1L);
        UserDetailsCourse userDetailsCourse2 = new UserDetailsCourse();
        userDetailsCourse2.setId(userDetailsCourse1.getId());
        assertThat(userDetailsCourse1).isEqualTo(userDetailsCourse2);
        userDetailsCourse2.setId(2L);
        assertThat(userDetailsCourse1).isNotEqualTo(userDetailsCourse2);
        userDetailsCourse1.setId(null);
        assertThat(userDetailsCourse1).isNotEqualTo(userDetailsCourse2);
    }
}
