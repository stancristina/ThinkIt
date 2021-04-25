package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.repository.*;
import org.fmi.unibuc.service.UserDetailsChapterService;
import org.fmi.unibuc.service.dto.UserDetailsChapterDTO;
import org.fmi.unibuc.service.mapper.UserDetailsChapterMapper;
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
 * Service Implementation for managing {@link UserDetailsChapter}.
 */
@Service
@Transactional
public class UserDetailsChapterServiceImpl implements UserDetailsChapterService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsChapterServiceImpl.class);

    private final UserDetailsChapterRepository userDetailsChapterRepository;

    private final UserDetailsChapterMapper userDetailsChapterMapper;


    public UserDetailsChapterServiceImpl(UserDetailsChapterRepository userDetailsChapterRepository, UserDetailsChapterMapper userDetailsChapterMapper) {
        this.userDetailsChapterRepository = userDetailsChapterRepository;
        this.userDetailsChapterMapper = userDetailsChapterMapper;
    }

    @Override
    public UserDetailsChapterDTO save(UserDetailsChapterDTO userDetailsChapterDTO) {
        log.debug("Request to save UserDetailsChapter : {}", userDetailsChapterDTO);
        UserDetailsChapter userDetailsChapter = userDetailsChapterMapper.toEntity(userDetailsChapterDTO);
        userDetailsChapter = userDetailsChapterRepository.save(userDetailsChapter);
        return userDetailsChapterMapper.toDto(userDetailsChapter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsChapterDTO> findAll() {
        log.debug("Request to get all UserDetailsChapters");
        return userDetailsChapterRepository.findAll().stream()
            .map(userDetailsChapterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetailsChapterDTO> findOne(Long id) {
        log.debug("Request to get UserDetailsChapter : {}", id);
        return userDetailsChapterRepository.findById(id)
            .map(userDetailsChapterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDetailsChapter : {}", id);
        userDetailsChapterRepository.deleteById(id);
    }
}
