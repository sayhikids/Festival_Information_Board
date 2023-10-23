// package com.whiteboard.whiteboard.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// @EnableWebSecurity
// @Configuration
// public class SecurityConfig {

//     // private final UserDetailsService securityService;

//     // ////whiteboard의 모든 페이지의 기본값으로 스프링 시큐리티 기능 비활성화 메서드
//     // //인증을 위해 필요(?)
//     // @Bean
//     // public WebSecurityCustomizer inactvatSecurity() { 
//     //     //WebSecurityCustomizer는 Spring Security의 설정을 사용자 정의하기 위한 함수형 인터페이스

//     //     return (web) -> web.ignoring() //web 파라미터는 WebSecurity 객체
//     //             .requestMatchers("/","/main", "//**", "/login", "/registerMember");
//     //             ///whiteboard/" 경로로 시작하는 모든 url은 인증, 인가 없이 접근 가능
                
//     // }
    
//     // //특정 HTTP 요청에 대한 웹 기반 보안 설정 메서드
//     // @Bean
//     // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     //     http
//     //     .authorizeHttpRequests(request -> request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()//foarward방식으로 오는 요청은 인증, 인가 없이 접근 가능
//     //     //.requestMatchers("/main", "//**", "/view/join", "/auth/join").permitAll()//url은 인증, 인가 없이 접근 가능
//     //     .requestMatchers("/dashBoard").hasRole("ADMIN") //해당 url은 ADMIN 권한만 인가
//     //     .requestMatchers("/myPage").hasRole("USER") //해당 url은 USER 권한만 인가
//     //     .anyRequest().authenticated() //그 외 모든 요청은 인증된 사용자에게만 허용 (인가는 불요)
//     //     ) 
        
//     //     .formLogin(login -> login //폼 로그인 방식 처리
//     //     .loginPage("/login") //로그인 페이지 설정
//     //     .loginProcessingUrl("/login-process") //로그인 성공 시 이동 경로
//     //     .usernameParameter("email")
//     //     .passwordParameter("pw")
//     //     .defaultSuccessUrl("/main", true) //로그인 성공 시 리다이렉트할 URL
//     //     .permitAll()
//     //     ) // 로그인 페이지와 로그인 처리 URL은 모든 사용자에게 접근을 허용
//     //     .logout() //로그아웃 설정 구성
//     //     .logoutUrl("/logout") //로그아웃 URL
//     //     .invalidateHttpSession(true) //로그아웃 이후 세션전체 삭제 설정
//     //     .and()
//     //     .csrf().disable() //csrf 비활성화 //개발 중일 땐 비활성화, 실제 운영 시엔 활성화하는 게 일반적
//     //     .cors().disable(); //cors 비활성화 //다른 리소스에서의 요청을 관리하는데 비활성화
        
//     //     return http.build();
//     // }
    
//     // //인증 관리자 설정. 사용자 정보를 가져올 서비스 재정의 혹은 인증 방법 등을 설정
//     // //아래 2가지 중 뭐가 더 적합한지 몰라서 일단 둘 다 정의했음. 학습 후 적절한 것으로 적용할 것!!!!!!!

//     // //AuthenticationManager는 여러 AuthenticationProvider를 관리하고 이를 이용하여 실제 인증을 수행하는 Spring Security의 핵심 역할
//     // @Bean
//     // AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

//     //     return authenticationConfiguration.getAuthenticationManager();
        
//     // }

//     // //AuthenticationProvider는 특정 인증 로직을 구현하고 실제로 사용자를 인증하는 역할
//     // //주로 사용자 지정 로직 또는 다양한 인증 메커니즘을 지원하기 위해 구현한다
//     // @Bean
//     // public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception{
//     //     DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

//     //     daoAuthenticationProvider.setUserDetailsService(securityService);
//     //     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

//     //     return daoAuthenticationProvider;
//     // }

//     //비밀번호 인코더로 사용할 빈 등록
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

// }
