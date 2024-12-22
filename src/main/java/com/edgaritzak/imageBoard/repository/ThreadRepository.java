package com.edgaritzak.imageBoard.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edgaritzak.imageBoard.model.PostThread;

public interface ThreadRepository extends JpaRepository<PostThread, Long>{
	
	@Query("SELECT COUNT(r) from Reply r where r.thread.id =:threadId")
	public int countRepliesByThreadId(@Param("threadId") Long threadId);

	@Query("SELECT COUNT (t) from PostThread t where t.board.id =:boardId")
	public int countThreadsByBoardId(@Param("boardId") Long boardId);

	@Query("SELECT COUNT (m) from Media m  where m.post.thread.id =:threadId")
	public int countMediaByThreadId(@Param("threadId") Long threadId);

	public List<PostThread> findByBoardId(Long boardId, Pageable pageable);
	public List<PostThread> findByBoardId(Long boardId);

}
