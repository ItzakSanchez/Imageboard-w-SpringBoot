package com.edgaritzak.imageBoard.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

	List<Reply> findByThreadId(Long threadId, Pageable pageable);
	List<Reply> findByThreadId(Long threadId);
	

}
