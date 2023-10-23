package com.whiteboard.whiteboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
//축제정보글 담기 목록
public class KeepList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long keepNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_email")
	private Member writer; // 축제글을 담은 회원 email
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_festival_num")
	private Festival festivalNum; // 축제번호
	
}
