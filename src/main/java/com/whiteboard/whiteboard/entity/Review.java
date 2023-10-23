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
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 리뷰게시판
public class Review extends BaseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long reviewNum; // 리뷰번호

   @ManyToOne(fetch = FetchType.LAZY)
   //@JoinColumn(nullable = false, name = "writer_email", referencedColumnName = "email")
   private Member writer; // 작성자 : Member 엔티티의 email 컬럼

   @Column(nullable = false)
   private String title; // 제목

   @Column(nullable = false)
   private String content; // 내용

   @Column(name = "read_count", nullable = false, columnDefinition = "integer default 0")
    private Long readCount; // 조회수

   // @Column(name = "read_count", nullable = false, columnDefinition = "bigint
   // default 0")
   // private Long readCount; // 조회수 (기본값 설정)

   @Column(name = "good_count", nullable = false, columnDefinition = "integer default 0")
   private Long goodCount; // 좋아요 수

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "link1_festival_num")
   private Festival link1; // 축제번호1 //축제정보게시판과의 연동 목적

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "link2_festival_num")
   private Festival link2; // 축제번호2

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "link3_festival_num")
   private Festival link3; // 축제번호3

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "link4_festival_num")
   private Festival link4; // 축제번호4

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "link5_festival_num")
   private Festival link5; // 축제번호5

   private String hashTag1; // 해시태그1 //검색목적
   private String hashTag2; // 해시태그2
   private String hashTag3; // 해시태그3
   private String hashTag4; // 해시태그4
   private String hashTag5; // 해시태그5

   public void updateContent(String Content) {
      this.content = Content;
   }

   public void updateTitle(String newTitle) {
      this.title = newTitle;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setContent(String content) {
      this.content = content;
   }

}