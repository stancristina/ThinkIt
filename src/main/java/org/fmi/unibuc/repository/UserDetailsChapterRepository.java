package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.UserDetailsChapter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserDetailsChapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsChapterRepository extends JpaRepository<UserDetailsChapter, Long> {
}
