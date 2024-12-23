package com.edgaritzak.imageBoard.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.dto.ResponseReplyDTO;
import com.edgaritzak.imageBoard.dto.ResponseThreadDTO;
import com.edgaritzak.imageBoard.dto.ResponseThreadWithRepliesDTO;
import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.model.Reply;
import com.edgaritzak.imageBoard.repository.BoardRepository;
import com.edgaritzak.imageBoard.repository.ReplyRepository;
import com.edgaritzak.imageBoard.repository.ThreadRepository;

@Service
public class RenderPostService {
	
	
	private int NUMBER_OF_REPLIES_PREVIEW = 3;
	private int NUMBER_OF_THREADS_PER_PAGE = 2;
	private int MAX_NUMBER_OF_PAGES = 10;

	@Autowired
	private ThreadRepository threadRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private BoardRepository boardRepository;
	

	public List<Reply> returnAllReplies(Long threadId) {
		return replyRepository.findByThreadId(threadId);
	}
	
	public List<Reply> returnLastReplies(Long threadId, int numberOfReplies) {
		Sort lastReplies = Sort.by(Sort.Order.desc("createdAt"));
		Pageable repliesPageable = PageRequest.of(0, numberOfReplies, lastReplies);
		List<Reply> replies  =  replyRepository.findByThreadId(threadId, repliesPageable);
		Collections.reverse(replies);
		return replies;
	}


	public int findNumberOfPagesByBoardCodeName(String boardCode){
		Optional<Board> optionalBoard = boardRepository.findByCodeName(boardCode);
		if (optionalBoard.isEmpty()){
			throw new NoSuchElementException("Board Not Found 1");
		}
		Board board = optionalBoard.get();
		Long boardId = board.getId();

		//Find Number of pages available and if provide page is valid
		int numberOfPages= (threadRepository.countThreadsByBoardId(boardId)+NUMBER_OF_THREADS_PER_PAGE-1)/NUMBER_OF_THREADS_PER_PAGE;
		if (numberOfPages > MAX_NUMBER_OF_PAGES) numberOfPages= MAX_NUMBER_OF_PAGES;
		if (numberOfPages < 1) numberOfPages= 1;
		return numberOfPages;
	}


	
	//SHOW LIST OF OF PREVIEWS 
	public List<ResponseThreadWithRepliesDTO> getThreadsPreview(String boardCode, int page){
		Board board = boardRepository.findByCodeName(boardCode).orElseThrow(()-> new NoSuchElementException("Board Not found 2"));
		Long boardId = board.getId();

		//Find Number of pages available and if provide page is valid
		int numberOfPages= (threadRepository.countThreadsByBoardId(boardId)+NUMBER_OF_THREADS_PER_PAGE-1)/NUMBER_OF_THREADS_PER_PAGE;
		if (numberOfPages > MAX_NUMBER_OF_PAGES) numberOfPages= MAX_NUMBER_OF_PAGES;
		if (numberOfPages < 1) numberOfPages= 1;
		if (page < 1 || page > numberOfPages){
			throw new IndexOutOfBoundsException("No posts availables for page: "+page);
		}

		//CRETE THREAD PAGEABLE AND GET RAW THREADS
		Sort threadSprt = Sort.by(Sort.Order.desc("isPinned"), Sort.Order.desc("lastBumpAt"));
		Pageable threadPageable = PageRequest.of(page-1, NUMBER_OF_THREADS_PER_PAGE, threadSprt);
		List<PostThread> rawThreads = threadRepository.findByBoardId(boardId, threadPageable);		
	

		// MAP RAW THREADS TO DTO WITH REPLIES
		List<ResponseThreadWithRepliesDTO> previews = rawThreads.stream()
				.map(thread -> new ResponseThreadWithRepliesDTO(
						new ResponseThreadDTO(
							thread.getId(),
							thread.getPostNumber(),
							thread.getAuthorId(),
							thread.getNickname(),
							thread.getTitle(),
							thread.getContent(),
							thread.getCreatedAt(),
							thread.getMedia().stream().map(media -> media.getFilename()).collect(Collectors.toList()), //FIND FILENAMES BY POST ID
							threadRepository.countRepliesByThreadId(thread.getId()),
							threadRepository.countMediaByThreadId(thread.getId())
							), 
							//GET 3 REPLIES FOR EACH THREAD
							returnLastReplies(thread.getId(), 3).stream()
								.map(reply -> new ResponseReplyDTO(
									reply.getPostNumber(),
									reply.getAuthorId(),
									reply.getNickname(),
									reply.getContent(),
									reply.getCreatedAt(),
									reply.getMedia().stream().map(media -> media.getFilename()).collect(Collectors.toList()) //FIND FILENAMES BY POST ID
									))
								.collect(Collectors.toList())
				))
				.collect(Collectors.toList());

		return previews;
	}


	//SHOW FULL THREAD
	public ResponseThreadWithRepliesDTO getFullThread(String boardCode, Long postNumber){
		Board board = boardRepository.findByCodeName(boardCode).orElseThrow(()-> new NoSuchElementException("Board Not found 3"));
		Long boardId = board.getId();


		PostThread rawThread;
		try{
			rawThread = threadRepository.findByBoardIdAndPostNumber(boardId, postNumber);		
		} catch (IncorrectResultSizeDataAccessException Ex){
			throw Ex;
		}


		// MAP THREAD TO DTO WITH REPLIES
		ResponseThreadWithRepliesDTO threadDTO = new ResponseThreadWithRepliesDTO(
					new ResponseThreadDTO(
						rawThread.getId(),
						rawThread.getPostNumber(),
						rawThread.getAuthorId(),
						rawThread.getNickname(),
						rawThread.getTitle(),
						rawThread.getContent(),
						rawThread.getCreatedAt(),
						rawThread.getMedia().stream().map(media -> media.getFilename()).collect(Collectors.toList()), //FIND FILENAMES BY POST ID
						threadRepository.countRepliesByThreadId(rawThread.getId()),
						threadRepository.countMediaByThreadId(rawThread.getId())
						), 
						//GET ALL REPLIES FOR EACH THREAD
						returnAllReplies(rawThread.getId()).stream()
							.map(reply -> new ResponseReplyDTO(
								reply.getPostNumber(),
								reply.getAuthorId(),
								reply.getNickname(),
								reply.getContent(),
								reply.getCreatedAt(),
								reply.getMedia().stream().map(media -> media.getFilename()).collect(Collectors.toList()) //FIND FILENAMES BY POST ID
								))
							.collect(Collectors.toList())
			);
		return threadDTO;
	}
}
