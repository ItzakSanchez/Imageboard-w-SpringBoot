package com.edgaritzak.imageBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.repository.ThreadRepository;

@Service
public class ThreadService {

	@Autowired
	private ThreadRepository threadRepo;
	
	public Optional<PostThread> findThreadById(Long id) {
		return threadRepo.findById(id);
	}
	
	public List<PostThread> findAll() {
		return threadRepo.findAll();
	}
	
	public PostThread saveThread(PostThread thread) {
		return threadRepo.save(thread);
	}
}
