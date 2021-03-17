package org.fmi.unibuc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsCourseMapperTest {

    private UserDetailsCourseMapper userDetailsCourseMapper;

    @BeforeEach
    public void setUp() {
        userDetailsCourseMapper = new UserDetailsCourseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userDetailsCourseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userDetailsCourseMapper.fromId(null)).isNull();
    }
}
