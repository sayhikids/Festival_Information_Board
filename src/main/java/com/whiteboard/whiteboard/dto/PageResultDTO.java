package com.whiteboard.whiteboard.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

/*
 * 이 클래스는 사용자가 요청한 page에 해당하는 글목록들을 담아서
 * Page<Entity>로 리턴되면 Entity -> DTO로 변환하는 기능을 정의합니다.
 * 
 *  리턴된 Entity Page는 바로 사용할 수 없기 때문입니다.
 *  
 *  이를 위해서 이 클래스의 생성자를 통해서 변경이 필요한 Page 객체를 받고
 *  그 객체의 Entity를 DTO로 변환하는 함수를 지정한 Function(java.util)
 *  객체를 파라미터로 줘서 변환된 DTO List를 리턴하도록 할게요.
 */
//이 클래스의 생성자를 호출할 때 Generic을 설정함 .DTO와 En(Entity)라는 타입명으로 설정
@Data
public class PageResultDTO<DTO, EN> {

	// Entity가 담겨진 요청된 페이지의 글목록을 담은 Page객체의 글들을
	// DTO로 변환해서 담게되는 list선언
	private List<DTO> dtoList;

	// paging 처리를 위한 메서드와 이에 필요한 필드를 선언합니다.
	// 이 부분은 JSP에서도 했기 때문에 알아서 분석하세요.

	// 총 페이지 카운트
	private int totalPage;

	// currentPage index
	private int page;

	// 페이지 그룹의 수(즉 한 그룹당 10개 페이지로 할지의 여부)
	private int size;

	// 전체 페이지에서 시작 페이지와 마지막 페이지의 변수
	private int start, end;

	// 페이지 그룹에 따른 이전, 다음을 표시할 수 있는 변수
	private boolean prev, next;

	// 페이지 번호 목록
	private List<Integer> pageList;

	// 생성자에 변환될 목록의 Page와 Entity -> DTO로 변환시키는 함수 객체를 파람으로 지정하여
	// 위에 지정된 목적대로 수행하도록 정의합니다.
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		// 위 생성자가 실행되려면 반드시 위 파람 타입의 객체를 넘겨야 함.
		dtoList = result
					.stream()
					.map(fn)
					.collect(Collectors.toList());
		this.totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}

	// 아래는 Pageable의 page를 통해서 위 변수의 값들을 세팅하는 메서드를 정의합니다.
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();

		int tempEnd = (int) (Math.ceil(page / 10.0)) * 10; // 산수. 연산하세요.

		start = tempEnd - 9;

		prev = start > 1;
		end = totalPage > tempEnd ? tempEnd : totalPage;

		next = totalPage > tempEnd;

		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}

	public List<DTO> getDtoList() {
		return this.dtoList;
	}
}
