package com.whiteboard.whiteboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whiteboard.whiteboard.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  // 특정 게시글에 댓글이 몇개 존재하는지 여부 쿼리 작성
  // n을 선택해서 리뷰넘만
  @Query("Select n From Review n Where n.reviewNum = :reviewNum")
  Review[] getReviewNum(@Param("reviewNum") Long reviewNum);

  @Query("Select n, w From Review n Left Join n.writer w Where n.reviewNum = :reviewNum")
  Review[] getReviewJoinMember(@Param("reviewNum") Long reviewNum);

  @Query("Select n From Review n")
  List<Review[]> getReviewList();

  @Query("SELECT n FROM Review n ORDER BY n.reviewNum ASC")
  List<Review> findAllByOrderByReviewNumAsc();

  // 리뷰 리스트 페이징
  @Query("SELECT n FROM Review n ORDER BY n.reviewNum ASC")
  Page<Review> findAllByOrderByReviewNum(Pageable pageable);

  // 리뷰 검색
  List<Review> findByTitleContaining(String searchQuery);

  // 조회수 누적
  @Modifying
  @Query("UPDATE Review r SET r.readCount=r.readCount+1 WHERE r.reviewNum = :reviewNum")
  void updateReadCount(@Param("reviewNum") Long reviewNum);


  
  // @Query(value = "Select r, w From Review r Left join b.writer w ")
  // //list 페이지에서 사용될 전체 목록 쿼리 이므로, Paging 처리를 위해 Page 객체로 리턴받도록 정의함
  // Page<Object[]> getReviewByPaging(Pageable pageable);
}
