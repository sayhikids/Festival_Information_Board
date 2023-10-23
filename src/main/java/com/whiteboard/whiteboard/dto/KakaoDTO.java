package com.whiteboard.whiteboard.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoDTO {
    
    private Long id; // 카카오 로그인 시 제공 받는 값
    private String email; // 카카오 로그인 시 제공 받는 값
	//private String pw; // 안 받는 값
	//private String name; // 안 받는 값
	//private String phoneNum; // 안 받는 값
	private String nickname; // 카카오 로그인 시 제공 받는 값
	private String gender; // 카카오 로그인 시 제공 받는 값
	private LocalDate birthDay; // 카카오 로그인 시 제공 받는 값
	//private boolean isSns = true; // 안 받는 값
}
