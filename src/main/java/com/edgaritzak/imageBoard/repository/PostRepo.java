package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
	
}