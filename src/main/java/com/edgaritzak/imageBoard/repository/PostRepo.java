package com.edgaritzak.imageBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edgaritzak.imageBoard.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
	
	@Query(value="SELECT p FROM Post p where parentId = 0")
	public List<Post> findAllMainPosts();
}
