package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.MemberDTO;
import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.dto.PageResultDTO;
import com.whiteboard.whiteboard.dto.ReviewDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Review;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.ReviewReplyRepository;
import com.whiteboard.whiteboard.repository.ReviewRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;
  private final MemberService memberService;
  private final ReviewReplyRepository reviewReplyRepository;

  private List<ReviewDTO> searchResults = new ArrayList<>();

  @Override
  public List<ReviewDTO> getAllReviews() {

    List<ReviewDTO> reviewDTOs = new ArrayList<>();

    List<Review> reviewList = reviewRepository.findAll();
    for (Review review : reviewList) {
      reviewDTOs.add(entityToDTO(review));
    }

    return reviewDTOs;
  }

  @Override
  public void updateReview(Long reviewId, ReviewDTO reviewDTO) {

  }

  @Transactional
  @Override
  public void remove(Long reviewNum) {
    Review review = reviewRepository.getReferenceById(reviewNum);
    reviewReplyRepository.deleteByReviewNum(review);
    reviewRepository.deleteById(reviewNum);
  }

  @Override
  public PageResultDTO<ReviewDTO, Object[]> getReviewDTOList(PageRequestDTO pageRequestDTO) {

    return null;
  }

  @Override
  public List<ReviewDTO[]> getReviewDTOs() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getReviewDTOs'");
  }

  @Override
  public void saveReview(ReviewDTO dto, HttpSession session) {
    dto.setGoodCount(0L);
    dto.setReadCount(0L);
    Review review = dtoToEntity(dto, session);
    reviewRepository.save(review);
    System.out.println("글 DB에 저장 성공");
    // Review saveReview = reviewRepository.save(review);
    // return saveReview.getReviewNum();

  }

  @Transactional
  @Override
  public void modify(ReviewDTO dto) {
    Review review = reviewRepository.getReferenceById(dto.getReviewNum());

    review.updateContent(dto.getContent());
    review.updateTitle(dto.getTitle());

    reviewRepository.save(review);

  }

  // Review review = reviewRepository.getReferenceById(dto.getReviewNum());

  // review.setContent(dto.getContent());
  // review.setTitle(dto.getTitle());

  // reviewRepository.save(review);

  @Override
  public ReviewDTO getReviewByReviewNum(Long reviewNum) {
    Review review = reviewRepository.findById(reviewNum)
        .orElseThrow(() -> new NoSuchElementException(reviewNum + "인 id 리뷰를 찾을 수 없습니다."));
    return entityToDTO(review);
  }

  public Review dtoToEntity(ReviewDTO reviewDTO, HttpSession session) {

    MemberDTO memberDTO = memberService.covertSessionToDTO(session);

    Member member = memberRepository.getReferenceById(memberDTO.getEmail());

    Review review = Review.builder()
        // 리뷰 엔티티의 writer는 Member타입임!!!!!!!!!!!!!!!
        .writer(member)
        .title(reviewDTO.getTitle())
        .content(reviewDTO.getContent())
        .readCount(reviewDTO.getReadCount())
        .goodCount(reviewDTO.getGoodCount())
        .build();
    return review;
  }

  // 리뷰페이징
  @Override
  public Page<ReviewDTO> findAllByOrderByReviewNum(Pageable pageable) {
    Page<Review> reviewPage = reviewRepository.findAllByOrderByReviewNum(pageable);
    return reviewPage.map(review -> entityToDTO(review));
  }

  // 축제 검색
  @Override
  public List<ReviewDTO> searchReviews(String searchQuery) {
    List<Review> reviews = reviewRepository.findByTitleContaining(searchQuery);

    List<ReviewDTO> reviewDTOs = reviews.stream()
        .map(this::entityToDTO)
        .collect(Collectors.toList());

    searchResults = reviewDTOs;

    return reviewDTOs;
  }

  @Override
  public PageResultDTO<ReviewDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    return null;

  }

  // 조회수 누적
  @Transactional
  @Override
  public void updateReadCount(Long reviewNum) {
      reviewRepository.updateReadCount(reviewNum);
  }
}
