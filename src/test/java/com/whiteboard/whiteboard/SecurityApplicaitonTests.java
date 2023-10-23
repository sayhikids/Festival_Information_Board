// package com.whiteboard.whiteboard;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.whiteboard.whiteboard.repository.MemberRepository;

// @SpringBootTest
// class SecurityApplicaitonTests {

//   @Autowired
//   private PasswordEncoder passwordEncoder;
//   @Autowired
// 	private MemberRepository memberRepository;
// 	//@Test
//   // void contextLoads() {
//   //   IntStream.rangeClosed(2, 10).forEach(i -> 
//   //   {Member member = Member.builder().
//   //                   email("회원" + i).
//   //                   pw(passwordEncoder.encode("1234")).
//   //                   name("이름" + i).
//   //                   phoneNum("1234567890" + i).
//   //                   nickname("닉네임" + i).
//   //                   birthDay(LocalDate.parse("2001-11-11")).
//   //                   gender("여자").
//   //                   isSns(false).
//   //                   build();
                    
//   //   member.addAuthority(AuthoritySet.MEMBER);

//   //   if (i <= 5) { //1번부터 5번까지의 회원은 member권한과 admin권한을 둘 다 갖도록...
//   //     member.addAuthority(AuthoritySet.ADMIN);
//   //   }

//   //   memberRepository.save(member);

//   //   });
//   // }
// }
