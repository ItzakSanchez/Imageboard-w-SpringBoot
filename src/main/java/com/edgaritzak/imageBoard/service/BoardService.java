package com.edgaritzak.imageBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepo;
	
	public Optional<Board> findBoardById(Long id) {
		return boardRepo.findById(id);
	}
	
	public List<Board> findAll() {
		return boardRepo.findAll();
	}
	
	public Board saveBoard(Board board) {
		board.setId(0L);
		return boardRepo.save(board);
	}
}
