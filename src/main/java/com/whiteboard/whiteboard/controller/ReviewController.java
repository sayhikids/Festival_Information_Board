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
import com.whiteboard.whiteboard.dto.ReplyDTO;
import com.whiteboard.whiteboard.dto.ReviewDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.ReviewRepository;
import com.whiteboard.whiteboard.service.MemberService;
import com.whiteboard.whiteboard.service.ReviewReplyService;
import com.whiteboard.whiteboard.service.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping({ "/review", "", "/" })
public class ReviewController {

  private final MemberService memberService;
  private final ReviewRepository reviewRepository;
  private final ReviewService reviewService;
  private final ReviewReplyService reviewReplyService;

  // 검색 기능 및 페이징을 위한 메서드
  @GetMapping("/reviewList")
  public String review(
      @RequestParam(name = "searchQuery", required = false) String searchQuery,
      PageRequestDTO pageRequestDTO, Model model) {

    List<ReviewDTO> reviews; // 축제 목록을 담을 변수
    boolean isSearch = false; // 검색 여부를 나타내는 변수

    if (searchQuery != null && !searchQuery.isEmpty()) {
      // 검색어가 제공된 경우, 해당 검색어를 사용하여 축제를 검색합니다.
      reviews = reviewService.searchReviews(searchQuery); // 검색된 축제 목록을 가져옴
      isSearch = true; // 검색 여부 플래그를 true로 설정
    } else {
      // 검색어가 없는 경우, 페이징을 위한 로직을 수행합니다.

      // 페이지 요청 정보(pageRequestDTO)를 이용하여 페이지 관련 정보를 가져오고
      Pageable pageable = pageRequestDTO.getPageable(Sort.by("reviewNum").descending());

      // 서비스 계층을 통해 페스티벌 데이터를 페이지네이션하여 가져옵니다.
      Page<ReviewDTO> reviewPage = reviewService.findAllByOrderByReviewNum(pageable);
      reviews = reviewPage.getContent(); // 현재 페이지의 축제 목록을 가져옴

      // 총 페이지 수 계산
      int totalPages = reviewPage.getTotalPages();

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

    model.addAttribute("result", reviews); // 축제 목록을 모델에 추가
    model.addAttribute("searchQuery", searchQuery); // 검색어를 모델에 추가
    model.addAttribute("isSearch", isSearch); // 검색 여부를 모델에 추가
    return "review/reviewList"; // "festivalList.html" 페이지로 이동
  }

  @GetMapping("/reviewDetail")
  public void getReviewDetail(@ModelAttribute ReviewDTO reviewDTO, Model model, HttpSession session) {

    // System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%리뷰상세에서 reviewNum : " +
    // reviewDTO.getReviewNum());
    // 해당 리뷰글의 조회수 1회 올리기 (DB의 데이터 변경)
    reviewDTO.setContent(removeNonBreakingSpaces(reviewDTO.getContent()));
    reviewService.updateReadCount(reviewDTO.getReviewNum());

    // 해당 리뷰글의 데이터 DB에서 모두 가져오기
    reviewDTO = reviewService.getReviewByReviewNum(reviewDTO.getReviewNum());
    // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 리뷰DTO : " +
    // reviewDTO);

    // 댓글 목록 가져오기
    List<ReplyDTO> replyList = reviewReplyService.findAll(reviewDTO.getReviewNum());

    // System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%리뷰 댓글목록 : " + replyList);

    model.addAttribute("reviewDTO", reviewDTO);
    model.addAttribute("replyList", replyList);
    model.addAttribute("session", session);

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

  public static String removeNonBreakingSpaces(String input) {
    if (input == null) {
      return "";
    }
    // &nbsp;를 공백으로 대체합니다.
    return input.replace("&nbsp;", " ");
  }

  @PostMapping("/reviewWrite")
  public String saveReivew(@ModelAttribute ReviewDTO dto, RedirectAttributes attributes, HttpSession session) {

    // System.out.println("작성한 값 담은 dto 전달되는지 확인!! : " + dto);
    dto.setContent(removeHtmlTags(dto.getContent()));
    dto.setContent(removeNonBreakingSpaces(dto.getContent()));
    reviewService.saveReview(dto, session);
    return "redirect:/review/reviewList";

    // attributes.addFlashAttribute("newReviewNum", newReviewNum);
  }

  @GetMapping("/reviewWrite")
  public String postWriteReview(@ModelAttribute ReviewDTO dto, RedirectAttributes attributes, HttpSession session) {

    System.out.println("Session 아이디 확인!! : " + session.getAttribute("loggedInUser"));
    String alertMessage = "";

    if (session.getAttribute("loggedInUser") != null) { // 로그인한 경우

      return "/review/reviewWrite"; // 작성페이지 띄우기

    } else {// 로그인하지 않은 경우
      alertMessage = "로그인한 회원만 글 작성 가능합니다.";
      attributes.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";
    }
  }

  @PostMapping("/remove")
  public String remove(@ModelAttribute ReviewDTO reviewDTO, Long reviewNum, RedirectAttributes redirect,
      HttpSession session) {
    System.out.println("================================================삭제시 리뷰dto" + reviewDTO);
    // reviewService.remove(reviewNum);
    // redirect.addAttribute("reviewDTO",reviewNum);
    // return "redirect:/review/reviewList";
    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");

    if (loginedMember != null) { // 로그인했을 시,

      if (loginedMember.getNickname().equals(reviewDTO.getNickname())) {
        // 작성자가 로그인했을 경우

        reviewService.remove(reviewDTO.getReviewNum());
        return "redirect:/review/reviewList";

      } else { // 로그인한 회원이 작성자가 아닐 경우

        alertMessage = "해당 글 작성자만 삭제 가능합니다.";
        redirect.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";

      }
    } else {// 로그인 안 했을 경우!

      alertMessage = "해당 글 작성자만 삭제 가능합니다.";
      redirect.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    }
  }

  @PostMapping("/reviewModify")
  public String modify(@ModelAttribute ReviewDTO reviewDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
      RedirectAttributes redirect) {
    System.out.println("================================================수정시 리뷰dto" + reviewDTO);
    reviewDTO.setContent(removeHtmlTags(reviewDTO.getContent()));
    reviewDTO.setContent(removeNonBreakingSpaces(reviewDTO.getContent()));
    reviewService.modify(reviewDTO);

    redirect.addAttribute("reviewNum", reviewDTO.getReviewNum());
    return "redirect:/review/reviewDetail";
  }

  // @PostMapping("/reviewModify")
  // public String modify(ReviewDTO dto, @ModelAttribute("requestDTO")
  // PageRequestDTO requestDTO, RedirectAttributes redirect){
  // reviewService.modify(dto);

  // redirect.addAttribute("reviewDTO", dto);
  // return "redirect:/review/reviewDetail";
  // }

  @GetMapping("/reviewModify")
  public String reviewModify(@ModelAttribute ReviewDTO reviewDTO, Model model, RedirectAttributes redirect,
      HttpSession session) {
    System.out.println("============================================================");
    System.out.println("수정페이지에서 DTO!!!!!!!!!!!!!!!!!!!!! : " + reviewDTO); // reviewNum, title, content옴

    reviewDTO = reviewService.getReviewByReviewNum(reviewDTO.getReviewNum());
    String alertMessage = "";
    Member loginedMember = (Member) session.getAttribute("loggedInUser");
    System.out.println("수정페이지에서 loginedMember!!!!!!!!!!!!!!!!!!!!! : " + loginedMember);
    if (loginedMember != null) { // 로그인했을 시,
      if (loginedMember.getNickname().equals(reviewDTO.getNickname())) { // 작성자가 로그인했을 경우
        model.addAttribute("dto", reviewDTO);
        return "/review/reviewModify";
      } else { // 로그인한 회원이 작성자가 아닐 경우
        alertMessage = "해당 글 작성자만 수정 가능합니다.";
        redirect.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
        return "redirect:/member/unloginedAlert";

      }
    } else {// 로그인 안 했을 경우!
      alertMessage = "해당 글 작성자만 수정 가능합니다.";
      redirect.addAttribute("alertMessage", alertMessage); // alertMessage 값을 모델에 추가
      return "redirect:/member/unloginedAlert";

    }

  }

  // @PostMapping("/reviewDetail")
  // public String getReviewDetail(@ModelAttribute ReviewDTO reviewDTO, Model
  // model, HttpSession session) {
  // //ReviewDTO reviewDTO = reviewService.getReviewById(reviewNumLong);
  // //model.addAttribute("result", reviewRepository.getReviewList());
  // // model.addAttribute("result", reviewRepository.getReviewNum(1L));
  // // model.addAttribute("result",
  // reviewRepository.getReviewNum(reviewDTO.getReviewNum()));
  // model.addAttribute("reviewDTO",
  // reviewService.getReviewByReviewNum(reviewDTO.getReviewNum()));
  // return "redirect:/review/reviewDetail";
  // }

}