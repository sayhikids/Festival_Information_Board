package com.whiteboard.whiteboard.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whiteboard.whiteboard.dto.FestivalDTO;
import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.entity.FestivalReply;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.FestivalReplyRepository;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.service.FestivalService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
// @RequestMapping("/member")//멤버 기본경로

public class FestivalController {

    private final FestivalService festivalService;

    private final FestivalReplyRepository festivalReplyRepository;

    private final MemberRepository memberRepository;

    // 메인화면에서 롤페이지 5개 랜덤 이미지 포스터
    @GetMapping("/member/main")
    @ResponseBody // 이 어노테이션을 추가하여 메서드가 JSON 데이터를 반환함
    public List<FestivalDTO> getFestivals() {
        List<FestivalDTO> festivals = festivalService.getFiveDTOs();
        // logger.info("축제 목록: {}", festivals); //콘솔에 찍히나 확인했습니다.
        // System.err.println("축제번호가 있나? :" + festivals);
        return festivals; // JSON 형식의 데이터를 반환합니다.
    }

    // 검색 기능 및 페이징을 위한 메서드
    @GetMapping("/festival/festivalList")
    public String showFestivalList(
            @RequestParam(name = "searchQuery", required = false) String searchQuery,
            PageRequestDTO pageRequestDTO, Model model) {

        List<FestivalDTO> festivals; // 축제 목록을 담을 변수
        boolean isSearch = false; // 검색 여부를 나타내는 변수

        if (searchQuery != null && !searchQuery.isEmpty()) {
            // 검색어가 제공된 경우, 해당 검색어를 사용하여 축제를 검색합니다.
            festivals = festivalService.searchFestivals(searchQuery); // 검색된 축제 목록을 가져옴
            isSearch = true; // 검색 여부 플래그를 true로 설정
        } else {
            // 검색어가 없는 경우, 페이징을 위한 로직을 수행합니다.

            // 페이지 요청 정보(pageRequestDTO)를 이용하여 페이지 관련 정보를 가져오고
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("festivalNum").ascending());

            // 서비스 계층을 통해 페스티벌 데이터를 페이지네이션하여 가져옵니다.
            Page<FestivalDTO> festivalPage = festivalService.findAllByOrderByFestivalNum(pageable);
            festivals = festivalPage.getContent(); // 현재 페이지의 축제 목록을 가져옴

            // 총 페이지 수 계산
            int totalPages = festivalPage.getTotalPages();

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

        model.addAttribute("festivals", festivals); // 축제 목록을 모델에 추가
        model.addAttribute("searchQuery", searchQuery); // 검색어를 모델에 추가
        model.addAttribute("isSearch", isSearch); // 검색 여부를 모델에 추가

        return "festival/festivalList"; // "festivalList.html" 페이지로 이동
    }

    // 축제 상세페이지 넘기기
    @GetMapping("/festival/festivalDetail")
    public String getFestivalDetail(@RequestParam("festivalNum") Long festivalNum, Model model) {
        FestivalDTO festivalDTO = festivalService.getfestivalFNum(festivalNum);
        model.addAttribute("festivalDTO", festivalDTO);
        System.err.println("festivalNum 숫자: " + festivalDTO);
        return "festival/festivalDetail"; // 렌더링할 뷰의 이름을 반환
    }

    // 세션에서 현재 로그인한 사용자의 이메일을 가져오는 메서드
    private Member getCurrentUserEmail(HttpSession session) {
        Object userEmail = session.getAttribute("userEmail"); // "userEmail"은 세션에서 사용자 이메일을 저장한 속성 이름입니다.
        if (userEmail != null && userEmail instanceof String) {
            return (Member) userEmail;
        }
        return null;
    }

    // 댓글 작성 메서드
    @PostMapping("/festival/festivalDetail/addComment")
    public String addComment(@RequestParam("festivalNum") Long festivalNum,
            @RequestParam("content") String content,
            HttpSession session) {
        // 세션에서 현재 로그인한 사용자 정보를 가져옴
        Member currentUser = getCurrentUserEmail(session);

        if (currentUser != null) {
            // 댓글 엔티티 생성 및 설정
            FestivalReply reply = new FestivalReply();
            FestivalDTO festivalDTO = festivalService.getfestivalFNum(festivalNum);
            reply.setFestivalNum(festivalNum); // Festival 엔티티를 설정
            reply.setWriter(currentUser);
            reply.setContent(content);

            // 댓글 저장
            festivalReplyRepository.save(reply);
        }

        // 댓글 작성 후, 상세 페이지로 리다이렉트
        return "redirect:/festival/festivalDetail?festivalNum=" + festivalNum;
    }

    // 댓글 수정 메서드
    @PostMapping("/festival/festivalDetail/editComment")
    public String editComment(@RequestParam("festivalNum") Long festivalNum,
            @RequestParam("commentNum") Long commentNum,
            @RequestParam("content") String content) {
        // 댓글 수정 서비스 호출
        festivalService.editComment(commentNum, content);

        // 댓글 수정 후 다시 상세페이지로 리다이렉트
        return "redirect:/festival/festivalDetail?festivalNum=" + festivalNum;
    }

    // 댓글 삭제 메서드
    @PostMapping("/festival/festivalDetail/deleteComment")
    public String deleteComment(@RequestParam("festivalNum") Long festivalNum,
            @RequestParam("commentNum") Long commentNum) {
        // 댓글 삭제 서비스 호출
        festivalService.deleteComment(commentNum);

        // 댓글 삭제 후 다시 상세페이지로 리다이렉트
        return "redirect:/festival/festivalDetail?festivalNum=" + festivalNum;
    }

    // 로그인한 사용자의 닉네임을 가져오는 메서드 (로그인 기능을 구현해야 함)
    private String getCurrentUserNickname() {
        // 로그인한 사용자의 닉네임을 가져오는 로직을 구현
        return "사용자의 닉네임"; // 실제 구현에서는 세션 또는 인증 정보를 이용하여 가져옵니다.
    }
}