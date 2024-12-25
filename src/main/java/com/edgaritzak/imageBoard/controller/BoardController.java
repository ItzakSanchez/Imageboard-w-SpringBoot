package com.edgaritzak.imageBoard.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.repository.ThreadRepository;
import com.edgaritzak.imageBoard.service.BoardService;
import com.edgaritzak.imageBoard.service.RenderPostService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class BoardController {


	@Autowired
	private BoardService boardService;

	@Autowired
	private RenderPostService renderPostService;

	@Autowired
	ThreadRepository threadRepository;

	@GetMapping("")
	public String homePage(Model model){
		model.addAttribute("boardList", boardService.findAllByOrderByIdAsc());
		return "home";
	}

	@GetMapping("/{boardCode}")
	public String redirectToBoard(@PathVariable("boardCode") String boardCodeName) {
		return "redirect:/"+boardCodeName+"/";
	}

	@GetMapping("/{boardCode}/") 
	public String boardMainPage(@PathVariable("boardCode") String boardCodeName, Model model) {
		System.out.println("TRYING TO ACCESS BOARD: "+boardCodeName);
		int numberOfPages = renderPostService.findNumberOfPagesByBoardCodeName(boardCodeName);

		model.addAttribute("board", boardService.findBoardByBoardCodeName(boardCodeName));
		model.addAttribute("currentPage", 1);
		model.addAttribute("numberOfPages", numberOfPages);
		if (numberOfPages > 1){
			model.addAttribute("nextPage", 2); //here I have to add "codeName/" manually
		}
		model.addAttribute("threads", renderPostService.getThreadsPreview(boardCodeName, 1));
		return "homeBoard";
	}
		
	@GetMapping("/{boardCode}/{page}")
	public String boardPage(@PathVariable("boardCode") String boardCodeName, @PathVariable("page") int page, Model model) {
		if (page == 1){
			return "redirect:/"+boardCodeName+"/";
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

	@GetMapping("/{boardCode}/thread/{threadPostNumber}")
	public String displayThread(@PathVariable("boardCode") String boardCodeName, @PathVariable("threadPostNumber") Long threadPostNumber, Model model) {

		model.addAttribute("boardCodeName", boardCodeName);
		model.addAttribute("thread", renderPostService.getFullThread(boardCodeName, threadPostNumber));

		return "threadTemplate";
	}




	@GetMapping("/admin/board")
	public String getMethodName(Model model) {
		model.addAttribute("boardList", boardService.findAllByOrderByIdAsc());
		return "boardPanel";
	}

	@PostMapping("/admin/board/addBoard")
	public String addBoard(@Valid @ModelAttribute Board board, Model model) {
		boardService.saveBoardAndCreateBoardIdCounter(board);
		return "redirect:/admin/board";
	}

	@PostMapping("/admin/board/updateBoardPage")
	public String displayUpdateBoard(@RequestParam("id") Optional<Long> id, Model model) {
		if (id.isPresent()){
			model.addAttribute("board", boardService.findBoardById(id.get()).get());
		}
		return "boardEditPanel";
	}

	@PostMapping("/admin/board/updateBoard")
	public String updateBoard(@Valid @ModelAttribute Board board, Model model) {
		System.out.println(board);
		System.out.println(board.getId());
		
		boardService.updateBoard(board);
		return "redirect:/admin/board";
	}

	@PostMapping("/admin/board/deleteBoard/{boardId}")
	public String deleteBoard(@PathVariable Long boardId) {
		boardService.deleteBoard(boardId);
		return "redirect:/admin/board";
	}

}