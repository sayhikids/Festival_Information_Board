package com.whiteboard.whiteboard.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
//공통 컬럼을 정의한 엔티티로, 자체 테이블 생성하지 않고 다른 테이블에 컬럼을 상속함. 
public abstract class BaseEntity {
	
	@CreatedDate //해당 필드 즉 컬럼에 날짜값이 자동 반영되도록 선언
	@Column(updatable = false)
	//DB에 registerDate 컬럼 생성하도록 선언 및 값이 처음 insert 이후엔 자동 update 불가하도록 설정
	private LocalDateTime registerDate; //작성일
	
	//@CreatedDate 
	@LastModifiedDate
	@Column
	private LocalDateTime modifyDate; //수정일


	public String getFormattedRegisterDate() {
        // 원하는 날짜 형식을 정의합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // registerDate를 원하는 형식으로 포맷합니다.
        String formattedDate = registerDate.format(formatter);

        return formattedDate;
    }
}
