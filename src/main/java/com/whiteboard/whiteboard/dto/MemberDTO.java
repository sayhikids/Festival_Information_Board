package com.whiteboard.whiteboard.dto;

import java.time.LocalDate;

import com.whiteboard.whiteboard.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 회원정보 DTO
public class MemberDTO {

	private String email; // 아이디 : 이메일주소로 받기
	private String pw; // 비밀번호 : 암호화해줘야 함
	private String name; // 실명
	private String phoneNum; // 전화번호
	private String nickname; // 닉네임
	private String gender; // 성별
	private LocalDate birthDay; // 생년월일
	private boolean isSns; // 소셜계정 인증을 통한 가입 여부

	


}
