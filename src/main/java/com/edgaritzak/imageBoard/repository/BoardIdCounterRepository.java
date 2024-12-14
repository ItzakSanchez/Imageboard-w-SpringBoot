package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.BoardIdCounter;

public interface BoardIdCounterRepository extends JpaRepository<BoardIdCounter, Long>{

	public BoardIdCounter findByBoard(Board board);
}
