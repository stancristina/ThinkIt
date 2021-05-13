package org.fmi.unibuc.repository;

import org.fmi.unibuc.domain.Chapter;
import org.fmi.unibuc.domain.Evaluation;
import org.fmi.unibuc.domain.Lesson;
import org.fmi.unibuc.domain.Question;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByEvaluation(Evaluation evaluation);
}
