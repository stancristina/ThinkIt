package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.service.UserDetailsCourseService;
import org.fmi.unibuc.domain.UserDetailsCourse;
import org.fmi.unibuc.repository.UserDetailsCourseRepository;
import org.fmi.unibuc.service.dto.UserDetailsCourseDTO;
import org.fmi.unibuc.service.mapper.UserDetailsCourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserDetailsCourse}.
 */
@Service
@Transactional
public class UserDetailsCourseServiceImpl implements UserDetailsCourseService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsCourseServiceImpl.class);

    private final UserDetailsCourseRepository userDetailsCourseRepository;

    private final UserDetailsCourseMapper userDetailsCourseMapper;

    public UserDetailsCourseServiceImpl(UserDetailsCourseRepository userDetailsCourseRepository, UserDetailsCourseMapper userDetailsCourseMapper) {
        this.userDetailsCourseRepository = userDetailsCourseRepository;
        this.userDetailsCourseMapper = userDetailsCourseMapper;
    }

    @Override
    public UserDetailsCourseDTO save(UserDetailsCourseDTO userDetailsCourseDTO) {
        log.debug("Request to save UserDetailsCourse : {}", userDetailsCourseDTO);
        UserDetailsCourse userDetailsCourse = userDetailsCourseMapper.toEntity(userDetailsCourseDTO);
        userDetailsCourse = userDetailsCourseRepository.save(userDetailsCourse);
        return userDetailsCourseMapper.toDto(userDetailsCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsCourseDTO> findAll() {
        log.debug("Request to get all UserDetailsCourses");
        return userDetailsCourseRepository.findAll().stream()
            .map(userDetailsCourseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetailsCourseDTO> findOne(Long id) {
        log.debug("Request to get UserDetailsCourse : {}", id);
        return userDetailsCourseRepository.findById(id)
            .map(userDetailsCourseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDetailsCourse : {}", id);
        userDetailsCourseRepository.deleteById(id);
    }
}
