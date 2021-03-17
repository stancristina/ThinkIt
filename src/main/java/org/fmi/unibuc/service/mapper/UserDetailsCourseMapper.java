package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserDetailsCourse} and its DTO {@link UserDetailsCourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, AppUserMapper.class})
public interface UserDetailsCourseMapper extends EntityMapper<UserDetailsCourseDTO, UserDetailsCourse> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "appUser.id", target = "appUserId")
    UserDetailsCourseDTO toDto(UserDetailsCourse userDetailsCourse);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "appUserId", target = "appUser")
    UserDetailsCourse toEntity(UserDetailsCourseDTO userDetailsCourseDTO);

    default UserDetailsCourse fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDetailsCourse userDetailsCourse = new UserDetailsCourse();
        userDetailsCourse.setId(id);
        return userDetailsCourse;
    }
}
