package com.whiteboard.whiteboard.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

// 축제정보게시판 DTO
public class FestivalDTO {

	private Long festivalNum; // 축제 번호
	private String festivalTitle; // 축제명
	private String region; // 지역
	private String venue; // 개최장소
	private String period; // 기간
	private String description; //상세설명
	private String link; // 홈페이지
	private String poster; // 메인 포스터링크
	private String thumbnail; // 썸네일 포스터링크
	private LocalDateTime registerDate; // 작성일
	private LocalDateTime modifyDate; // 수정일
	private Long readCount; // 조회수

	public String getFestivalTitle() {
		return festivalTitle;
	}

	public void setFestivalTitle(String festivalTitle) {
		this.festivalTitle = festivalTitle;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	//2번째 방법
	public FestivalDTO(String festivalTitle, String thumbnail, String poster) {
		this.festivalTitle = festivalTitle;
		this.thumbnail = thumbnail;
		this.poster = poster;
	}
}
