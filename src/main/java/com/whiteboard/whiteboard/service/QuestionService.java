package com.whiteboard.whiteboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whiteboard.whiteboard.dto.QuestionDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Question;

import jakarta.servlet.http.HttpSession;

public interface QuestionService {
    
  // 신규글 등록 메서드
  void register(QuestionDTO dto, HttpSession session);

  // 검색
  List<QuestionDTO> searchQuestions(String searchQuery);

  // 페이징
  Page<QuestionDTO> findAllByOrderByQuestionNum(Pageable pageable);

  // 특정 게시물의 정보를 리턴하는 메서드 선언
  QuestionDTO get(Long questionNum);

  // 게시물 삭제 메서드
  void remove(long questionNum);

  public QuestionDTO getQuestionByQuestionNum(Long questionNum);

  // 설명: Entity 객체를 DTO로 변환하는 메서드
    default QuestionDTO entityToDTO(Question question){
        QuestionDTO dto = QuestionDTO.builder()
                        .questionNum(question.getQuestionNum())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .registerDate(question.getRegisterDate())
                        .nickname(question.getWriter().getNickname())
                        .build();

        return dto;
    }

    // dtoToEntity 변환 메서드 정의
    default Question dtoToEntity(QuestionDTO dto, HttpSession session){
        
        Member member1 = Member.builder().nickname(dto.getNickname()).build();

        Question question = Question.builder()
                        .questionNum(dto.getQuestionNum())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .writer(member1)
                        .build();

        return question;
    }
    //게시물 수정 메서드 선언
    void modify(QuestionDTO dto);
}
