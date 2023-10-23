package com.whiteboard.whiteboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whiteboard.whiteboard.dto.FestivalDTO;
import com.whiteboard.whiteboard.entity.Festival;
import com.whiteboard.whiteboard.entity.FestivalReply;
import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.FestivalReplyRepository;
import com.whiteboard.whiteboard.repository.FestivalRepository;
import com.whiteboard.whiteboard.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    private final FestivalRepository festivalRepository;

    private final FestivalReplyRepository festivalReplyRepository;

    private final MemberRepository memberRepository;

    private List<FestivalDTO> searchResults = new ArrayList<>();

    @Override
    public List<FestivalDTO> getAllFestivalsAsDTO() {
        List<Festival> festivals = festivalRepository.findAll();
        List<FestivalDTO> festivalDTOs = new ArrayList<>();

        for (Festival festival : festivals) {
            FestivalDTO festivalDTO = new FestivalDTO(festival.getFestivalTitle(), festival.getThumbnail(),
                    festival.getPoster());
            festivalDTOs.add(festivalDTO);
        }

        return festivalDTOs;
    }

    @Override
    public List<FestivalDTO> getFiveDTOs() {

        List<Festival> fetival5List = festivalRepository.getFiveEntity();

        return fetival5List.stream().map(festivals -> entityToDTO(festivals)).collect(Collectors.toList());
    }

    // 전체 가져와서 순서대로
    @Override
    public List<FestivalDTO> findAllByOrderByFestivalNumAsc() {

        List<Festival> festivalAll = festivalRepository.findAllByOrderByFestivalNumAsc();

        return festivalAll.stream().map(festivals -> entityToDTO(festivals)).collect(Collectors.toList());

    }

    // 축제 페이징
    @Override
    public Page<FestivalDTO> findAllByOrderByFestivalNum(Pageable pageable) {
        Page<Festival> festivalPage = festivalRepository.findAllByOrderByFestivalNum(pageable);
        return festivalPage.map(festival -> entityToDTO(festival));
    }

    // 축제페이지에서 클릭시 상세페이지로
    @Override
    public FestivalDTO getfestivalFNum(Long festivalNum) {
        Optional<Festival> festivalOptional = festivalRepository.findByFestivalNum(festivalNum);
        if (festivalOptional.isPresent()) {
            Festival festival = festivalOptional.get();
            return entityToDTO(festival);
        } else {
            // 게시물을 찾을 수 없을 때 예외 처리
            throw new NoSuchElementException(festivalNum + " : 게시물을 찾을 수 없습니다.");
        }
    }

    // 축제검색
    @Override
    public List<FestivalDTO> searchFestivals(String searchQuery) {
        // 축제 제목에 검색어가 포함된 모든 축제를 검색
        List<Festival> festivals = festivalRepository.findByFestivalTitleContaining(searchQuery);

        // Festival 엔티티를 FestivalDTO로 변환
        List<FestivalDTO> festivalDTOs = festivals.stream()
                .map(this::entityToDTO) // entityToDTO 메서드 활용
                .collect(Collectors.toList());

        // 검색 결과를 searchResults 변수에 저장
        searchResults = festivalDTOs;

        return festivalDTOs;
    }

    // 댓글 작성
    @Override
    public void addComment(Long festivalNum, String currentUserNickname, String content) {
        // 1. 댓글 엔티티 생성
        FestivalReply reply = new FestivalReply();
        reply.setFrom(Festival.builder().festivalNum(festivalNum).build());

        // 작성자의 닉네임을 기반으로 작성자 엔티티 생성 및 설정
        Member writer = Member.builder().nickname(currentUserNickname).build();
        reply.setWriter(writer);

        reply.setContent(content);

        // 2. 댓글 레벨 및 순서 설정 (원하는 로직에 따라 설정)
        reply.setReplyLevel(1); // 댓글 레벨은 1로 설정 (대댓글인 경우 2 등으로 설정 가능)
        reply.setReplyStep(1); // 댓글 순서는 1로 설정 (댓글의 순서를 관리하려면 로직 추가 필요)

        // 3. 댓글 엔티티를 저장
        festivalReplyRepository.save(reply);
    }

    // 댓글 수정
    @Override
    public void editComment(Long commentNum, String content) {
        // 1. 수정할 댓글 엔티티 가져오기
        FestivalReply reply = festivalReplyRepository.findById(commentNum)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        // 2. 댓글 내용 업데이트
        reply.updateFContent(content);

        // 3. 수정된 댓글 엔티티를 저장
        festivalReplyRepository.save(reply);
    }

    // 댓글삭제
    @Override
    public void deleteComment(Long commentNum) {
        // 1. 삭제할 댓글 엔티티 가져오기
        FestivalReply reply = festivalReplyRepository.findById(commentNum)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        // 2. 댓글 엔티티 삭제
        festivalReplyRepository.delete(reply);
    }

    // 검색 결과를 반환하는 메서드
    // public List<FestivalDTO> getSearchResults() {
    // return searchResults;
    // }

}
