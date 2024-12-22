package com.edgaritzak.imageBoard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

  public Optional<Board> findByCodeName(String codeName);
}
