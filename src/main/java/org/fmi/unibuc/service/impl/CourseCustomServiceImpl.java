package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.Course;
import org.fmi.unibuc.repository.CourseRepository;
import org.fmi.unibuc.service.CourseCustomService;
import org.fmi.unibuc.service.dto.CourseCustomDTO;
import org.fmi.unibuc.service.mapper.CourseCustomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseCustomServiceImpl implements CourseCustomService {

    private final Logger log = LoggerFactory.getLogger(CourseCustomServiceImpl.class);

    private final CourseRepository courseRepository;

    private final CourseCustomMapper courseCustomMapper;

    public CourseCustomServiceImpl(CourseRepository courseRepository, CourseCustomMapper courseCustomMapper) {
        this.courseRepository = courseRepository;
        this.courseCustomMapper = courseCustomMapper;
    }

    @Override
    public CourseCustomDTO save(CourseCustomDTO courseCustomDTO) {

        log.debug("Request to save Course : {}", courseCustomDTO);
        Course course = courseCustomMapper.toEntity(courseCustomDTO);
        course = courseRepository.save(course);
        return courseCustomMapper.toDto(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseCustomDTO> findAll() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll().stream()
            .map(courseCustomMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseCustomDTO> findOne(Long id) {

        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id)
            .map(courseCustomMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
