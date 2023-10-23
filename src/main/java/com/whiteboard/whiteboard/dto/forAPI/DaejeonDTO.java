package com.whiteboard.whiteboard.dto.forAPI;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// 축제정보게시판 DTO
public class DaejeonDTO {

	private Long festivalNum; // 축제 번호

	@JsonProperty("festvNm")
	private String festivalTitle; // 축제명

	@JsonProperty("festvAddr")
	private String region; // 지역 : "대전광역시 " + ~~
	
	private String venue; // 개최장소

	//"festvPlcNm"값이 있는 경우 매핑할 필드
	@JsonProperty("festvPlcNm")
	private String venue1; // 개최장소

	//"festvPlcNm"값이 없는 경우 "festvAddr"값을 매핑할 필드
	@JsonProperty("festvAddr")
	private String venue2; // 개최장소
	
	@JsonProperty("festvPrid")
	private String period; // 기간

	@JsonProperty("festvSumm")
	private String description; // 설명(묘사)

	@JsonProperty("hmpgAddr")
	private String link; // 홈페이지

	private String poster; // 포스터링크

	private String thumbnail;

	private LocalDateTime registerDate; // 작성일
	private LocalDateTime modifyDate; // 수정일

	private Long readCount; // 조회수
	
	
}
