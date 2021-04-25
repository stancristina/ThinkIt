package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.repository.AppUserRepository;
import org.fmi.unibuc.repository.LessonRepository;
import org.fmi.unibuc.repository.UserRepository;
import org.fmi.unibuc.service.UserDetailsLessonService;
import org.fmi.unibuc.repository.UserDetailsLessonRepository;
import org.fmi.unibuc.service.dto.UserDetailsLessonDTO;
import org.fmi.unibuc.service.mapper.UserDetailsLessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserDetailsLesson}.
 */
@Service
@Transactional
public class UserDetailsLessonServiceImpl implements UserDetailsLessonService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsLessonServiceImpl.class);

    private final UserDetailsLessonRepository userDetailsLessonRepository;

    private final UserDetailsLessonMapper userDetailsLessonMapper;

    public UserDetailsLessonServiceImpl(UserDetailsLessonRepository userDetailsLessonRepository, UserDetailsLessonMapper userDetailsLessonMapper, AppUserRepository appUserRepository, UserRepository userRepository, LessonRepository lessonRepository) {
        this.userDetailsLessonRepository = userDetailsLessonRepository;
        this.userDetailsLessonMapper = userDetailsLessonMapper;
    }

    @Override
    public UserDetailsLessonDTO save(UserDetailsLessonDTO userDetailsLessonDTO) {
        log.debug("Request to save UserDetailsLesson : {}", userDetailsLessonDTO);
        UserDetailsLesson userDetailsLesson = userDetailsLessonMapper.toEntity(userDetailsLessonDTO);
        userDetailsLesson = userDetailsLessonRepository.save(userDetailsLesson);
        return userDetailsLessonMapper.toDto(userDetailsLesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsLessonDTO> findAll() {
        log.debug("Request to get all UserDetailsLessons");
        return userDetailsLessonRepository.findAll().stream()
            .map(userDetailsLessonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetailsLessonDTO> findOne(Long id) {
        log.debug("Request to get UserDetailsLesson : {}", id);
        return userDetailsLessonRepository.findById(id)
            .map(userDetailsLessonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDetailsLesson : {}", id);
        userDetailsLessonRepository.deleteById(id);
    }
}
