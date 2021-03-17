package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.UserDetailsCourse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserDetailsCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsCourseRepository extends JpaRepository<UserDetailsCourse, Long> {
}
