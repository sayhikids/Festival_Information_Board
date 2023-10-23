package com.whiteboard.whiteboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whiteboard.whiteboard.entity.KeepList;

public interface KeepListRepository extends JpaRepository<KeepList, Long> {
   
}
