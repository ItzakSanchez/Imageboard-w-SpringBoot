package com.edgaritzak.imageBoard.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Reply;
import com.edgaritzak.imageBoard.repository.ReplyRepository;
import com.edgaritzak.imageBoard.repository.ThreadRepository;

@Service
public class RenderPostService {
	
	
	int NUMBER_OF_REPLIES_PREVIEW = 3;

	@Autowired
	private ThreadRepository threadRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	
	//GET BOARD ID   ->   RETURN LISTS OF  " RESPONSETHREADWITHREAPLIES DTO "
	
	//CRETE THREAD PAGEABLE
	
	//FIND THREADS BY ID  (FIND THREADS BY BOARD IDD)
	
	//CREATE REPLY PAGEABLE
	
	//FOR EACH THREAD, 
	
	//FIND LAS 3 REPLIES (FIND REPLIES BY THREAD ID)
	//CREATE DTO RESPONSETHREADWITHREAPLIESDTO
	//ADD RESPONSETHREADWITHREAPLIESDTO TO A LIST
	
	
	
//	RETURN LIST OF  " RESPONSETHREADWITHREAPLIES DTO "

	public List<Reply> returnLastReplies(Long threadId) {
		return replyRepository.findByThreadId(threadId);
	}
	
	public List<Reply> returnLastReplies(Long threadId, int numberOfReplies) {
		
		Sort lastReplies = Sort.by(Sort.Order.desc("createdAt"));
		Pageable repliesPageable = PageRequest.of(0, numberOfReplies, lastReplies);
		
		List<Reply> replies  =  replyRepository.findByThreadId(threadId, repliesPageable);
		Collections.reverse(replies);
		return replies;
	}
}
