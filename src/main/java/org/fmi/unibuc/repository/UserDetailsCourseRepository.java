package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.AppUser;
import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.domain.User;
import org.fmi.unibuc.domain.UserDetailsCourse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserDetailsCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsCourseRepository extends JpaRepository<UserDetailsCourse, Long> {

    Optional<UserDetailsCourse> findUserDetailsCourseByAppUserAndCourse(AppUser appUser, Course course);

    List<UserDetailsCourse> findUserDetailsCourseByAppUser(AppUser appUser);

}
