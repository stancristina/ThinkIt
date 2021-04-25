package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the UserDetailsLesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsLessonRepository extends JpaRepository<UserDetailsLesson, Long> {
    Optional<UserDetailsLesson> findUserDetailsLessonByAppUserAndLesson(AppUser appUser, Lesson lesson);
}
