package com.whiteboard.whiteboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.dto.PageResultDTO;
import com.whiteboard.whiteboard.dto.ReviewDTO;
import com.whiteboard.whiteboard.entity.Review;

import jakarta.servlet.http.HttpSession;

public interface ReviewService {

  //Long likePost(ReviewDTO dto);

  // 새로운 리뷰 생성
  void saveReview(ReviewDTO reviewDTO, HttpSession session);

  List<ReviewDTO> getAllReviews() ;

  List<ReviewDTO[]> getReviewDTOs();

  PageResultDTO<ReviewDTO, Object[]> getReviewDTOList(PageRequestDTO pageRequestDTO);

 // String getNickname(Review review, Member member);

  // 리뷰의 reviewNum으로 리뷰 가져오기
  ReviewDTO getReviewByReviewNum(Long reviewNum);


  // 기존 리뷰 업데이트
  void updateReview(Long reviewNum, ReviewDTO reviewDTO);

  // 리뷰의 ID로 리뷰 삭제
  void remove(Long reviewNum);

  // 게시물 수정 메서드 선언
  void modify(ReviewDTO dto);

  //리뷰페이징
  Page<ReviewDTO> findAllByOrderByReviewNum(Pageable pageable);

  //서치를 위해 메서드 정의
  List<ReviewDTO> searchReviews(String searchQuery);

  PageResultDTO<ReviewDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  //조회수 1회 누적
  void updateReadCount(Long reviewNum);

  //좋아요 수
 // Long likeReview(ReviewDTO dto);






  
  default public ReviewDTO entityToDTO(Review review) {
     ReviewDTO dto = ReviewDTO.builder()
        .reviewNum(review.getReviewNum())
        .writer(review.getWriter().getEmail())
        .nickname(review.getWriter().getNickname())
        .title(review.getTitle())
        .content(review.getContent())
        .readCount(0L)
        .goodCount(0L)
        // .festivalNum1(review.getFestivalNum1())
        // .festivalNum2(review.getFestivalNum2())
        // .festivalNum3(review.getFestivalNum3())
        // .festivalNum4(review.getFestivalNum4())
        // .festivalNum5(review.getFestivalNum5())
        .hashTag1(review.getHashTag1())
        .hashTag2(review.getHashTag2())
        .hashTag3(review.getHashTag3())
        .hashTag4(review.getHashTag4())
        .hashTag5(review.getHashTag5())
        .build();
    return dto;
  }
}