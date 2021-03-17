package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.ChapterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chapter} and its DTO {@link ChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface ChapterMapper extends EntityMapper<ChapterDTO, Chapter> {

    @Mapping(source = "course.id", target = "courseId")
    ChapterDTO toDto(Chapter chapter);

    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "removeLessons", ignore = true)
    @Mapping(source = "courseId", target = "course")
    Chapter toEntity(ChapterDTO chapterDTO);

    default Chapter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapter chapter = new Chapter();
        chapter.setId(id);
        return chapter;
    }
}
