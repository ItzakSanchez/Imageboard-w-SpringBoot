package com.edgaritzak.imageBoard.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.edgaritzak.imageBoard.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
	
	@Query(value="SELECT p FROM Post p where parentId = 0")
	public List<Post> findAllMainPosts(Pageable pageable);
	@Query(value="SELECT p FROM Post p where parentId = 0")
	public List<Post> findAllMainPosts();
	
	@Query(value="SELECT p FROM Post p where parentId = :parentId")
	public List<Post> findAllResponsesByMainId(@Param("parentId") Long parentId, Pageable pageable);
	@Query(value="SELECT p FROM Post p where parentId = :parentId")
	public List<Post> findAllResponsesByMainId(@Param("parentId") Long parentId);
	
	public List<Post> findByParentId(long id);
}
