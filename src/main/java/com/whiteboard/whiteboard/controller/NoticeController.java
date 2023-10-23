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

import com.whiteboard.whiteboard.dto.NoticeDTO;
import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.NoticeRepository;
import com.whiteboard.whiteboard.repository.QuestionRepository;
import com.whiteboard.whiteboard.service.MemberService;
import com.whiteboard.whiteboard.service.NoticeService;
import com.whiteboard.whiteboard.service.NoticeServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")

public class NoticeController {
  private final NoticeService noticeService;
  private final NoticeServiceImpl noticeServiceImpl;
  private final NoticeRepository noticeRepository;
  private final QuestionRepository questionRepository;
  private final MemberService memberService;

  // @GetMapping("/notice1")
  // public void notice(PageRequestDTO pageRequestDTO, Model model){
  // //model.addAttribute("result", noticeService.getList(pageRequestDTO));
  // //model.addAttribute("result", noticeRepository.getNoticeBynoticeNum(1L));

  // model.addAttribute("result", noticeService.findAll());
  // //model.addAttribute("result", noticeRepository.getNoticeList());
  // }

  // 검색 기능 및 페이징을 위한 메서드
  @GetMapping("/notice1")
  public String notice(String alertMessage,
      @RequestParam(name = "searchQuery", required = false) String searchQuery,
      PageRequestDTO pageRequestDTO, Model model) {

    // if (session.getAttribute("loggedInUser") != null) {
    //   mEmail = memberService.covertSessionToDTO(session).getEmail();
    //   if (mEmail.equals("123@123.com")) {
    //     mEmail = "123@123.com";
    //   }
    // } else {
    //   mEmail = "";
    // }

    List<NoticeDTO> notices; // 축제 목록을 담을 변수
    boolean isSearch = false; // 검색 여부를 나타내는 변수

    if (searchQuery != null && !searchQuery.isEmpty()) {
      // 검색어가 제공된 경우, 해당 검색어를 사용하여 축제를 검색합니다.
      notices = noticeService.searchNotices(searchQuery); // 검색된 축제 목록을 가져옴
      isSearch = true; // 검색 여부 플래그를 true로 설정
    } else {
      // 검색어가 없는 경우, 페이징을 위한 로직을 수행합니다.

      // 페이지 요청 정보(pageRequestDTO)를 이용하여 페이지 관련 정보를 가져오고
      Pageable pageable = pageRequestDTO.getPageable(Sort.by("noticeNum").descending());

      // 서비스 계층을 통해 페스티벌 데이터를 페이지네이션하여 가져옵니다.
      Page<NoticeDTO> noticePage = noticeService.findAllByOrderByNoticeNum(pageable);
      notices = noticePage.getContent(); // 현재 페이지의 축제 목록을 가져옴

      // 총 페이지 수 계산
      int totalPages = noticePage.getTotalPages();

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

    model.addAttribute("result", notices); // 축제 목록을 모델에 추가
    model.addAttribute("searchQuery", searchQuery); // 검색어를 모델에 추가
    model.addAttribute("isSearch", isSearch); // 검색 여부를 모델에 추가
    model.addAttribute("alertMessage", alertMessage);

    return "notice/notice1"; // "festivalList.html" 페이지로 이동
  }

  

  @GetMapping("/notice1Detail")
  public void noticeDetail(@RequestParam("noticeNum") Long noticeNum, Model model, HttpSession session) {
    NoticeDTO noticeDTO = noticeService.get(noticeNum);

    System.out.println("공지 상세페이지에서 공지DTO++++++++++++++++++++++++++++++++++" +noticeDTO);
    model.addAttribute("noticeDTO", noticeDTO);

  }

  // 신규글등록폼 요청처리하기
  @GetMapping("/noticeWrite")
  public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes attributes, HttpSession session) {
    //System.out.println("Session 아이디 확인!! : " + session.getAttribute("loggedInUser"));
    String alertMessage = "";

    Member loginedMember = (Member) session.getAttribute("loggedInUser");

    if (session.getAttribute("loggedInUser") == null) { 
      // 로그인하지 않은 경우
      alertMessage = "관리자만 글 작성 가능합니다.";
      attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    } else {
      //로그인한 경우

      if (loginedMember.getEmail().equals("manager123@naver.com")) {
        //관리자인 경우
        return "/notice/noticeWrite"; // 작성페이지 띄우기

      } else {
        //관리자가 아닌 회원이 로그인한 경우
        alertMessage = "관리자만 글 작성 가능합니다.";
        attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";
      }
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

  // 신규글 등록처리하기..
  @PostMapping("/noticeWrite")
  public String register(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes attributes, HttpSession session) {

    noticeDTO.setContent(removeHtmlTags(noticeDTO.getContent())); // Notice notice =
    noticeService.register(noticeDTO, session);
    // notice.updateTitle(dto.getTitle());
    // notice.updateNContent(dto.getContent());
    // noticeRepository.save(notice);
    // Long newNoticeNum = noticeService.register(dto,session);
    // attributes.addFlashAttribute("newNoticeNum", newNoticeNum);
    return "redirect:/notice/notice1";
  }

  @PostMapping("/remove")
  public String remove(long noticeNum, RedirectAttributes redirectAttributes, HttpSession session) {
    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");
    if (session.getAttribute("loggedInUser") == null) { 
      // 로그인하지 않은 경우
      alertMessage = "관리자만 삭제 가능합니다.";
      redirectAttributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    } else {
      //로그인한 경우

      if (loginedMember.getEmail().equals("manager123@naver.com")) {
        //관리자인 경우
        noticeService.remove(noticeNum);
        redirectAttributes.addAttribute("newNoticeNum", noticeNum);
        return "redirect:/notice/notice1";

      } else {
        //관리자가 아닌 회원이 로그인한 경우
        alertMessage = "관리자만 삭제 가능합니다.";
        redirectAttributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";
      }
    }
  }

  @PostMapping("/noticemodify")
  public String modify(@ModelAttribute NoticeDTO noticeDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
      RedirectAttributes redirect) {
    //System.out.println("수정창띄우기!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.err.println("공지POST 수정dto!!!!!!!!!!!!!!!!!!!!! : " + noticeDTO);
    noticeDTO.setContent(removeHtmlTags(noticeDTO.getContent()));
    noticeService.modify(noticeDTO);

    // redirect.addAttribute("page", requestDTO);
    redirect.addAttribute("noticeNum", noticeDTO.getNoticeNum());
    // System.out.println(redirect.addAttribute("page", requestDTO));
    return "redirect:/notice/notice1Detail";
  }

  // 신규글등록폼 요청처리하기
  @GetMapping("/noticemodify")
  public String noticemodify(@ModelAttribute NoticeDTO noticeDTO, Model model, HttpSession session,
      RedirectAttributes redirectAttributes) {

    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");
    if (session.getAttribute("loggedInUser") == null) { 
      // 로그인하지 않은 경우
      alertMessage = "관리자만 글 수정 가능합니다.";
      redirectAttributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    } else {
      //로그인한 경우

      if (loginedMember.getEmail().equals("manager123@naver.com")) {
        //관리자인 경우
        System.err.println("공지GET 수정dto!!!!!!!!!!!!!!!!!!!!! : " + noticeDTO);
        
        model.addAttribute("noticeDTO", noticeDTO);
        return "/notice/noticemodify"; // 작성페이지 띄우기

      } else {
        //관리자가 아닌 회원이 로그인한 경우
        alertMessage = "관리자만 글 수정 가능합니다.";
        redirectAttributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";
      }
    }


    
  }

  // @GetMapping("/question")
  // public void question(PageRequestDTO pageRequestDTO, Model model){
  // //model.addAttribute("result", noticeService.getList(pageRequestDTO));
  // //model.addAttribute("result", noticeRepository.getNoticeBynoticeNum(1L));

  // //model.addAttribute("result", noticeService.findAllQuestion());
  // model.addAttribute("result", questionRepository.getQuestionList());
  // }

  // // @GetMapping("/questionDetail")
  // // public void questionDetail(@RequestParam("questionNum") Long questionNum,
  // Model model, HttpSession session) {
  // // QuestionDTO questionDTO = noticeService.getquestion(questionNum);
  // // model.addAttribute("result", questionDTO);

  // // }

}
