package com.whiteboard.whiteboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whiteboard.whiteboard.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

@Query("SELECT n, m FROM Notice n LEFT JOIN n.writer m WHERE n.noticeNum = :noticeNum")
Object getNoticeWithWriter(@Param("noticeNum") Long noticeNum);

@Query("SELECT n, m FROM Notice n JOIN n.writer m")
Page<Object[]> getNoticeWithWriter(Pageable pageable);


@Query("Select n From Notice n Where n.noticeNum = :noticeNum")
Notice getNoticeBynoticeNum(@Param("noticeNum") Long noticeNum);


@Query("Select n, m From Notice n LEFT JOIN n.writer m")
List<Notice> getNoticeList();

@Query(value = "Select n, w From Notice n LEFT JOIN n.writer w")
Page<Object[]> getNoticePage(Pageable pageable);

@Query("SELECT n FROM Notice n ORDER BY n.noticeNum DESC")
Page<Notice> findAllByOrderByNoticeNum(Pageable pageable);

List<Notice> findByTitleContaining(String searchQuery);
}

