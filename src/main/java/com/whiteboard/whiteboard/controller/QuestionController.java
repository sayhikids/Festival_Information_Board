package com.whiteboard.whiteboard.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.dto.QuestionDTO;
import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.service.QuestionReplyService;
import com.whiteboard.whiteboard.service.QuestionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class QuestionController {
  private final QuestionService questionService;
  private final QuestionReplyService questionReplyService;

  // 검색 기능 및 페이징을 위한 메서드
  @GetMapping("/question")
  public String question(
      @RequestParam(name = "searchQuery", required = false) String searchQuery,
      PageRequestDTO pageRequestDTO, Model model) {

    List<QuestionDTO> questions = null; // 축제 목록을 담을 변수
    boolean isSearch = false; // 검색 여부를 나타내는 변수

    if (searchQuery != null && !searchQuery.isEmpty()) {
      // 검색어가 제공된 경우, 해당 검색어를 사용하여 축제를 검색합니다.
      questions = questionService.searchQuestions(searchQuery); // 검색된 축제 목록을 가져옴
      isSearch = true; // 검색 여부 플래그를 true로 설정
    } else {
      // 검색어가 없는 경우, 페이징을 위한 로직을 수행합니다.

      // 페이지 요청 정보(pageRequestDTO)를 이용하여 페이지 관련 정보를 가져오고
      Pageable pageable = pageRequestDTO.getPageable(Sort.by("questionNum").descending());

      // 서비스 계층을 통해 페스티벌 데이터를 페이지네이션하여 가져옵니다.
      Page<QuestionDTO> questionPage = questionService.findAllByOrderByQuestionNum(pageable);
      questions = questionPage.getContent(); // 현재 페이지의 축제 목록을 가져옴

      // 총 페이지 수 계산
      int totalPages = questionPage.getTotalPages();

      // 현재 페이지 번호 가져오기
      int currentPage = pageRequestDTO.getPage();

      // 페이지 번호 목록 생성 (고정된 3개 페이지만 표시)
      // 현재 페이지를 중심으로 앞뒤 1페이지만 보여주도록 제한
      List<Integer> pageNumbers = IntStream
          .rangeClosed(Math.max(1, currentPage - 1), Math.min(currentPage + 1, totalPages))
          .boxed()
          .collect(Collectors.toList());

      // 이전 페이지와 다음 페이지 버튼을 제어하기 위한 조건 설정
      boolean hasPrevPage = currentPage > 1; // 현재 페이지가 1보다 크면 이전 페이지가 있다는 의미
      boolean hasNextPage = currentPage < totalPages; // 현재 페이지가 총 페이지 수보다 작으면 다음 페이지가 있다는 의미

      // 이전 페이지 번호와 다음 페이지 번호 계산
      int prevPageNumber = currentPage - 1;
      int nextPageNumber = currentPage + 1;

      // 모델에 페이지 관련 데이터를 추가하여 뷰로 전달
      model.addAttribute("pageNumbers", pageNumbers); // 페이지 번호 목록
      model.addAttribute("hasPrevPage", hasPrevPage); // 이전 페이지 버튼 제어를 위한 조건
      model.addAttribute("hasNextPage", hasNextPage); // 다음 페이지 버튼 제어를 위한 조건
      model.addAttribute("prevPageNumber", prevPageNumber); // 이전 페이지 번호
      model.addAttribute("nextPageNumber", nextPageNumber); // 다음 페이지 번호
    }

    model.addAttribute("result", questions); // 축제 목록을 모델에 추가
    model.addAttribute("searchQuery", searchQuery); // 검색어를 모델에 추가
    model.addAttribute("isSearch", isSearch); // 검색 여부를 모델에 추가

    return "/notice/question"; // "festivalList.html" 페이지로 이동
  }

  @GetMapping("/questionDetail")
  public void questionDetail(@ModelAttribute("dto") QuestionDTO dto, Model model, HttpSession session) {
    
    //해당 질문글의 데이터 DB에서 모두 가져오기
    dto = questionService.get(dto.getQuestionNum());
    System.out.println("질문상세의 질문DTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + dto);
    // 질문번호, 닉네임, 제목, 내용, 등록날짜 매핑됨.
    
    System.out.println("//////////질문상세의 Session/////////////////////////////////////" + session.getAttribute("loggedInUser"));
    //댓글 목록 가져오기
    List<ReplyDTO> replyList  = questionReplyService.findAll(dto.getQuestionNum());
    System.out.println("~~~~~~~~~~~~~~~~~~~질문상세의 질문replyList~~~~~~~~~~~"+ replyList);


    model.addAttribute("dto", dto);
    model.addAttribute("replyList", replyList);
    model.addAttribute("session", session);

  }


  // 신규글등록폼 요청처리
  @GetMapping("/questionWrite")
  public String questionWrite(@ModelAttribute QuestionDTO dto, RedirectAttributes attributes, HttpSession session) {
    // System.out.println("Session 아이디 확인!! : " +
    // session.getAttribute("loggedInUser"));
    String alertMessage = "";

    if (session.getAttribute("loggedInUser") != null) { // 로그인한 경우

      return "/notice/questionWrite"; // 작성페이지 띄우기

    } else {// 로그인하지 않은 경우
      alertMessage = "로그인한 회원만 글 작성 가능합니다.";
      attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";
    }
  }

  // 글쓰기 에디터 사용 시 자동 삽입되는 html 태그 제거하는 메서드
  public static String removeHtmlTags(String input) {
    if (input == null) {
      return "";
    }
    // 정규 표현식을 사용하여 HTML 태그를 제거하고 기호를 대체합니다.
    String regex = "<[^>]*>";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);
    return matcher.replaceAll("").replace("&lt;", "<").replace("&gt;", ">");
  }

  // 신규글 등록처리
  @PostMapping("/questionWrite")
  public String register(@ModelAttribute QuestionDTO dto, RedirectAttributes attributes, HttpSession session) {
    dto.setContent(removeHtmlTags(dto.getContent()));

    questionService.register(dto, session);

    // Long newQuestionNum = questionService.register(dto, null);
    // attributes.addFlashAttribute("newQuestionNum", newQuestionNum);
    return "redirect:/notice/question";
  }

  @PostMapping("/questionRemove")
  public String questionRemove(@ModelAttribute QuestionDTO dto, long questionNum, RedirectAttributes attributes,
      HttpSession session) {
    System.out.println("삭제 시 질문DTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(dto);

    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");

    if (loginedMember != null) { // 로그인했을 시,

      if (loginedMember.getNickname().equals(dto.getNickname())) {
        // 작성자가 로그인했을 경우

        questionService.remove(questionNum);
        return "redirect:/notice/question";

      } else { // 로그인한 회원이 작성자가 아닐 경우

        alertMessage = "해당 글 작성자만 삭제 가능합니다.";
        attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";

      }
    } else {// 로그인 안 했을 경우!

      alertMessage = "해당 글 작성자만 삭제 가능합니다.";
      attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    }

  }

  @PostMapping("/questionmodify")
  public String modify(@ModelAttribute QuestionDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
      RedirectAttributes redirect) {

    // System.out.println("질문수정 ==============================" + dto);
    dto.setContent(removeHtmlTags(dto.getContent()));
    questionService.modify(dto);

    redirect.addAttribute("questionNum", dto.getQuestionNum());
    return "redirect:/notice/questionDetail";
  }

  @GetMapping("/questionmodify")
  public String questionmodify(@ModelAttribute("dto") QuestionDTO dto, Model model, RedirectAttributes attributes,
      HttpSession session) {

    System.out.println("!!!!!!!!!!!!!!!!!!!!!!질문수정 시 질문DTO!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(dto);
    // 질문번호, 닉네임

    dto = questionService.get(dto.getQuestionNum());

    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");
    
    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!질문수정 시 Session!!!!!!!!!!!!!!!!!!!!!!!!!");    
    //System.out.println(loginedMember);

    if (loginedMember != null) { // 로그인했을 시,
      if (loginedMember.getNickname().equals(dto.getNickname())) { // 작성자가 로그인했을 경우
        model.addAttribute("dto", dto);
        return "/notice/questionmodify";
      } else { // 로그인한 회원이 작성자가 아닐 경우
        alertMessage = "해당 글 작성자만 수정 가능합니다.";
        attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";

      }
    } else {// 로그인 안 했을 경우!
      alertMessage = "해당 글 작성자만 수정 가능합니다.";
      attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    }
  }

  // @PostMapping("/remove")
  // public String remove(long questionNum, RedirectAttributes redirect){
  // questionService.remove(questionNum);
  // redirect.addAttribute("newQuestionNum", questionNum);
  // return "redirect:/notice/question";
  // }

}
