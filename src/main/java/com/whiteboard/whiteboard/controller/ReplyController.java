package com.whiteboard.whiteboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whiteboard.whiteboard.dto.QuestionDTO;
import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.dto.ReviewDTO;
import com.whiteboard.whiteboard.service.FestivalReplyService;
import com.whiteboard.whiteboard.service.QuestionReplyService;
import com.whiteboard.whiteboard.service.QuestionService;
import com.whiteboard.whiteboard.service.ReviewReplyService;
import com.whiteboard.whiteboard.service.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final FestivalReplyService festivalReplyService;
    private final ReviewService reviewService;
    private final ReviewReplyService reviewReplyService;
    private final QuestionService questionService;
    private final QuestionReplyService questionReplyService; 
    
    // ResponseEntity는 Spring Framework에서 사용되는 클래스로, HTTP 응답을 생성하고 반환하는 데 사용
    // 클라이언트에게 HTTP 응답을 보내는 데 필요한 모든 정보를 포함
    // 응답의 상태 코드와 내용을 설정할 수 있어 클라이언트에게 적절한 응답을 보내는 데 유용
    // HTTP 상태 코드, 응답 본문, 헤더 등을 설정할 수 있는 방법을 제공

    //리뷰글에 댓글 작성 POST 메서드
    @PostMapping("/review/save")
    public String saveReviewReply(@RequestParam("reviewNum") Long reviewNum, @ModelAttribute ReplyDTO replyDTO,
    @ModelAttribute ReviewDTO reviewDTO, RedirectAttributes attributes ,HttpSession session) {
        //System.out.println("replyDTO = " + replyDTO);

        //댓글 등록
        System.out.println("11111111111111111111111111111111111111111111 : "+reviewDTO);
        reviewReplyService.save(replyDTO, session);
        
        reviewDTO = reviewService.getReviewByReviewNum(reviewNum);
        System.out.println("22222222222222222222222222222222222222222222 : "+reviewDTO);
        //attributes.addFlashAttribute("reviewNum",reviewDTO.getReviewNum());
        // // System.out.println("!GGGGGGGGGGGGGGGGGGGGGGGGGGGGG리뷰DTO : " + reviewDTO);
        // // System.out.println("!GGGGGGGGGGGGGGGGGGGGGGGGGGGGG리뷰넘 : " + reviewNum);
        // // System.out.println("!GGGGGGGGGGGGGGGGGGGGGGGGGGGGG리플라이 : " + replyDTO);
        //attributes.addAttribute(reviewDTO);
        attributes.addFlashAttribute("reviewDTO",reviewDTO);
        
        return "redirect:/review/reviewDetail";
    }


    //질문글에 댓글 작성 POST 메서드
    @PostMapping("/notice/replySave")
    public String saveQuestionReply(@RequestParam("questionNum") Long questionNum, @ModelAttribute ReplyDTO replyDTO,
    @ModelAttribute QuestionDTO dto, RedirectAttributes attributes ,HttpSession session) {
        System.out.println("*********************************댓글작성 시 댓글DTO" + replyDTO);
        System.out.println("*********************************댓글작성 시 질문DTO" + dto);
        System.out.println("*********************************댓글작성 시 SESSION" + session.getAttribute("loggedInUser"));

        //댓글 등록
        System.out.println("11111111111111111111111111111111111111111111 : "+dto);
        questionReplyService.save(replyDTO, session);
        dto = questionService.getQuestionByQuestionNum(questionNum);

        System.out.println("22222222222222222222222222222222222222222222 : "+dto);
        attributes.addFlashAttribute("dto", dto);
        
        return "redirect:/notice/questionDetail";
    }

    
  
 
}
