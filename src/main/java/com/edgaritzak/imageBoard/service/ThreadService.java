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
import com.edgaritzak.imageBoard.repository.PostRepository;
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
	
	private int MAX_BUMP = 100;

	@Autowired
	private BoardIdCounterService boardIdCounterService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ThreadRepository threadRepo;
	@Autowired
	private ReplyRepository replyRepo;
	@Autowired
	private PostRepository postRepository;


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
		if(images.size()>4){
			throw new IllegalArgumentException("The maximum number of images per post is 4");
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
		if(images.size()>4){
			throw new IllegalArgumentException("The maximum number of images per post is 4");
		}
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
		if(thread.isEmpty()) {
			throw new NoSuchElementException("This thread does not exit");
		}
		Optional<Board> board = boardService.findBoardById(thread.get().getBoard().getId());
		if (board.isEmpty()){
			throw new NoSuchElementException("Board not found");
		}
		
		Reply reply = new Reply(
				boardIdCounterService.getNextIdAndUpdate(board.get()),
				requestReplyDTO.getAuthorId(),
				requestReplyDTO.getContent(),
				requestReplyDTO.getNickname(),
				thread.get());
		try {

			//UPDATE DATE OF THREAD
			thread.get().setUpdatedAtNow();
			//FIND NUMBER OF REPLIES
			int numberOfReplies = threadRepo.countRepliesByThreadId(thread.get().getId());
			//IF THE NUMBER OF REPLIES IS LESS THAN MAX BUMPS, UPDATE THE DATE OF LAST BUMP
			if (numberOfReplies <= MAX_BUMP) {
				thread.get().setLastBumpAtNow();
			}
			
			//SAVER REPLY
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
				requestThreadDTO.getContent(),
				requestThreadDTO.getNickname(),
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

	/*SAVE MEDIA FILENAME ON DATABASE*/
	private void saveImagePath(Post post, String filename) {
		Media media = new Media(post, filename);
		try {
			mediaRepository.saveAndFlush(media);
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("An error occurred while saving the filename:" + ex);
		}
	}

	public void deletePost(Long id){
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isEmpty()){
			throw new NoSuchElementException("Error finding post with id: "+id+" for deletion");
		}
		postRepository.deleteById(id);
	}
}