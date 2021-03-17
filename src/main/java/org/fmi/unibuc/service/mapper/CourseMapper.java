package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {EvaluationMapper.class, CategoryMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "evaluation.id", target = "evaluationId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.title", target = "categoryTitle")
    CourseDTO toDto(Course course);

    @Mapping(source = "evaluationId", target = "evaluation")
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "removeChapters", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
