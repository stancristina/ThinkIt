package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.UserDetailsLesson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserDetailsLesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsLessonRepository extends JpaRepository<UserDetailsLesson, Long> {
}
