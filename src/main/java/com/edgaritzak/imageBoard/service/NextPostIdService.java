package com.edgaritzak.imageBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.NextPostId;
import com.edgaritzak.imageBoard.repository.NextPostIdRepository;

@Service
public class NextPostIdService {
	
	@Autowired
	private NextPostIdRepository nextPostIdRepo;
	
	public Long getNextIdAndUpdate(Board board) {
		 NextPostId item =nextPostIdRepo.findByBoard(board);
		 Long nextId = item.getId();
		 item.setNextId(item.getNextId()+1);
		 nextPostIdRepo.save(item);
		 
		 return nextId; 
	}
}
