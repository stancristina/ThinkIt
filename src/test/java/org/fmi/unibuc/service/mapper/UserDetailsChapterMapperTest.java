package org.fmi.unibuc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsChapterMapperTest {

    private UserDetailsChapterMapper userDetailsChapterMapper;

    @BeforeEach
    public void setUp() {
        userDetailsChapterMapper = new UserDetailsChapterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userDetailsChapterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userDetailsChapterMapper.fromId(null)).isNull();
    }
}
