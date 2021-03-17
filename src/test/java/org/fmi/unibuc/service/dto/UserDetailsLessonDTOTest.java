package org.fmi.unibuc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fmi.unibuc.web.rest.TestUtil;

public class UserDetailsLessonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsLessonDTO.class);
        UserDetailsLessonDTO userDetailsLessonDTO1 = new UserDetailsLessonDTO();
        userDetailsLessonDTO1.setId(1L);
        UserDetailsLessonDTO userDetailsLessonDTO2 = new UserDetailsLessonDTO();
        assertThat(userDetailsLessonDTO1).isNotEqualTo(userDetailsLessonDTO2);
        userDetailsLessonDTO2.setId(userDetailsLessonDTO1.getId());
        assertThat(userDetailsLessonDTO1).isEqualTo(userDetailsLessonDTO2);
        userDetailsLessonDTO2.setId(2L);
        assertThat(userDetailsLessonDTO1).isNotEqualTo(userDetailsLessonDTO2);
        userDetailsLessonDTO1.setId(null);
        assertThat(userDetailsLessonDTO1).isNotEqualTo(userDetailsLessonDTO2);
    }
}
