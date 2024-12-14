package com.edgaritzak.imageBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.service.BoardService;

@RestController
public class BoardController {


	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(path = "/board", method = RequestMethod.GET)
	public List<Board> findAllBoards(){
		return boardService.findAll();
	}
	
	@RequestMapping(path = "/board/{id}", method = RequestMethod.GET)
	public Board findBoard(@PathVariable(name = "id")Long id){
		return boardService.findBoardById(id).get();
	}
	
	@RequestMapping(path = "/board", method = RequestMethod.POST)
	public Board saveBoard(@RequestBody Board board){
		return boardService.saveBoardAndCreateBoardIdCounter(board);
	}
	
}