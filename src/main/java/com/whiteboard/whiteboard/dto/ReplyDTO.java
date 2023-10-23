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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class ReplyDTO {
   
   private Long replyNum; // 댓글번호
 
   private Long festivalNum; // 축제번호 : 축제글의 댓글인 경우
   private Long reviewNum; // 리뷰번호 : 리뷰글의 댓글인 경우
   private Long questionNum; // 질문번호 : 질문글의 댓글인 경우

   private String writer; // 작성자의 이메일
   private String nickname; // 작성자의 닉네임

   private String content; // 내용
   
   private int replyLevel; //댓글수준 : 댓글(1)/대댓글(2)까지만
   private int replyStep; //댓글순서 : 댓글1/댓글1-1/댓글1-2/댓글2/댓글2-1
 
   private LocalDateTime regDate; // 작성일
   private LocalDateTime modDate; // 수정일
}