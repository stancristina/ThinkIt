package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the UserDetailsChapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsChapterRepository extends JpaRepository<UserDetailsChapter, Long> {
    Optional<UserDetailsChapter> findUserDetailsChapterByAppUserAndChapter(AppUser appUser, Chapter chapter);
}
