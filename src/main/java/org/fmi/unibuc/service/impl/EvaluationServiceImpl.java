package org.fmi.unibuc.service.impl;

import org.fmi.unibuc.service.EvaluationService;
import org.fmi.unibuc.domain.Evaluation;
import org.fmi.unibuc.repository.EvaluationRepository;
import org.fmi.unibuc.service.dto.EvaluationDTO;
import org.fmi.unibuc.service.mapper.EvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Evaluation}.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    private final EvaluationRepository evaluationRepository;

    private final EvaluationMapper evaluationMapper;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, EvaluationMapper evaluationMapper) {
        this.evaluationRepository = evaluationRepository;
        this.evaluationMapper = evaluationMapper;
    }

    @Override
    public EvaluationDTO save(EvaluationDTO evaluationDTO) {
        log.debug("Request to save Evaluation : {}", evaluationDTO);
        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        evaluation = evaluationRepository.save(evaluation);
        return evaluationMapper.toDto(evaluation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationDTO> findAll() {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll().stream()
            .map(evaluationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the evaluations where Course is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<EvaluationDTO> findAllWhereCourseIsNull() {
        log.debug("Request to get all evaluations where Course is null");
        return StreamSupport
            .stream(evaluationRepository.findAll().spliterator(), false)
            .filter(evaluation -> evaluation.getCourse() == null)
            .map(evaluationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationDTO> findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        return evaluationRepository.findById(id)
            .map(evaluationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.deleteById(id);
    }
}
