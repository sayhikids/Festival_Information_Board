// package com.whiteboard.whiteboard.security;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.whiteboard.whiteboard.entity.AuthoritySet;
// import com.whiteboard.whiteboard.entity.Member;
// import com.whiteboard.whiteboard.repository.MemberRepository;

// import lombok.RequiredArgsConstructor;

// //인증(Authentication)과 권한 부여(Authorization)를 다루는 서비스
// //사용자의 로그인 및 인증 처리, 접근 제어, 권한 확인 등과 같은 보안 관련 작업을 수행

// //필드 주입 (Field Injection)을 위해 생성자를 자동으로 생성
// //final 필드있을 때 필수
// @RequiredArgsConstructor
// @Service
// public class SecurityService implements UserDetailsService {

    
//     //@RequiredArgsConstructor로 MemberRepository 인스턴스를 주입받아 이 클래스에서 사용
//     private final MemberRepository memberRepository;

   
    

//     //UserDetailsService 인터페이스의 메서드로, 인증을 수행할 때 호출
//     //파라미터는 User객체의 username 즉 회원의 PK인 email로 받아야 한다.
//     @Override
//     public User loadUserByUsername(String email) throws UsernameNotFoundException {
        
//         //Optional 객체에 회원 정보(멤버 Entity)를 담는다.
//         Optional<Member> member = this.memberRepository.findByEmail(email);
//         //Optional 객체가 비어 있을 수도 있으므로 (회원을 찾지 못한 경우) 확인
//         if (member.isEmpty()) {
//             throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
//         }

//         Member loadedMember = member.get();
        
//         //사용자를 찾은 경우, 해당 사용자의 권한을 정의하기 위해 SimpleGrantedAuthority 객체를 생성
//         List<GrantedAuthority> authorities = new ArrayList<>();
        
//         if ("admin".equals(email)) { //"admin" 사용자인 경우 ADMIN 권한이 부여
//             authorities.add(new SimpleGrantedAuthority(AuthoritySet.ADMIN.getValue()));
//         } else { //그렇지 않은 경우 USER 권한이 부여
//             authorities.add(new SimpleGrantedAuthority(AuthoritySet.USER.getValue()));
//         }

//         //리턴타입은 UserDetails 인터페이스를 구현한 User 객체로서
//         //사용자 인증 및 권한을 나타내는 데 사용
//         //여기서 반환되는 User 객체는 사용자의 이메일, 패스워드 및 권한 목록을 가지도록 정의한다.
//         return new User(loadedMember.getUsername(), loadedMember.getPassword(), authorities);

//     }

// }
