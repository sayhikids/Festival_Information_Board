package com.whiteboard.whiteboard.entity;

import jakarta.persistence.Column;
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
//리뷰댓글
public class ReviewReply extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyNum;//리뷰댓글번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewNum")
	private Review reviewNum;//리뷰번호
	
	@Column(name = "reply_level")
	private int replyLevel;//댓글수준

	@Column(name = "reply_step")
	private int replyStep;//댓글순서
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_email")
	private Member writer; //작성자 : Member 엔티티의 email 컬럼

	@Column(nullable = false)
	private String content;//내용
	
	public void updateContent(String newContent) {
			this.content = newContent;
	}

}
