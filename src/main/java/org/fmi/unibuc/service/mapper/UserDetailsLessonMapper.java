package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.UserDetailsLessonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserDetailsLesson} and its DTO {@link UserDetailsLessonDTO}.
 */
@Mapper(componentModel = "spring", uses = {LessonMapper.class, AppUserMapper.class})
public interface UserDetailsLessonMapper extends EntityMapper<UserDetailsLessonDTO, UserDetailsLesson> {

    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "appUser.id", target = "appUserId")
    UserDetailsLessonDTO toDto(UserDetailsLesson userDetailsLesson);

    @Mapping(source = "lessonId", target = "lesson")
    @Mapping(source = "appUserId", target = "appUser")
    UserDetailsLesson toEntity(UserDetailsLessonDTO userDetailsLessonDTO);

    default UserDetailsLesson fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDetailsLesson userDetailsLesson = new UserDetailsLesson();
        userDetailsLesson.setId(id);
        return userDetailsLesson;
    }
}
