package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edgaritzak.imageBoard.model.PostThread;

public interface ThreadRepository extends JpaRepository<PostThread, Long>{
	
	@Query("SELECT COUNT(r) from Reply r where r.thread.id =:threadId")
	public int countRepliesByThreadId(@Param("threadId") Long threadId);

}
