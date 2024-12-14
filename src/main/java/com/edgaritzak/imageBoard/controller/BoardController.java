<<<<<<< HEAD
package com.edgaritzak.imageBoard.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.service.BoardIdCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.service.BoardService;
import com.edgaritzak.imageBoard.service.ThreadService;

@RestController
public class BoardController {

	@Autowired
	private BoardIdCounterService boardIdCounterService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ThreadService threadService;
	
	
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
	


	
	
	@RequestMapping(path = "/thread", method = RequestMethod.GET)
	public List<PostThread> findAllThreads(){
		return threadService.findAll();
	}
	
	@RequestMapping(path = "/thread/{id}", method = RequestMethod.GET)
	public PostThread findThread(@PathVariable(name = "id")Long id){
		return threadService.findThreadById(id).get();
	}
	
	@RequestMapping(path = "/thread", method = RequestMethod.POST)
	public PostThread saveThread(@RequestBody RequestThreadDTO requestThreadDTO){
		return threadService.saveThread(requestThreadDTO);
	}
}
=======
package com.edgaritzak.imageBoard.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.service.BoardIdCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.service.BoardService;
import com.edgaritzak.imageBoard.service.ThreadService;

@RestController
public class BoardController {

	@Autowired
	private BoardIdCounterService boardIdCounterService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ThreadService threadService;
	
	
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
	


	
	
	@RequestMapping(path = "/thread", method = RequestMethod.GET)
	public List<PostThread> findAllThreads(){
		return threadService.findAll();
	}
	
	@RequestMapping(path = "/thread/{id}", method = RequestMethod.GET)
	public PostThread findThread(@PathVariable(name = "id")Long id){
		return threadService.findThreadById(id).get();
	}
	
	@RequestMapping(path = "/thread", method = RequestMethod.POST)
	public PostThread saveThread(@RequestBody RequestThreadDTO requestThreadDTO){
		return threadService.saveThread(requestThreadDTO);
	}
}
>>>>>>> 7a9c4e126cdc1ac05c7c7c9741595f4b40c47553
