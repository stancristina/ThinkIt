package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.service.SimilarityService;
import org.fmi.unibuc.domain.Similarity;
import org.fmi.unibuc.repository.SimilarityRepository;
import org.fmi.unibuc.service.dto.SimilarityDTO;
import org.fmi.unibuc.service.mapper.SimilarityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Similarity}.
 */
@Service
@Transactional
public class SimilarityServiceImpl implements SimilarityService {

    private final Logger log = LoggerFactory.getLogger(SimilarityServiceImpl.class);

    private final SimilarityRepository similarityRepository;

    private final SimilarityMapper similarityMapper;

    public SimilarityServiceImpl(SimilarityRepository similarityRepository, SimilarityMapper similarityMapper) {
        this.similarityRepository = similarityRepository;
        this.similarityMapper = similarityMapper;
    }

    @Override
    public SimilarityDTO save(SimilarityDTO similarityDTO) {
        log.debug("Request to save Similarity : {}", similarityDTO);
        Similarity similarity = similarityMapper.toEntity(similarityDTO);
        similarity = similarityRepository.save(similarity);
        return similarityMapper.toDto(similarity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimilarityDTO> findAll() {
        log.debug("Request to get all Similarities");
        return similarityRepository.findAll().stream()
            .map(similarityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SimilarityDTO> findOne(Long id) {
        log.debug("Request to get Similarity : {}", id);
        return similarityRepository.findById(id)
            .map(similarityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Similarity : {}", id);
        similarityRepository.deleteById(id);
    }
}
