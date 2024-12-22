package com.edgaritzak.imageBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgaritzak.imageBoard.dto.ResponseThreadWithRepliesDTO;
import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.repository.ThreadRepository;
import com.edgaritzak.imageBoard.service.BoardService;
import com.edgaritzak.imageBoard.service.RenderPostService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class BoardController {


	@Autowired
	private BoardService boardService;

	@Autowired
	private RenderPostService renderPostService;

	@Autowired
	ThreadRepository threadRepository;

	
	// @RequestMapping(path = "/board", method = RequestMethod.GET)
	// public List<Board> findAllBoards(){
	// 	return boardService.findAll();
	// }
	
	// @RequestMapping(path = "/board/{id}", method = RequestMethod.GET)
	// public Board findBoard(@PathVariable(name = "id")Long id){
	// 	return boardService.findBoardById(id).get();
	// }
	
	// @RequestMapping(path = "/board", method = RequestMethod.POST)
	// public Board saveBoard(@RequestBody Board board){
	// 	return boardService.saveBoardAndCreateBoardIdCounter(board);
	// }
	


	@GetMapping("/home")
	public String homePage(Model model){
		model.addAttribute("boardList", boardService.findAll());	
		return "home";
	}

	@GetMapping("/{boardCode}/{page}")
	public String boardPage(@PathVariable("boardCode") String boardCodeName, @PathVariable("page") int page, Model model) {
		if (page == 1){
			return "redirect:/"+boardCodeName;
		}
		model.addAttribute("threads", renderPostService.getThreadsPreview(boardCodeName, page));
		return "homeBoard";
	}

	@GetMapping("/{boardCode}")
	public String boardMainPage(@PathVariable("boardCode") String boardCodeName, Model model) {
		model.addAttribute("board", boardService.findBoardByBoardCodeName(boardCodeName));
		model.addAttribute("threads", renderPostService.getThreadsPreview(boardCodeName, 1));

		System.out.println("Board name: "+boardService.findBoardByBoardCodeName(boardCodeName).getName());

		return "homeBoard";
	}



	// @GetMapping("/threadReplyCounter/{thread}")
	// public int threadReplyCounter(@PathVariable("thread") Long thread) {
	// 	return threadRepository.countRepliesByThreadId(thread);
	// }

	// @GetMapping("/threadMediaCounter/{thread}")
	// public int threadMediaCounter(@PathVariable("thread") Long thread) {
	// 	return threadRepository.countMediaByThreadId(thread);
	// }
}