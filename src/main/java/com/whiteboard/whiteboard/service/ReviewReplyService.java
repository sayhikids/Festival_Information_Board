package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Review;
import com.whiteboard.whiteboard.entity.ReviewReply;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.ReviewReplyRepository;
import com.whiteboard.whiteboard.repository.ReviewRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewReplyService {

  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewReplyRepository reviewReplyRepository;

  public Long save(ReplyDTO replyDTO, HttpSession session) {
      //부모엔티티(Review) 조회
      Optional<Review> optionalReviewEntity = reviewRepository.findById(replyDTO.getReviewNum());
      if(optionalReviewEntity.isPresent()) {
        Review review = optionalReviewEntity.get();
        ReviewReply replyEntity = convertToReplyEntity(replyDTO, review, session);
        return reviewReplyRepository.save(replyEntity).getReplyNum();
      }
      return null;
  }
    
public List<ReplyDTO> findAll(Long reviewNum) {
          List<ReviewReply> replyEntityList = reviewReplyRepository
                        .findAllByReviewNumOrderByReplyNumDesc(reviewRepository.getReferenceById(reviewNum));
          /* EntityList -> DTOList */
          List<ReplyDTO> replyDTOList = new ArrayList<>();
          for (ReviewReply replyEntity: replyEntityList) {
              ReplyDTO replyDTO = convertToReplyDTO(replyEntity, reviewNum);
              replyDTOList.add(replyDTO);
          }
          return replyDTOList;
    }

    public ReplyDTO convertToReplyDTO(ReviewReply entity, Long reviewNum){

        ReplyDTO dto = new ReplyDTO();
        dto.setReviewNum(reviewNum);
        dto.setReplyNum(entity.getReplyNum());
        dto.setNickname(entity.getWriter().getNickname());
        dto.setContent(entity.getContent());
        //dto.setReplyLevel(entity.getReplyLevel());
        //dto.setReplyStep(entity.getReplyStep());
        dto.setRegDate(entity.getRegisterDate());
        dto.setModDate(entity.getModifyDate());

        return dto;
    }

    public ReviewReply convertToReplyEntity(ReplyDTO reply, Review review, HttpSession session){

        Member member = memberRepository.getReferenceById(memberService.covertSessionToDTO(session).getEmail());

        ReviewReply entity = ReviewReply.builder()
                            //.replyNum(reply.getReplyNum())
                            .reviewNum(review)
                            .replyLevel(reply.getReplyLevel())
                            .replyStep(reply.getReplyStep())
                            .writer(member)
                            .content(reply.getContent())
                            .build();

        return entity;
    }
}

