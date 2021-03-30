package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.service.WordService;
import org.fmi.unibuc.domain.Word;
import org.fmi.unibuc.repository.WordRepository;
import org.fmi.unibuc.service.dto.WordDTO;
import org.fmi.unibuc.service.mapper.WordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Word}.
 */
@Service
@Transactional
public class WordServiceImpl implements WordService {

    private final Logger log = LoggerFactory.getLogger(WordServiceImpl.class);

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    public WordServiceImpl(WordRepository wordRepository, WordMapper wordMapper) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    @Override
    public WordDTO save(WordDTO wordDTO) {
        log.debug("Request to save Word : {}", wordDTO);
        Word word = wordMapper.toEntity(wordDTO);
        word = wordRepository.save(word);
        return wordMapper.toDto(word);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordDTO> findAll() {
        log.debug("Request to get all Words");
        return wordRepository.findAll().stream()
            .map(wordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WordDTO> findOne(Long id) {
        log.debug("Request to get Word : {}", id);
        return wordRepository.findById(id)
            .map(wordMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Word : {}", id);
        wordRepository.deleteById(id);
    }
}
