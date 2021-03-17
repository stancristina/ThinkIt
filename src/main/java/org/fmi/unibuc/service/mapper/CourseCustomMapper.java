package org.fmi.unibuc.service.mapper;

import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.service.dto.CourseCustomDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface CourseCustomMapper extends EntityMapper<CourseCustomDTO, Course> {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "thumbnailUrl", target = "thumbnailUrl")
    CourseCustomDTO toDto(Course course);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "thumbnailUrl",target = "thumbnailUrl")
    @Mapping(target = "removeChapters", ignore = true)
    Course toEntity(CourseCustomDTO courseCustomDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
