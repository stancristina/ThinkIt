package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.UserDetailsChapterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserDetailsChapter} and its DTO {@link UserDetailsChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChapterMapper.class, AppUserMapper.class})
public interface UserDetailsChapterMapper extends EntityMapper<UserDetailsChapterDTO, UserDetailsChapter> {

    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "appUser.id", target = "appUserId")
    UserDetailsChapterDTO toDto(UserDetailsChapter userDetailsChapter);

    @Mapping(source = "chapterId", target = "chapter")
    @Mapping(source = "appUserId", target = "appUser")
    UserDetailsChapter toEntity(UserDetailsChapterDTO userDetailsChapterDTO);

    default UserDetailsChapter fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDetailsChapter userDetailsChapter = new UserDetailsChapter();
        userDetailsChapter.setId(id);
        return userDetailsChapter;
    }
}
