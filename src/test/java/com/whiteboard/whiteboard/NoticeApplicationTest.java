package com.whiteboard.whiteboard;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.whiteboard.whiteboard.entity.Notice;
import com.whiteboard.whiteboard.entity.Question;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.repository.NoticeRepository;
import com.whiteboard.whiteboard.repository.QuestionRepository;

@SpringBootTest
class NoticeApplicationTest {
  
  @Autowired
  //private NoticeService noticeService;
  private NoticeRepository noticeRepository;
  private QuestionRepository questionRepository;
  private MemberRepository memberRepository;
  //@Test
  void BaseEntity_테스트(){
        // given
        LocalDateTime nowTime = LocalDateTime.now();

        Notice notice = Notice.builder()
                              .title("제목")
                              .content("내용")
                              .build();
                

        //when
        noticeRepository.save(notice);

        //then
        //assertThat(notice.getCreatedTime()).isAfter(nowTime);

    }


  // 공지사항 db에 밀어넣기
  //@Test
  // void contextLoads() {
  // IntStream.rangeClosed(1, 10).forEach(i -> {

  // Notice notice = Notice.builder()
  //                     .title("제목" + i + "입니다.")
  //                     .content("이건" + i + " 번째 글입니다.")
  //                     .build();
  //     noticeRepository.save(notice);
  //   });
  // }

  // 공지사항 db에 밀어넣기
  @Test
  void contextLoads() {
  IntStream.rangeClosed(1, 10).forEach(i -> {
  //Member member = memberRepository.getReferenceById(i);
  Question question = Question.builder()
                      .title("제목" + i + "입니다.")
                      .content("이건" + i + " 번째 글입니다.")
                      .writer(null)
                      .build();
      questionRepository.save(question);
    });
  }
  //@Test
  //void contextLoads(){
    //IntStream.rangeClosed(1, 10).forEa
  //}

//   @Test // 리스트 페이지의 글목록 get 테스트
// 	public void getPageList() {
		
// 	PageRequestDTO dto = new PageRequestDTO();
		
// 	PageResultDTO<NoticeDTO, Object[]> result = noticeService.getList(dto);
		
// 	for(NoticeDTO nDTO : result.getDtoList()) {
// System.out.println(nDTO);
// 		}
//  }

//@Test
public void testGetOneRowFromBoard() {
  Object res = noticeRepository.getNoticeBynoticeNum(1L);
  
  Object[] theRow = (Object[])res;
  
  System.out.println(Arrays.toString(theRow));
  
  System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++");
}
}
