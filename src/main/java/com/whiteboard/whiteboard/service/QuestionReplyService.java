package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Question;
import com.whiteboard.whiteboard.entity.QuestionReply;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.QuestionReplyRepository;
import com.whiteboard.whiteboard.repository.QuestionRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionReplyService {

  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final QuestionRepository questionRepository;
  private final QuestionReplyRepository questionReplyRepository;

  public Long save(ReplyDTO replyDTO, HttpSession session) {
      //부모엔티티(Question) 조회
      Optional<Question> optionalQuestionEntity = questionRepository.findById(replyDTO.getQuestionNum());
      if(optionalQuestionEntity.isPresent()) {
        Question question = optionalQuestionEntity.get();
        QuestionReply replyEntity = convertToReplyEntity(replyDTO, question, session);
        return questionReplyRepository.save(replyEntity).getReplyNum();
      }
      return null;
  }
    
public List<ReplyDTO> findAll(Long questionNum) {
          List<QuestionReply> replyEntityList = questionReplyRepository
                        .findAllByQuestionNumOrderByReplyNumDesc(questionRepository.getReferenceById(questionNum));
          /* EntityList -> DTOList */
          List<ReplyDTO> replyDTOList = new ArrayList<>();
          for (QuestionReply replyEntity: replyEntityList) {
              ReplyDTO replyDTO = convertToReplyDTO(replyEntity, questionNum);
              replyDTOList.add(replyDTO);
          }
          return replyDTOList;
    }

    public ReplyDTO convertToReplyDTO(QuestionReply entity, Long questionNum){

        ReplyDTO dto = new ReplyDTO();
        dto.setQuestionNum(questionNum);
        dto.setReplyNum(entity.getReplyNum());
        dto.setNickname(entity.getWriter().getNickname());
        dto.setContent(entity.getContent());
        //dto.setReplyLevel(entity.getReplyLevel());
        //dto.setReplyStep(entity.getReplyStep());
        dto.setRegDate(entity.getRegisterDate());
        dto.setModDate(entity.getModifyDate());

        return dto;
    }

    public QuestionReply convertToReplyEntity(ReplyDTO reply, Question question, HttpSession session){

        Member member = memberRepository.getReferenceById(memberService.covertSessionToDTO(session).getEmail());

        QuestionReply entity = QuestionReply.builder()
                            //.replyNum(reply.getReplyNum())
                            .questionNum(question)
                            //.replyLevel(reply.getReplyLevel())
                            //.replyStep(reply.getReplyStep())
                            .writer(member)
                            .content(reply.getContent())
                            .build();

        return entity;
    }
}

