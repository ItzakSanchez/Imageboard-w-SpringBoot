package com.edgaritzak.imageBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.edgaritzak.imageBoard.Exceptions.NoImagesUploadedException;
import com.edgaritzak.imageBoard.dto.RequestReplyDTO;
import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.Media;
import com.edgaritzak.imageBoard.model.Post;
import com.edgaritzak.imageBoard.repository.MediaRepository;
import com.edgaritzak.imageBoard.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.PostThread;
import com.edgaritzak.imageBoard.model.Reply;
import com.edgaritzak.imageBoard.repository.ThreadRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ThreadService {

	@Autowired
	private BoardIdCounterService boardIdCounterService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ThreadRepository threadRepo;
	@Autowired
	private ReplyRepository replyRepo;


	@Autowired
	private MediaRepository mediaRepository;
	
	public Optional<PostThread> findThreadById(Long id) {
		return threadRepo.findById(id);
	}
	public List<PostThread> findAll() {
		return threadRepo.findAll();
	}
	
	@Transactional
	public PostThread processNewThread(RequestThreadDTO requestThreadDTO, List<MultipartFile> images) throws IOException {
		if(images.get(0).isEmpty()) {
			throw new NoImagesUploadedException("You must upload at least one image to create a new thread.");
		}
		
		PostThread thread = saveThread(requestThreadDTO);
		for(MultipartFile image: images){
			if(!image.isEmpty()) {		
				String filename = saveImage(image);
				saveImagePath(thread, filename);
			}
		}
		return thread;
	}
	
	@Transactional
	public Reply processNewReply(RequestReplyDTO requestReplyDTO, List<MultipartFile> images) throws IOException {
		Reply reply = saveReply(requestReplyDTO);
		for(MultipartFile image: images){
			if(!image.isEmpty()) {
				String filename = saveImage(image);
				saveImagePath(reply, filename);
			}
		}
		return reply;
	}

	/*SAVE REPLY*/
	public Reply saveReply(RequestReplyDTO requestReplyDTO) {

		if(requestReplyDTO.getContent().isBlank() || requestReplyDTO.getContent() == null || requestReplyDTO.getContent().length()>8000) {
			throw new IllegalArgumentException("Posts must be at least 1 characters long and 8000 max");
		}

		Optional<PostThread> thread = threadRepo.findById(requestReplyDTO.getThreadId());
		Optional<Board> board = boardService.findBoardById(requestReplyDTO.getBoardId());
		if (board.isEmpty()){
			throw new NoSuchElementException("Board not found");
		}
		if(thread.isEmpty()) {
			throw new NoSuchElementException("This thread does not exit");
		}
		Reply reply = new Reply(
				boardIdCounterService.getNextIdAndUpdate(board.get()),
				requestReplyDTO.getAuthorId(),
				requestReplyDTO.getContent(),
				requestReplyDTO.getNickname(),
				board.get(),
				thread.get());
		try {
			return replyRepo.save(reply);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("An error occurred while saving the reply: "+e);
		}
	}

	/*SAVE THREAD*/
	public PostThread saveThread(RequestThreadDTO requestThreadDTO) {

		if(requestThreadDTO.getContent().isBlank() || requestThreadDTO.getContent() == null || requestThreadDTO.getContent().length()>8000) {
			throw new IllegalArgumentException("Posts must be at least 1 characters long and 8000 max");
		}

		Optional<Board> board = boardService.findBoardById(requestThreadDTO.getBoardId());
		if (board.isEmpty()){
			throw new NoSuchElementException("Board not found");
		}
		PostThread postThread = new PostThread(
				boardIdCounterService.getNextIdAndUpdate(board.get()),
				requestThreadDTO.getAuthorId(),
				requestThreadDTO.getNickname(),
				requestThreadDTO.getContent(),
				board.get(),
				requestThreadDTO.getTitle());
		try {
			return threadRepo.save(postThread);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("An error occurred while saving the thread: "+e);
		}
	}

	/*SAVE MEDIA TO LOCAL FOLDER*/
	private String saveImage(MultipartFile image) throws IOException {
		if(image == null || image.isEmpty()) {
			return null;
			//throw new IOException("Can not update the file.");
		}
		if(!image.getContentType().startsWith("image/")) {
			throw new IOException("Only image files.");
		}

		String randomUuid = UUID.randomUUID()+"-"+image.getOriginalFilename();
		Path imagePath = Paths.get("src/main/uploaded_images/"+randomUuid);
		try{
			Files.write(imagePath, image.getBytes());
		} catch (IOException ex){
			throw ex;
		}

		return String.valueOf(randomUuid);
	}

	/*SAVE MEDIA REFERENCE ON DATABASE*/
	private void saveImagePath(Post post, String filename) {
		Media media = new Media(post, filename);
		try {
			mediaRepository.saveAndFlush(media);
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("An error occurred while saving the filename:" + ex);
		}
	}
}