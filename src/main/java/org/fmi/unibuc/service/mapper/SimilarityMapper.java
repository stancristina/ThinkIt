package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.SimilarityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Similarity} and its DTO {@link SimilarityDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SimilarityMapper extends EntityMapper<SimilarityDTO, Similarity> {

    @Mapping(source = "courseA.id", target = "courseAId")
    @Mapping(source = "courseB.id", target = "courseBId")
    SimilarityDTO toDto(Similarity similarity);

    @Mapping(source = "courseAId", target = "courseA")
    @Mapping(source = "courseBId", target = "courseB")
    Similarity toEntity(SimilarityDTO similarityDTO);

    default Similarity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Similarity similarity = new Similarity();
        similarity.setId(id);
        return similarity;
    }
}
