<<<<<<< HEAD
package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.BoardIdCounter;

public interface BoardIdCounterRepository extends JpaRepository<BoardIdCounter, Long>{

	public BoardIdCounter findByBoard(Board board);
}
=======
package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.BoardIdCounter;

public interface BoardIdCounterRepository extends JpaRepository<BoardIdCounter, Long>{

	public BoardIdCounter findByBoard(Board board);
}
>>>>>>> 7a9c4e126cdc1ac05c7c7c9741595f4b40c47553
