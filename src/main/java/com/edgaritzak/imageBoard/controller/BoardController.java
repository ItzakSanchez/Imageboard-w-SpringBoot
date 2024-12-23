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
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class BoardController {


	@Autowired
	private BoardService boardService;

	@Autowired
	private RenderPostService renderPostService;

	@Autowired
	ThreadRepository threadRepository;

	// T E S T  F U N C T I O N 
	// @ResponseBody
	// @GetMapping("/findByBoardIdAndPostNumber/{boardId}/{postNumber}")
	// public Long getMethodName(@PathVariable("boardId") Long boardId, @PathVariable("postNumber") Long postNumber) {
	// 	System.out.println("EXECUTING findByBoardIdAndPostNumber");
	// 	PostThread thread =threadRepository.findByBoardIdAndPostNumber(boardId, postNumber);

	// 	return thread.getId();
	// }
	

	
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

		int numberOfPages = renderPostService.findNumberOfPagesByBoardCodeName(boardCodeName);
		model.addAttribute("board", boardService.findBoardByBoardCodeName(boardCodeName));
		model.addAttribute("currentPage", page);
		model.addAttribute("previousPage", page-1);
		model.addAttribute("numberOfPages", numberOfPages);
		if (page < numberOfPages){
			model.addAttribute("nextPage", page+1); //For some strange reason, "/codeName/page" is added when rendering in Thymeleaf
		}

		model.addAttribute("threads", renderPostService.getThreadsPreview(boardCodeName, page));
		return "homeBoard";
	}

	@GetMapping("/{boardCode}")
	public String boardMainPage(@PathVariable("boardCode") String boardCodeName, Model model) {

		int numberOfPages = renderPostService.findNumberOfPagesByBoardCodeName(boardCodeName);

		model.addAttribute("board", boardService.findBoardByBoardCodeName(boardCodeName));
		model.addAttribute("currentPage", 1);
		model.addAttribute("numberOfPages", numberOfPages);
		if (numberOfPages > 1){
			model.addAttribute("nextPage", boardCodeName+"/2"); //here I have to add "codeName/" manually
		}
		model.addAttribute("threads", renderPostService.getThreadsPreview(boardCodeName, 1));
		return "homeBoard";
		}
		


		//T E S T
		// @GetMapping("/{boardCode}/thread/{threadPostNumber}")
		// public String displayThread(@PathVariable("boardCode") String boardCodeName, @PathVariable("threadPostNumber") Long threadPostNumber, Model model) {
		// 	//GET THREAD ID
		// 	renderPostService.getFullThread(boardCodeName, threadPostNumber);

		// 	model.addAttribute("boardCodeName", boardCodeName);
		// 	model.addAttribute("thread", renderPostService.getFullThread(boardCodeName, threadPostNumber));

		// 	return "threadTemplate";
		// }


	// @GetMapping("/threadReplyCounter/{thread}")
	// public int threadReplyCounter(@PathVariable("thread") Long thread) {
	// 	return threadRepository.countRepliesByThreadId(thread);
	// }

	// @GetMapping("/threadMediaCounter/{thread}")
	// public int threadMediaCounter(@PathVariable("thread") Long thread) {
	// 	return threadRepository.countMediaByThreadId(thread);
	// }
}