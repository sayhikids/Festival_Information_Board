package com.whiteboard.whiteboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whiteboard.whiteboard.entity.Question;
import com.whiteboard.whiteboard.entity.QuestionReply;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, Long> {
  @Query("SELECT q FROM QuestionReply q WHERE q.questionNum = :questionNum ORDER BY q.replyNum DESC")
  List<QuestionReply> findAllByQuestionNumOrderByReplyNumDesc(@Param("questionNum") Question question);

  @Modifying
  @Query("Delete From QuestionReply q WHERE q.questionNum = :questionNum")
  void deleteByQuestionNum(@Param("questionNum") Question question);
}
