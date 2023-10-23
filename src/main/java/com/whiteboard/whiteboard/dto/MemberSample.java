// package com.whiteboard.whiteboard.dto;

// import java.time.LocalDate;
// import java.util.Collection;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.User;

// import com.whiteboard.whiteboard.entity.Member;

// import jakarta.persistence.Entity;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;

// @Getter
// @Setter
// @ToString
// @Entity
// //권한까지 부여된 memberDTO 샘플....
// public class MemberSample extends User {

    
  
//   //User 객체에 내부에 설정된 필드명(name, password, authorities)는 사용하지 말 것!!!
// 	private String email; //아이디 : 이메일주소로 받기
// 	private String pw; //비밀번호 : 암호화해줘야 함
// 	private String name; //실명
// 	private String phoneNum; //전화번호
// 	private String nickname; //별명
// 	private String gender; //성별
// 	private LocalDate birthDay; //생년월일
// 	private boolean isSns; //소셜계정 인증을 통한 가입 여부

//     private Member member;
	
// 	public MemberSample(Member member,
// 			Collection<? extends GrantedAuthority> authorities){
// 				super(member.getEmail(),member.getPw(),authorities);

// 				//User객체에 내장된 username 필드는 PK를 의미하므로 우리 DB 상의 PK인 id와 매핑해줘야 함
// 				this.name = member.getName();
//         this.phoneNum = member.getPhoneNum();
//         this.nickname = member.getNickname();
//         this.isSns = member.isSns();

// 			}
	
// }
