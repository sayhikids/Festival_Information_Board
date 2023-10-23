// package com.whiteboard.whiteboard;

// import java.util.stream.IntStream;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.whiteboard.whiteboard.entity.Member;
// import com.whiteboard.whiteboard.entity.Review;
// import com.whiteboard.whiteboard.repository.ReviewRepository;

// @SpringBootTest
// public class ReviewListRepositoryTest {

//   @Autowired
//   private ReviewRepository reviewRepository;

//  // @Test
//   void contextLoads() {
//     IntStream.rangeClosed(1, 10).forEach(i -> {

//       Review review = Review.builder()
//           .writer(null)
//           .title("제목" + i + "입니다.")
//           // .content("이건" + i + " 번째 글입니다.")
//           .readCount(0L)
//           .goodCount(0L)
//           .build();
//       reviewRepository.save(review);
//     });
//   }

//   @Test
// public void contextLoads2() {

//   Member member = new Member(); // Member 객체 생성
// // 필요한 Member 객체의 속성(예: email) 설정
// member.setEmail("123@123.com");
//     // Review 엔티티를 생성하고 writer 필드를 초기화
//     Review review = Review.builder()
//             .writer(member) // 작성자 설정
//             .title("Sample Title")
//             .content("Sample Content")
//             .readCount(0L) // readCount 필드를 초기화
//             .goodCount(0L) // 나머지 필드도 초기화
//             .build();

//             reviewRepository.save(review);
    
//     // Review 엔티티를 저장하는 로직을 추가
// }

// }
