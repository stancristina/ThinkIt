package org.fmi.unibuc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChapterMapperTest {

    private ChapterMapper chapterMapper;

    @BeforeEach
    public void setUp() {
        chapterMapper = new ChapterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(chapterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chapterMapper.fromId(null)).isNull();
    }
}
