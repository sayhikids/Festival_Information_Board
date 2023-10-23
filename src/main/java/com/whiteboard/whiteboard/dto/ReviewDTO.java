package com.whiteboard.whiteboard.dto;

import java.time.LocalDateTime;

import com.whiteboard.whiteboard.entity.Festival;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder 
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {
	
	private Long reviewNum;//리뷰번호
	private Long replyNum;//리뷰댓글번호
	private int replyLevel;// 댓글수준
	private int replyStep;//댓글순서 
	private LocalDateTime regDate,modDate;

	private String writer;// 작성자 : Member 엔티티의 id(PK 즉 프라이머리키를 의미함) 컬럼
	private String nickname;// 작성자의 닉네임 얻는 법.. 엔티티 타입 변수.getWriter().getNickname()

	private String title;// 제목
	private String content;// 내용
	private Long readCount;// 조회수
	private Long goodCount;//좋아요수

	private Festival festivalNum1; // 축제번호1 //축제정보게시판과의 연동 목적
	private Festival festivalNum2; // 축제번호2
	private Festival festivalNum3; // 축제번호3
	private Festival festivalNum4; // 축제번호4  
	private Festival festivalNum5; // 축제번호5

	private String hashTag1; // 해시태그1 //검색목적
	private String hashTag2; // 해시태그2
	private String hashTag3; // 해시태그3
	private String hashTag4; // 해시태그4
	private String hashTag5; // 해시태그5 
	
	
	
}

