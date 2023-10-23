package com.whiteboard.whiteboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whiteboard.whiteboard.dto.FestivalDTO;
import com.whiteboard.whiteboard.entity.Festival;

public interface FestivalService {

      // 한울쓰
      List<FestivalDTO> getFiveDTOs();

      List<FestivalDTO> getAllFestivalsAsDTO();

      // 게시물상세 페이지 정보 넘기기
      FestivalDTO getfestivalFNum(Long festivalNum);

      // 미건쓰: 게시물 메서드 정의
      List<FestivalDTO> findAllByOrderByFestivalNumAsc();

      // 축제 페이징
      Page<FestivalDTO> findAllByOrderByFestivalNum(Pageable pageable);

      // 서치를 위해 메서드 정의
      List<FestivalDTO> searchFestivals(String searchQuery);

      // Entity를 DTO로 변환하는 메서드 선언
      default FestivalDTO entityToDTO(Festival festival) {

            FestivalDTO dto = FestivalDTO.builder()
                        .festivalNum(festival.getFestivalNum())
                        .festivalTitle(festival.getFestivalTitle())
                        .region(festival.getRegion())
                        .venue(festival.getVenue())
                        .period(festival.getPeriod())
                        .description(festival.getDescription())
                        .link(festival.getLink())
                        .poster(festival.getPoster())
                        .thumbnail(festival.getThumbnail())
                        .registerDate(festival.getRegisterDate())
                        .modifyDate(festival.getModifyDate())

                        .build();
            return dto;
      }

      // 댓글생성
      void addComment(Long festivalNum, String currentUserEmail, String content);

      // 댓글 수정
      void editComment(Long commentNum, String content);

      // 댓글 수정
      void deleteComment(Long commentNum);

}
