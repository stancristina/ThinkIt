package org.fmi.unibuc.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import liquibase.pro.packaged.B;
import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.repository.*;
import org.fmi.unibuc.security.SecurityUtils;
import org.fmi.unibuc.service.CustomService;
import org.fmi.unibuc.service.dto.*;
import org.fmi.unibuc.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsCourseServiceImpl.class);

    private final LessonRepository lessonRepository;

    private final ChapterRepository chapterRepository;

    private final CourseRepository courseRepository;

    private final LessonMapper lessonMapper;

    private final UserDetailsLessonMapper userDetailsLessonMapper;

    private final UserDetailsChapterMapper userDetailsChapterMapper;

    private final UserDetailsLessonRepository userDetailsLessonRepository;

    private final UserDetailsChapterRepository userDetailsChapterRepository;

    private final UserDetailsCourseRepository userDetailsCourseRepository;

    private final UserRepository userRepository;

    private final AppUserRepository appUserRepository;

    private final SimilarityRepository similarityRepository;

    private final CourseMapper courseMapper;

    private final UserDetailsCourseMapper userDetailsCourseMapper;

    public CustomServiceImpl(LessonRepository lessonRepository, ChapterRepository chapterRepository, CourseRepository courseRepository, LessonMapper lessonMapper, UserDetailsLessonMapper userDetailsLessonMapper, UserDetailsChapterMapper userDetailsChapterMapper, UserDetailsLessonRepository userDetailsLessonRepository, UserDetailsChapterRepository userDetailsChapterRepository, UserDetailsCourseRepository userDetailsCourseRepository, UserRepository userRepository, AppUserRepository appUserRepository, SimilarityRepository similarityRepository, CourseMapper courseMapper, UserDetailsCourseMapper userDetailsCourseMapper) {
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
        this.courseRepository = courseRepository;
        this.lessonMapper = lessonMapper;
        this.userDetailsLessonMapper = userDetailsLessonMapper;
        this.userDetailsChapterMapper = userDetailsChapterMapper;
        this.userDetailsLessonRepository = userDetailsLessonRepository;
        this.userDetailsChapterRepository = userDetailsChapterRepository;
        this.userDetailsCourseRepository = userDetailsCourseRepository;
        this.userRepository = userRepository;
        this.appUserRepository = appUserRepository;
        this.similarityRepository = similarityRepository;
        this.courseMapper = courseMapper;
        this.userDetailsCourseMapper = userDetailsCourseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> findAllLessonsByChapterId(long chapterId) {
        Chapter chapter = chapterRepository.getOne(chapterId);
        return lessonRepository.findAllByChapterOrderByOrderAsc(chapter).stream()
            .map(lessonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ExtendedChapterDTO> findAllChaptersByCourseId(long courseId) {
        Course course = courseRepository.getOne(courseId);
        List<Chapter> chapterDTOList = chapterRepository.findAllByCourseOrderByOrderNrAsc(course);
        List<ExtendedChapterDTO> results = new ArrayList<>();
        chapterDTOList.forEach(chapter -> {
            List<LessonDTO> lessonDTOList = findAllLessonsByChapterId(chapter.getId());
            ExtendedChapterDTO extendedChapterDTO = new ExtendedChapterDTO();
            extendedChapterDTO.setId(chapter.getId());
            extendedChapterDTO.setTitle(chapter.getTitle());
            extendedChapterDTO.setDescription(chapter.getDescription());
            extendedChapterDTO.setCourseId(chapter.getCourse().getId());
            extendedChapterDTO.setOrderNr(chapter.getOrderNr());
            extendedChapterDTO.setXp(chapter.getXp());
            extendedChapterDTO.setLessons(lessonDTOList);
            results.add(extendedChapterDTO);
        });
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkUserIsEnrolledInCourse(long courseId) {
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return false;
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if (!appUser.isPresent()) {
            return false;
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return false;
        }

        Optional<UserDetailsCourse> userDetailsCourseOpt = userDetailsCourseRepository.
            findUserDetailsCourseByAppUserAndCourse(appUser.get(), courseOptional.get());

        return userDetailsCourseOpt.isPresent();
    }

    @Override
    @Transactional()
    public Boolean enrollAppUserInCourse(long courseId) {
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return false;
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if (!appUser.isPresent()) {
            return false;
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return false;
        }
        Course course = courseOptional.get();
        UserDetailsCourse userDetailsCourse = new UserDetailsCourse();
        userDetailsCourse.setCourse(course);
        userDetailsCourse.setAppUser(appUser.get());
        userDetailsCourse.setIsStarted(true);
        userDetailsCourseRepository.save(userDetailsCourse);

        List<Chapter> chapterList = chapterRepository.findAllByCourseOrderByOrderNrAsc(course);
        for (Chapter chapter : chapterList) {
            UserDetailsChapter userDetailsChapter = new UserDetailsChapter();
            userDetailsChapter.setAppUser(appUser.get());
            userDetailsChapter.setChapter(chapter);
            userDetailsChapter.setIsStarted(true);
            userDetailsChapterRepository.save(userDetailsChapter);

            lessonRepository.findAllByChapterOrderByOrderAsc(chapter).forEach(p -> {
                UserDetailsLesson userDetailsLesson = new UserDetailsLesson();
                userDetailsLesson.setAppUser(appUser.get());
                userDetailsLesson.setLesson(p);
                userDetailsLesson.setIsStarted(true);
                userDetailsLessonRepository.save(userDetailsLesson);
            });

        }

        return true;

    }

    public List<CourseDTO> getCourseRecommendationForLoggedUser() {

        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return new ArrayList<>();
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if (!appUser.isPresent()) {
            return new ArrayList<>();
        }

        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findUserDetailsCourseByAppUser(appUser.get());

        Set<Similarity> similaritySet = new HashSet<>();
        for (UserDetailsCourse userDetailsCourse : userDetailsCourseList) {
            List<Similarity> currentCourseSimilarities = similarityRepository.findAllByCourseA(userDetailsCourse.getCourse())
                .stream().filter(p -> p.getValue().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
            similaritySet.addAll(currentCourseSimilarities);
        }

        return similaritySet.stream()
            .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
            .limit(10).map(Similarity::getCourseB).distinct()
            .map(courseMapper::toDto)
            .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsCourseDTO findOneByCurrentUserAndCourse(long courseId) {
        log.debug("Request to get CurrentUserAndCourse");
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return null;
        }
        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return null;
        }

        Optional<UserDetailsCourse> userDetailsCourseOpt = userDetailsCourseRepository.findUserDetailsCourseByAppUserAndCourse(appUser.get(), courseOptional.get());
        return userDetailsCourseOpt.map(userDetailsCourseMapper::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsChapterDTO> findAllByCurrentUserAndChapter(long courseId) {
        log.debug("Request to get CurrentUserAndChapter");
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return null;
        }
        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return null;
        }
        Course course = courseOptional.get();

        List<UserDetailsChapterDTO> results = new ArrayList<>();

        for (Chapter chapter : course.getChapters()) {
            Optional<UserDetailsChapter> userDetailsChapterOpt = userDetailsChapterRepository.findUserDetailsChapterByAppUserAndChapter(appUser.get(), chapter);
            userDetailsChapterOpt.ifPresent(userDetailsChapter -> results.add(userDetailsChapterMapper.toDto(userDetailsChapter)));
        }

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsLessonDTO> findAllByCurrentUserAndLesson(long courseId) {
        log.debug("Request to get CurrentUserAndLesson");
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return null;
        }
        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return null;
        }
        Course course = courseOptional.get();

        List<UserDetailsLessonDTO> results = new ArrayList<>();

        for (Chapter chapter : course.getChapters()) {
            for (Lesson lesson : chapter.getLessons()) {
                Optional<UserDetailsLesson> userDetailsLessonOpt = userDetailsLessonRepository.findUserDetailsLessonByAppUserAndLesson(appUser.get(), lesson);
                userDetailsLessonOpt.ifPresent(userDetailsLesson -> results.add(userDetailsLessonMapper.toDto(userDetailsLesson)));
            }
        }
        return results;
    }

    @Transactional
    @Override
    public Boolean updateCompletedLesson(long lessonId) {

        // Update the lesson that was finished
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (!optionalLesson.isPresent()) {
            return Boolean.FALSE;
        }

        Lesson lesson = optionalLesson.get();

        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (!userOpt.isPresent()) {
            return Boolean.FALSE;
        }
        Optional<AppUser> appUserOpt = appUserRepository.findAppUserByUser(userOpt.get());

        AppUser appUser = appUserOpt.get();

        Optional<UserDetailsLesson> userDetailsLessonOpt = userDetailsLessonRepository.findUserDetailsLessonByAppUserAndLesson(appUser, lesson);
        if (!userDetailsLessonOpt.isPresent()) {
            return Boolean.FALSE;
        }

        UserDetailsLesson userDetailsLesson = userDetailsLessonOpt.get();
        userDetailsLesson.setIsCompleted(true);
        userDetailsLessonRepository.save(userDetailsLesson);

        // Check if the chapter is finished.
        // A chapter is finished if all the lessons in that chapter are finished

        Chapter chapter = lesson.getChapter();
        boolean isChapterFinished = true;
        for(Lesson chapterLesson : chapter.getLessons()) {
            Optional<UserDetailsLesson> userDetailsLessonOptTemp = userDetailsLessonRepository.findUserDetailsLessonByAppUserAndLesson(appUser, chapterLesson);
            if (!userDetailsLessonOptTemp.isPresent()) {
                return Boolean.FALSE;
            }
            UserDetailsLesson userDetailsLessonTemp = userDetailsLessonOptTemp.get();
            if(userDetailsLessonTemp.isIsCompleted() == null || !userDetailsLessonTemp.isIsCompleted()) {
                isChapterFinished = false;
                break;
            }
        }

        // If chapter is not finished, just exit
        if(!isChapterFinished) {
            return Boolean.TRUE;
        }

        // Update the userDetailsChapter to isCompleted = true
        Optional<UserDetailsChapter> userDetailsChapterOpt = userDetailsChapterRepository.findUserDetailsChapterByAppUserAndChapter(appUser, chapter);
        if(!userDetailsChapterOpt.isPresent()) {
            return Boolean.FALSE;
        }
        UserDetailsChapter userDetailsChapter = userDetailsChapterOpt.get();
        userDetailsChapter.setIsCompleted(true);
        userDetailsChapterRepository.save(userDetailsChapter);

        // Check if the course is finished after finishing this chapter
        Course course = chapter.getCourse();
        boolean isCourseFinished = true;
        for(Chapter c : course.getChapters()) {
            Optional<UserDetailsChapter> userDetailsChapterOptTemp = userDetailsChapterRepository.findUserDetailsChapterByAppUserAndChapter(appUser, c);
            if (!userDetailsChapterOptTemp.isPresent()) {
                return Boolean.FALSE;
            }
            UserDetailsChapter userDetailsChapterTemp = userDetailsChapterOptTemp.get();
            if(userDetailsChapterTemp.isIsCompleted() == null || !userDetailsChapterTemp.isIsCompleted()) {
                isCourseFinished = false;
                break;
            }
        }

        // if course not finished, just exit
        if(!isCourseFinished) {
            return Boolean.TRUE;
        }

        // update the userDetailsCourse
        Optional<UserDetailsCourse> userDetailsCourseOptional = userDetailsCourseRepository.findUserDetailsCourseByAppUserAndCourse(appUser, course);
        if(!userDetailsCourseOptional.isPresent()) {
            return Boolean.FALSE;
        }

        UserDetailsCourse userDetailsCourse = userDetailsCourseOptional.get();
        userDetailsCourse.setIsCompleted(true);
        userDetailsCourseRepository.save(userDetailsCourse);

        return Boolean.TRUE;
    }
}



