package com.whiteboard.whiteboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whiteboard.whiteboard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
@Query("SELECT q, m FROM Question q LEFT JOIN q.writer m WHERE q.questionNum = :questionNum")
Object getQuestionWithWriter(@Param("questionNum") Long questionNum);

@Query("SELECT q, m FROM Question q JOIN q.writer m")
Page<Object[]> getQuestionWithWriter(Pageable pageable);


@Query("Select q From Question q Where q.questionNum = :questionNum")
Question getQuestionByquestionNum(@Param("questionNum") Long questionNum);


@Query("Select q, m From Question q LEFT JOIN q.writer m")
List<Question> getQuestionList();

@Query(value = "Select q, w From Question q LEFT JOIN q.writer w")
Page<Object[]> getQuestionpage(Pageable pageable);

@Query("SELECT q FROM Question q ORDER BY q.questionNum DESC")
Page<Question> findAllByOrderByQuestionNum(Pageable pageable);

List<Question> findByTitleContaining(String searchQuery);
}
