package com.whiteboard.whiteboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whiteboard.whiteboard.entity.Review;
import com.whiteboard.whiteboard.entity.ReviewReply;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long>{
    //@Query("SELECT r FROM ReviewReply r WHERE r.reviewNum = :reviewNum ORDER BY r.reviewNum DESC")
    //List<ReviewReply> findAllOrderByReviewNumDesc(@Param("reviewNum") Long reviewNum);

    @Query("SELECT r FROM ReviewReply r WHERE r.reviewNum = :reviewNum ORDER BY r.replyNum DESC")
    List<ReviewReply> findAllByReviewNumOrderByReplyNumDesc(@Param("reviewNum") Review review);

    @Modifying
    @Query("Delete From ReviewReply r WHERE r.reviewNum = :reviewNum")
    void deleteByReviewNum(@Param("reviewNum") Review review);

    
  @Query("DELETE FROM ReviewReply rr WHERE rr.reviewNum = :reviewNum")
  void removeReplyByReviewNum(@Param("reviewNum") Review review);
}
