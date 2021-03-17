package org.fmi.unibuc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsCourseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsCourseDTO.class);
        UserDetailsCourseDTO userDetailsCourseDTO1 = new UserDetailsCourseDTO();
        userDetailsCourseDTO1.setId(1L);
        UserDetailsCourseDTO userDetailsCourseDTO2 = new UserDetailsCourseDTO();
        assertThat(userDetailsCourseDTO1).isNotEqualTo(userDetailsCourseDTO2);
        userDetailsCourseDTO2.setId(userDetailsCourseDTO1.getId());
        assertThat(userDetailsCourseDTO1).isEqualTo(userDetailsCourseDTO2);
        userDetailsCourseDTO2.setId(2L);
        assertThat(userDetailsCourseDTO1).isNotEqualTo(userDetailsCourseDTO2);
        userDetailsCourseDTO1.setId(null);
        assertThat(userDetailsCourseDTO1).isNotEqualTo(userDetailsCourseDTO2);
    }
}
