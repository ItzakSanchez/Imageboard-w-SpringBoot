package com.edgaritzak.imageBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edgaritzak.imageBoard.model.Media;
import com.edgaritzak.imageBoard.model.Post;
import com.edgaritzak.imageBoard.repository.MediaRepository;

@Service
public class MediaService {
	
	private final String FILE_PATH = "src\\main\\uploaded_images\\";

	@Autowired
	private MediaRepository mediaRepo;
	
	public String saveMediaFile (MultipartFile file) throws IOException{
		String filename = UUID.randomUUID()+"_"+file.getOriginalFilename();
		Path path= Paths.get(FILE_PATH+filename);
		try{
			Files.write(path, file.getBytes());
		} catch(IOException ioex) {
			ioex.printStackTrace();
		}
		return filename;
	}
	
	public Media saveMedia(Post post,String filename) {
		Media media = new Media(post, filename);
		return  mediaRepo.saveAndFlush(media);
	}
}
