package com.edgaritzak.imageBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Reply;
import com.edgaritzak.imageBoard.repository.ReplyRepository;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepo;
	
	public Optional<Reply> findReplyById(Long id) {
		return replyRepo.findById(id);
	}
	
	public List<Reply> findAll() {
		return replyRepo.findAll();
	}
	
	public Reply saveReply(Reply reply) {
		return replyRepo.save(reply);
	}
}
