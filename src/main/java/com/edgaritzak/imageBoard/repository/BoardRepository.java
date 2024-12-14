package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
