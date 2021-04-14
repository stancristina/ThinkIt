package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.repository.*;
import org.fmi.unibuc.security.SecurityUtils;
import org.fmi.unibuc.service.CustomService;
import org.fmi.unibuc.service.dto.CourseCustomDTO;
import org.fmi.unibuc.service.dto.CourseDTO;
import org.fmi.unibuc.service.dto.ExtendedChapterDTO;
import org.fmi.unibuc.service.dto.LessonDTO;
import org.fmi.unibuc.service.mapper.CourseCustomMapper;
import org.fmi.unibuc.service.mapper.CourseMapper;
import org.fmi.unibuc.service.mapper.LessonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    private final LessonRepository lessonRepository;

    private final ChapterRepository chapterRepository;

    private final CourseRepository courseRepository;

    private final LessonMapper lessonMapper;

    private final UserDetailsLessonRepository userDetailsLessonRepository;

    private final UserDetailsChapterRepository userDetailsChapterRepository;

    private final UserDetailsCourseRepository userDetailsCourseRepository;

    private final UserRepository userRepository;

    private final AppUserRepository appUserRepository;

    private final SimilarityRepository similarityRepository;

    private final CourseMapper courseMapper;

    public CustomServiceImpl(LessonRepository lessonRepository, ChapterRepository chapterRepository, CourseRepository courseRepository, LessonMapper lessonMapper, UserDetailsLessonRepository userDetailsLessonRepository, UserDetailsChapterRepository userDetailsChapterRepository, UserDetailsCourseRepository userDetailsCourseRepository, UserRepository userRepository, AppUserRepository appUserRepository, SimilarityRepository similarityRepository, CourseMapper courseMapper) {
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
        this.courseRepository = courseRepository;
        this.lessonMapper = lessonMapper;
        this.userDetailsLessonRepository = userDetailsLessonRepository;
        this.userDetailsChapterRepository = userDetailsChapterRepository;
        this.userDetailsCourseRepository = userDetailsCourseRepository;
        this.userRepository = userRepository;
        this.appUserRepository = appUserRepository;
        this.similarityRepository = similarityRepository;
        this.courseMapper = courseMapper;
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
        if(!userOpt.isPresent()) {
            return false;
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if(!appUser.isPresent()) {
            return false;
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(!courseOptional.isPresent()) {
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
        if(!userOpt.isPresent()) {
            return false;
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if(!appUser.isPresent()) {
            return false;
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(!courseOptional.isPresent()) {
            return false;
        }
        Course course = courseOptional.get();
        UserDetailsCourse userDetailsCourse = new UserDetailsCourse();
        userDetailsCourse.setCourse(course);
        userDetailsCourse.setAppUser(appUser.get());
        userDetailsCourse.setIsStarted(true);
        userDetailsCourseRepository.save(userDetailsCourse);

        List<Chapter> chapterList = chapterRepository.findAllByCourseOrderByOrderNrAsc(course);
        for(Chapter chapter : chapterList) {
            UserDetailsChapter userDetailsChapter = new UserDetailsChapter();
            userDetailsChapter.setAppUser(appUser.get());
            userDetailsChapter.setChapter(chapter);
            userDetailsChapterRepository.save(userDetailsChapter);

            lessonRepository.findAllByChapterOrderByOrderAsc(chapter).stream().peek(p -> {
               UserDetailsLesson userDetailsLesson = new UserDetailsLesson();
               userDetailsLesson.setAppUser(appUser.get());
               userDetailsLesson.setLesson(p);
               userDetailsLessonRepository.save(userDetailsLesson);
            });

        }

        return true;

    }

    public List<CourseDTO> getCourseRecommendationForLoggedUser() {

        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if(!userOpt.isPresent()) {
            return new ArrayList<>();
        }

        Optional<AppUser> appUser = appUserRepository.findAppUserByUser(userOpt.get());
        if(!appUser.isPresent()) {
            return new ArrayList<>();
        }

        List<UserDetailsCourse> userDetailsCourseList = userDetailsCourseRepository.findUserDetailsCourseByAppUser(appUser.get());

        Set<Similarity> similaritySet = new HashSet<>();
        for(UserDetailsCourse userDetailsCourse : userDetailsCourseList) {
            List<Similarity> currentCourseSimilarities = similarityRepository.findAllByCourseA(userDetailsCourse.getCourse());
            similaritySet.addAll(currentCourseSimilarities);
        }

        return similaritySet.stream()
            .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
            .limit(10).map(Similarity::getCourseB)
            .map(courseMapper::toDto)
            .collect(Collectors.toList());


    }

}
