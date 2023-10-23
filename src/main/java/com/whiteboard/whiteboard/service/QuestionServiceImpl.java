package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.MemberDTO;
import com.whiteboard.whiteboard.dto.QuestionDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Question;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.QuestionReplyRepository;
import com.whiteboard.whiteboard.repository.QuestionRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionReplyRepository questionReplyRepository;
  private final MemberService memberService;
  private final MemberRepository memberRepository;

  private List<QuestionDTO> searchResults = new ArrayList<>();


  @Override
  public void register(QuestionDTO dto, HttpSession session) {
    Question question = dtoToEntity(dto, session);
    questionRepository.save(question);

    //return question.getQuestionNum();
  }

  @Override
  public List<QuestionDTO> searchQuestions(String searchQuery) {
    List<Question> questions = questionRepository.findByTitleContaining(searchQuery);
    List<QuestionDTO> questionDTOs = questions.stream().map(this::entityToDTO).collect(Collectors.toList());
    searchResults = questionDTOs;
    return questionDTOs;
  }

  @Override
  public Page<QuestionDTO> findAllByOrderByQuestionNum(Pageable pageable) {
    Page<Question> questionPage = questionRepository.findAllByOrderByQuestionNum(pageable);
    return questionPage.map(question -> entityToDTO(question));
  }

  @Override
  public QuestionDTO get(Long questionNum) {
    Question question = questionRepository.getQuestionByquestionNum(questionNum);
    return entityToDTO(question);
  }

  @Transactional
  @Override
  public void remove(long questionNum) {
    //댓글이 있는 경우 댓글 먼저 삭제하고 리뷰글 삭제해야 함
    questionReplyRepository.deleteByQuestionNum(questionRepository.getReferenceById(questionNum));
    
    // 리뷰 ID로 리뷰 삭제
    questionRepository.deleteById(questionNum);
  }

  @Override
  public void modify(QuestionDTO dto) {
    System.out.println("모디파이 메서드 ~~!!!!!!!!!!!!!!!!!!!!!!!!!!!" + dto);
    Question question = questionRepository.getReferenceById(dto.getQuestionNum());
    
    question.updateContent(dto.getContent());
    question.updateTitle(dto.getTitle());
    
    questionRepository.save(question);
  }
  
  public Question dtoToEntity(QuestionDTO questionDTO, HttpSession session) {
  
  MemberDTO memberDTO = memberService.covertSessionToDTO(session);
  
  Member member = memberRepository.getReferenceById(memberDTO.getEmail());
  
  Question question = Question.builder()
  //리뷰 엔티티의 writer는 Member타입임!!!!!!!!!!!!!!!
  .writer(member)
  .title(questionDTO.getTitle())
  .content(questionDTO.getContent())
  .build();
    return question;
  }

  @Override
  public QuestionDTO getQuestionByQuestionNum(Long questionNum) {
    Question question = questionRepository.findById(questionNum)
        .orElseThrow(() -> new NoSuchElementException(questionNum + "인 id 리뷰를 찾을 수 없습니다."));
    return entityToDTO(question);
  }
  
}
