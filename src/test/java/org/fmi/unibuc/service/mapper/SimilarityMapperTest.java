package org.fmi.unibuc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SimilarityMapperTest {

    private SimilarityMapper similarityMapper;

    @BeforeEach
    public void setUp() {
        similarityMapper = new SimilarityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(similarityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(similarityMapper.fromId(null)).isNull();
    }
}
