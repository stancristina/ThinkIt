package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.Chapter;
import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.repository.ChapterRepository;
import org.fmi.unibuc.repository.CourseRepository;
import org.fmi.unibuc.service.LessonService;
import org.fmi.unibuc.domain.Lesson;
import org.fmi.unibuc.repository.LessonRepository;
import org.fmi.unibuc.service.dto.LessonDTO;
import org.fmi.unibuc.service.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Lesson}.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public LessonDTO save(LessonDTO lessonDTO) {
        log.debug("Request to save Lesson : {}", lessonDTO);
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> findAll() {
        log.debug("Request to get all Lessons");
        return lessonRepository.findAll().stream()
            .map(lessonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LessonDTO> findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        return lessonRepository.findById(id)
            .map(lessonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.deleteById(id);
    }
}
