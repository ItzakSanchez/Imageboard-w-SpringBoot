package com.edgaritzak.imageBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.BoardIdCounter;
import com.edgaritzak.imageBoard.repository.BoardIdCounterRepository;

@Service
public class BoardIdCounterService {
	
	@Autowired
	private BoardIdCounterRepository boardIdCounterRepository;
	
	public Long getNextIdAndUpdate(Board board) {
		 BoardIdCounter counter = boardIdCounterRepository.findByBoard(board);
		 Long nextId = counter.getNextId();
		 counter.setNextId(counter.getNextId()+1);
		 boardIdCounterRepository.save(counter);
		 return nextId; 
	}
}
