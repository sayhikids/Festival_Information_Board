package com.whiteboard.whiteboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whiteboard.whiteboard.dto.NoticeDTO;
import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.dto.PageResultDTO;
import com.whiteboard.whiteboard.entity.Notice;

import jakarta.servlet.http.HttpSession;

public interface NoticeService {
    // 신규글 등록 메서드
    Long register(NoticeDTO dto, HttpSession session);

    //모든 공지글 가져오기
    List<NoticeDTO> findAll();

    // 특정 게시물의 정보를 리턴라는 메서드 선언
    NoticeDTO get(Long noticeNum);

    // 축제 페이징
    Page<NoticeDTO> findAllByOrderByNoticeNum(Pageable pageable);

    // list 페이지에서 페이지에 해당하는 글목록 조회 리스트 get 메서드 정의
    PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 설명: Entity 객체를 DTO로 변환하는 메서드
    default NoticeDTO entityToDTO(Notice notice){
        NoticeDTO dto = NoticeDTO.builder()
                        .noticeNum(notice.getNoticeNum())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .registerDate(notice.getRegisterDate())
                        .nickname(notice.getWriter().getNickname())
                        .build();

        return dto;
    }

    // // dtoToEntity 변환 메서드 정의
    // default Notice dtoToEntity(NoticeDTO dto){
    //     Member member = Member.builder().name(dto.getWriter()).build();
        
    //     Notice notice = Notice.builder()
    //                     .noticeNum(dto.getNoticeNum())
    //                     .title(dto.getTitle())
    //                     .content(dto.getContent())
    //                     .build();

    //     return notice;
    // }

    // 게시물 삭제 메서드 선언.
    void remove(Long questionNum);

    //게시물 수정 메서드 선언
    void modify(NoticeDTO dto);

    //검색
    List<NoticeDTO> searchNotices(String searchQuery);
}
