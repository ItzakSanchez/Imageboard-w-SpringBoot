package com.edgaritzak.imageBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.NextPostId;
import com.edgaritzak.imageBoard.repository.BoardRepository;
import com.edgaritzak.imageBoard.repository.NextPostIdRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private  NextPostIdRepository nextPostIdRepo;

	public Optional<Board> findBoardById(Long id) {
		return boardRepo.findById(id);
	}
	
	public List<Board> findAll() {
		return boardRepo.findAll();
	}
	
	@Transactional
	public Board saveBoardAndCreateNextPostId(Board board) {
		board.setId(0L);
		Board newBoard = boardRepo.save(board);
		NextPostId nextPostId = new NextPostId(newBoard,1L);
		nextPostId = nextPostIdRepo.save(nextPostId);
		return newBoard;
	}
}
