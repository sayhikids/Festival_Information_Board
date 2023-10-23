package com.whiteboard.whiteboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.MemberDTO;
import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.entity.Festival;
import com.whiteboard.whiteboard.entity.FestivalReply;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FestivalReplyService {

  public final MemberRepository memberRepository;
  private final MemberService memberService;

  public List<ReplyDTO> getAllReply(Long festivalNum) {
        return null;
  }

  public List<ReplyDTO> register(ReplyDTO replyDTO) {
        return null;
  }

  public void modify(ReplyDTO dto) {

  }

  public void remove(Long rno) {
    
  }


  //DTO를 Entity로 변환하는 메서드 선언
	public FestivalReply convertToFestvialEntity(ReplyDTO replyDTO, HttpSession session) {
		
    //session 정보를 회원DTO로 받아오기
    MemberDTO memberDTO = memberService.covertSessionToDTO(session);
    
    //이메일정보 찍어서 DB에서 해당하는 회원Entity 가져오기
    Member member = memberRepository.getReferenceById(memberDTO.getEmail());

		//글번호가 필요하니 Festival Entity를 참조해야 한다.
    Festival festival = Festival.builder().festivalNum(replyDTO.getFestivalNum()).build();
		
    FestivalReply festivalReply = FestivalReply.builder()
															.from(festival)
                              //writer는 멤버 타입!
															.writer(member)
															.content(replyDTO.getContent())
															.replyLevel(replyDTO.getReplyLevel())
															.replyStep(replyDTO.getReplyStep())
															.build();
		return festivalReply;
	}
	

	//Entity를 DTO로 변환하는 메서드 선언
	public ReplyDTO convertToFestvialDTO(FestivalReply reply) {
		
		ReplyDTO festivaReplyDTO = ReplyDTO.builder()
              .festivalNum(reply.getFrom().getFestivalNum())
              //작성자 정보는 닉네임만 출력
					    .nickname(reply.getWriter().getNickname())
              .content(reply.getContent())
              .replyLevel(reply.getReplyLevel())
              .replyStep(reply.getReplyStep())
              //수정일자는 굳이 보여줄 필요 없음. 등록일만 출력
              .regDate(reply.getRegisterDate())
					    .build();
		return festivaReplyDTO;
	}
}
