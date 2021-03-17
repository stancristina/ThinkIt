package org.fmi.unibuc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsLessonMapperTest {

    private UserDetailsLessonMapper userDetailsLessonMapper;

    @BeforeEach
    public void setUp() {
        userDetailsLessonMapper = new UserDetailsLessonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userDetailsLessonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userDetailsLessonMapper.fromId(null)).isNull();
    }
}
