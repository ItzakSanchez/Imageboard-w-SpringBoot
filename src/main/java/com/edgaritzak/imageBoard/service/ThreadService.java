package com.edgaritzak.imageBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.model.Board;
import com.edgaritzak.imageBoard.model.Media;
import com.edgaritzak.imageBoard.repository.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edgaritzak.imageBoard.model.PostThread;
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
	private MediaRepository mediaRepository;
	
	public Optional<PostThread> findThreadById(Long id) {
		return threadRepo.findById(id);
	}
	public List<PostThread> findAll() {
		return threadRepo.findAll();
	}
	public PostThread saveThread(PostThread thread) {
		return threadRepo.save(thread);
	}

	@Transactional
	public PostThread processNewThread(RequestThreadDTO requestThreadDTO, List<MultipartFile> images) throws IOException {
		PostThread thread = saveThread(requestThreadDTO);
		for(MultipartFile image: images){
			String filename = saveImage(image);
			saveImagePath(thread, filename);
		}
		return thread;
	}


	/*SAVE THREAD*/
	public PostThread saveThread(RequestThreadDTO requestThreadDTO) {

		if(requestThreadDTO.getContent().isBlank() || requestThreadDTO.getContent() == null || requestThreadDTO.getContent().length()>8000) {
			throw new IllegalArgumentException("Posts must be at least 1 characters long and 8000 max");
		}

		Optional<Board> board = boardService.findBoardById(requestThreadDTO.getBoardId());
		if (board.isPresent()){
			PostThread postThread = new PostThread(
					boardIdCounterService.getNextIdAndUpdate(board.get()),
					requestThreadDTO.getAuthorId(),
					requestThreadDTO.getNickname(),
					requestThreadDTO.getContent(),
					board.get(),
					requestThreadDTO.getTitle());
			try {
				return saveThread(postThread);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrityViolationException("An error occurred while saving the thread");
			}
		} else {
			throw new NoSuchElementException("Board not found");
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
	private void saveImagePath(PostThread post, String filename) {
		Media media = new Media(post, filename);
		try {
			mediaRepository.saveAndFlush(media);
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("An error occurred while saving the filename:" + ex);
		}
	}
}
