package com.edgaritzak.imageBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.BoardIdCounter;
import com.edgaritzak.imageBoard.repository.BoardRepository;
import com.edgaritzak.imageBoard.repository.BoardIdCounterRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private BoardIdCounterRepository boardIdCounterRepo;

	public Optional<Board> findBoardById(Long id) {
		return boardRepo.findById(id);
	}
	
	public List<Board> findAll() {
		return boardRepo.findAll();
	}


	//CREATE NEW BOARD
	@Transactional
	public Board saveBoardAndCreateBoardIdCounter(Board board) {
		board.setId(0L);
		Board newBoard = boardRepo.save(board);
		BoardIdCounter boardIdCounter = new BoardIdCounter(newBoard,1L);
		boardIdCounterRepo.save(boardIdCounter);
		return newBoard;
	}
}
