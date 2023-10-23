package com.whiteboard.whiteboard;

import java.time.LocalDate;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.whiteboard.whiteboard.entity.Member;
import com.whiteboard.whiteboard.repository.MemberRepository;
import com.whiteboard.whiteboard.service.MemberService;

@SpringBootTest
class WhiteboardApplicationTests {

	@Autowired
	private MemberService whiteboardService;

	@Autowired
	private MemberRepository memberRepository;// 멤버 레포지토리

	// @Autowired
	// private PasswordEncoder passwordEncoder;// 비밀번호 인코더

	// @Test
	public void memberregister() {// DB에 회원가입 member 테이블에 넣기

		Member member = Member.builder()
				.email("member@naver.com")
				.pw("7777")
				.phoneNum("01094800129")
				.nickname("민건왕자")
				.gender("하남자")
				.birthDay(LocalDate.parse("1991-05-02"))
				.name("김민건")
				.isSns(false)
				.build();
		memberRepository.save(member);

		System.err.println(member);
	}

	// 회원조회 테스트
	//@Test
	public void memberRE() {
		// 조회하려는 회원의 id (기본 키)를 알고 있다고 가정
		String memberId = "chansol91@naver.com";

		// 해당 ID로 회원 정보 조회
		Member member = memberRepository.findById(memberId).orElse(null);

		// 조회된 회원 정보가 null이 아니라면 정보를 출력
		if (member != null) {
			System.out.println("회원 정보: ");
			System.out.println("아이디: " + member.getEmail());
			System.out.println("전화번호: " + member.getPhoneNum());
			System.out.println("닉네임: " + member.getNickname());
			System.out.println("성별: " + member.getGender());
			System.out.println("생년월일: " + member.getBirthDay());
			System.out.println("이름: " + member.getName());
			System.out.println("sns로그인: " + member.isSns());
			System.out.println("비밀번호: " + member.getPw());

		} else {
			System.out.println("해당 ID에 해당하는 회원 정보가 없습니다.");
		}
	}

	// 회원 삭제 테스트
	// @Test
	public void deleteMember() {
		String memberId = "chansol91@naver.com";
		Member member = memberRepository.findById(memberId).orElse(null);

		System.out.println("DB에 담겨있는 아이디: " + member);

		if (member == null) {
			System.err.println("DB에 해당 아이디가 없습니다.");
			return;
		}

		// 입력한 패스워드
		// String inputPassword = "7777"; // 입력한 패스워드를 직접 입력

		// 저장된 회원의 패스워드와 입력한 패스워드를 비교 :
		// boolean isPasswordCorrect = passwordEncoder.matches(inputPassword,
		// member.getPw());

		// if (isPasswordCorrect) {
		// // 비밀번호가 일치하면 회원 삭제
		// whiteboardService.memberDelete(memberId);
		// System.err.println("비밀번호가 일치해서 삭제됨");
		// } else {
		// // 비밀번호가 일치하지 않을 때
		// System.err.println("비밀번호가 틀렸습니다. 확인해주세요.");
		// }
	}

	// 회원정보 수정 테스트
	// @Test
	// public void modifyMember() {
	// String memberId = "chansol91@naver.com";
	// Member member = memberRepository.findById(memberId).orElse(null);

	// System.out.println("DB에 담겨있는 아이디: " + member);

	// if (member == null) {
	// System.err.println("DB에 해당 아이디가 없습니다.");
	// return;
	// }

	// // 입력한 패스워드
	// String inputPassword = "7777"; // 입력한 패스워드를 직접 입력

	// // 저장된 회원의 패스워드와 입력한 패스워드를 비교
	// boolean isPasswordCorrect = passwordEncoder.matches(inputPassword,
	// member.getPw());

	// if (isPasswordCorrect) {
	// // 비밀번호가 일치하면 회원 정보 수정 시도
	// try {
	// // 수정할 회원 정보 가져오기
	// Member updatedMember = memberRepository.findById(memberId).orElse(null);

	// if (updatedMember != null) {
	// // 회원 정보 수정
	// updatedMember.setPhoneNum("01094800129"); // 수정할 내용 입력
	// updatedMember.setNickname("민건왕자");
	// updatedMember.setGender("천사");
	// updatedMember.setBirthDay(LocalDate.parse("2002-05-02")); // 수정할 내용 입력
	// updatedMember.setName("김민건");
	// updatedMember.setSns(false); // 수정할 내용 입력

	// // 수정된 정보 저장
	// memberRepository.save(updatedMember);

	// System.err.println("회원 정보가 수정되었습니다.");
	// } else {
	// System.err.println("회원 정보가 존재하지 않습니다.");
	// }
	// } catch (Exception e) {
	// // 회원 정보 수정 중 오류 발생 시
	// System.err.println("회원 정보 수정 중 오류 발생: " + e.getMessage());
	// }
	// } else {
	// // 비밀번호가 일치하지 않을 때
	// System.err.println("비밀번호가 틀렸습니다. 확인해주세요.");
	// }
	// }

	// @Test
	void contextLoads() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			// Board 테이블의 부모인 Member의 이메일값을 넣기 위한 테스트 멤버 생성 및 이메일만 세팅

			Member member = Member.builder()
					.email("member" + i + "@whiteboard.com")
					.pw("1234" + i)
					.name("회원명" + i)
					.nickname("닉네임" + i)
					.phoneNum("010-0000-000" + i)
					.gender("남")
					.birthDay(LocalDate.parse("2000-01-01"))
					.isSns(false)
					.build();
			memberRepository.save(member);
		});
	}

}
