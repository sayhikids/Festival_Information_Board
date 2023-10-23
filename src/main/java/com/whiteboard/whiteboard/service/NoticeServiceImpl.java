package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.MemberDTO;
import com.whiteboard.whiteboard.dto.NoticeDTO;
import com.whiteboard.whiteboard.dto.PageRequestDTO;
import com.whiteboard.whiteboard.dto.PageResultDTO;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.entity.Notice;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.NoticeRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class NoticeServiceImpl implements NoticeService{

  private final NoticeRepository noticeRepository;

  private List<NoticeDTO> searchResults = new ArrayList<>();

  private final MemberService memberService;
  private final MemberRepository memberRepository;

  // 게시글 등록 메서드
  @Override
  public Long register(NoticeDTO dto, HttpSession session) {
    // DTO를 엔티티로 변환하고 저장 후, 저장된 게시글의 번호를 반환
    Notice notice = dtoToEntity(dto,session);
    noticeRepository.save(notice);
    return notice.getNoticeNum();
  }

// 페이지 요청 정보를 기반으로 게시글 목록을 가져오는 메서드
@Override
public PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
  
  // // 엔터티를 DTO로 변환하는 함수를 정의
  // Function<Object[], NoticeDTO> fn = (en -> entityToDTO((Notice)en[0], (Member)en[1]));

  // // Pageable을 이용하여 정렬 조건을 적용하고, 게시글 목록을 가져옴
  // Page<Object[]> result = noticeRepository.getNoticeWithWriter(pageRequestDTO.getPageable(Sort.by("noticeNum").descending()));

  // return new PageResultDTO<>(result, fn);
  return null;
}

    @Override
    public List<NoticeDTO> findAll() {
        
      List<NoticeDTO> noticeDTOs = new ArrayList<>();
        
        List<Notice> notices = noticeRepository.findAll();
        for (Notice notice : notices) {
          noticeDTOs.add(entityToDTO(notice));
        }

        return noticeDTOs;
    }

@Override
public NoticeDTO get(Long noticeNum) {

    Notice notice = noticeRepository.getNoticeBynoticeNum(noticeNum);

    return entityToDTO(notice);
  
}



@Transactional
@Override
public void remove(Long questionNum) {
  noticeRepository.deleteById(questionNum);
}

@Transactional
@Override
public void modify(NoticeDTO dto) {
  Notice notice = noticeRepository.getReferenceById(dto.getNoticeNum());

  notice.updateNContent(dto.getContent());
  notice.updateTitle(dto.getTitle());

  noticeRepository.save(notice);

} 

@Override
public Page<NoticeDTO> findAllByOrderByNoticeNum(Pageable pageable) {
  Page<Notice> noticePage = noticeRepository.findAllByOrderByNoticeNum(pageable);
        return noticePage.map(notice -> entityToDTO(notice));

}

@Override
public List<NoticeDTO> searchNotices(String searchQuery) {
  // 축제 제목에 검색어가 포함된 모든 축제를 검색
        List<Notice> notices = noticeRepository.findByTitleContaining(searchQuery);
        
        // Festival 엔티티를 FestivalDTO로 변환
        List<NoticeDTO> noticeDTOs = notices.stream()
                .map(this::entityToDTO) // entityToDTO 메서드 활용
                .collect(Collectors.toList());
        
        // 검색 결과를 searchResults 변수에 저장
        searchResults = noticeDTOs;
        
        return noticeDTOs;
}

  public Notice dtoToEntity(NoticeDTO noticeDTO, HttpSession session) {
    
    MemberDTO memberDTO = memberService.covertSessionToDTO(session);
    
    Member member = memberRepository.getReferenceById(memberDTO.getEmail());
    
    Notice notice = Notice.builder()
    //리뷰 엔티티의 writer는 Member타입임!!!!!!!!!!!!!!!
    .writer(member)
    .title(noticeDTO.getTitle())
    .content(noticeDTO.getContent())
    .build();
      return notice;
    }
 
}
