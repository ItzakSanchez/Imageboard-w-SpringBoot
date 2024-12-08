package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.NextPostId;

public interface NextPostIdRepository extends JpaRepository<NextPostId, Long>{

	public NextPostId findByBoard(Board board);
}
