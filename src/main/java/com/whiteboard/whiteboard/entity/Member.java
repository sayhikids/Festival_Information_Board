package com.whiteboard.whiteboard.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor //(access = AccessLevel.PROTECTED) : 외부(다른 패키지)에서 생성 불가하도록 막아놓을 수도. 
@Getter
//@Setter //조작 방지를 위해 주석. 필드값 수정을 위해서는 별도로 메서드를 정의하기로 함. ex) updateName, updatePhoneNum
@ToString
//회원정보
public class Member extends BaseEntity{ //security설정을 위해 UserDetail 상속

	@Id
	@Column(updatable = false)
	private String email; //아이디 : 이메일주소로 받기
	
	//소셜계정인증 로그인의 경우 비밀번호 값이 없으므로 pw는 null허용해줘야 함(?)
	private String pw; //비밀번호 : 암호화해줘야 함

	@Column(nullable = false, length = 10)
	private String name; //실명

	@Column(nullable = false,unique = true)
	private String phoneNum; //전화번호

	@Column(nullable = false, unique = true, length = 20)
	private String nickname; //별명

	@Column(nullable = false)
	private String gender; //성별

	@Column(nullable = false)
	private LocalDate birthDay; //생년월일

	@Column(nullable = false)
	private boolean isSns; //소셜계정 인증을 통한 가입 여부
	
	// 여기에 비밀번호 업데이트 로직을 추가할 수 있음
	public void updatePw(String newPW) {
		this.pw = newPW;
}

// 실명 업데이트 메서드
public void updateName(String newName) {
		// 여기에 실명 업데이트 로직을 추가할 수 있음
		this.name = newName;
}

// 별명 업데이트 메서드
public void updateNickname(String newNickname) {
		// 여기에 별명 업데이트 로직을 추가할 수 있음
		this.nickname = newNickname;
}

// 전화번호 업데이트 메서드
public void updatePhoneNum(String newPhoneNum) {
		// 여기에 전화번호 업데이트 로직을 추가할 수 있음
		this.phoneNum = newPhoneNum;
}

public void setEmail(String email){
	this.email=email;
}

//회원이 DB에 인서트(생성)될 때 권한 부여하기

//권한 엔티티를 제네릭으로 준 컬랙션 생성
//@Builder.Default
//아래 어노테이션은 자동으로 참조를 걸어줌
//@ElementCollection(fetch = FetchType.LAZY) //권한값을 항상 가져올 텐데 LAZY가 꼭 필요한가..? 추후 수정할 수도...
//private Set<AuthoritySet> authority = new HashSet<>(); 

//회원이 DB에 인서트(생성) 시 권한 부여 메서드
//public void addAuthority(AuthoritySet authoritySet){
	//authority.add(authoritySet);
//}

//이하 7개의 메서드는 UserDetail 인터페이스의 "필수" 오버라이드 메서드이므로
//정의하지 않을 경우 오류 발생. 즉, 주석처리하지 말 것!

// @Override //권한 반환 메서드 오버라이드
// public Collection<? extends GrantedAuthority> getAuthorities() {
// 	//회원가입 시(회원DB에 인서트 시) 권한 기본값을 USER에 해당하는 "ROLE_USER"로 설정
// 	return List.of(new SimpleGrantedAuthority(AuthoritySet.USER.getValue()));
// }

// //사용자의 ID(PK값) 반환
// @Override
// public String getUsername() {
// 	return email;
// }

// //사용자의 패스워드 반환
// @Override
// public String getPassword() {
// 	return pw;
// }

// //이하 4개의 필수 오버라이드 메서드는 기본적으로 true값을 반환하도록 정의한 것.

// //계정 만료 여부 반환
// @Override
// public boolean isAccountNonExpired() {
// 	return true;
// }

// //계정 잠금 여부 반환
// @Override
// public boolean isAccountNonLocked() {
// 	return true;
// }

// //패스워드 만료 여부 반환
// @Override
// public boolean isCredentialsNonExpired() {
// 	return true;
// }

// //계정 사용 가능 여부 반환
// @Override
// public boolean isEnabled() {
// 	return true;
// }


//일단 주석..
// public static Member createUser(String email, String pw , PasswordEncoder passwordEncoder){
// 	return new Member(null, email, passwordEncoder.encode(pw), "USER");

}

