package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.Chapter;
import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.repository.ChapterRepository;
import org.fmi.unibuc.repository.CourseRepository;
import org.fmi.unibuc.repository.LessonRepository;
import org.fmi.unibuc.service.CustomService;
import org.fmi.unibuc.service.dto.ExtendedChapterDTO;
import org.fmi.unibuc.service.dto.LessonDTO;
import org.fmi.unibuc.service.mapper.LessonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    private final LessonRepository lessonRepository;

    private final ChapterRepository chapterRepository;

    private final CourseRepository courseRepository;

    private final LessonMapper lessonMapper;

    public CustomServiceImpl(LessonRepository lessonRepository, ChapterRepository chapterRepository, CourseRepository courseRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
        this.courseRepository = courseRepository;
        this.lessonMapper = lessonMapper;
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
}
