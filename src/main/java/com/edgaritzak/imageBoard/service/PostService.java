package com.edgaritzak.imageBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edgaritzak.imageBoard.dto.PostDTO;
import com.edgaritzak.imageBoard.model.Post;
import com.edgaritzak.imageBoard.repository.PostRepo;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	
	private PostRepo postRepo; 
	@Autowired
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
	
	@Transactional
	public Post processNewPost(PostDTO postDTO, MultipartFile image) throws IOException {
		Post post = savePost(postDTO);
		String path = saveImage(image);
		post = updateImagePath(post, path);
		updateParentPost(post);
		return post;
	}
	
	/*SAVE POST*/
	public Post savePost(PostDTO postDTO) {
		if(postDTO.getIdposter() == null) {
			throw new IllegalArgumentException("An error occurred while generating/getting the author ID cookie");
		}
		if(postDTO.getContent().isBlank() || postDTO.getContent() == null || postDTO.getContent().length()>8000) {
			throw new IllegalArgumentException("Posts must be at least 1 characters long and 8000 max");
		}
		if(postDTO.getParentId() == null) {
			throw new IllegalArgumentException("An error has occurred while getting post's parentID");
		}
		System.out.println("postDTO Nickname: "+postDTO.getNickname());
		Post newPost = new Post.Builder(postDTO)
				.nickname(postDTO.getNickname())
				.title(postDTO.getTitle())
				.build();
		System.out.println("newPost Nickname: "+newPost.getNickname());

		try {
			Post savedPost = postRepo.save(newPost);
			return(savedPost);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("An error occurred while saving the publication to the database: " + e.getMessage());
		}
	}
	
	/*SAVE IMAGE*/
	public String saveImage(MultipartFile image) throws IOException {
		if(image.isEmpty() || image == null) {
			return null;
			//throw new IOException("Can not update the file.");
		}
		if(!image.getContentType().startsWith("image/")) {
			throw new IOException("Only image files.");
		}
		
		String randomUuid = UUID.randomUUID()+"-"+image.getOriginalFilename();
		Path imagePath = Paths.get("src/main/resources/uploads/images/"+randomUuid);
		Files.write(imagePath, image.getBytes());
		return String.valueOf(imagePath);
	}
	
	/*UPDATE IMAGE PATH*/
	public Post updateImagePath(Post post, String imagePath) {
		post.setImagePath(imagePath);
		try {
			return postRepo.save(post);
		} catch(DataIntegrityViolationException dive) {
			throw new DataIntegrityViolationException("An error occurred while updating the imagePath:" + dive);
		}
	}
	
	/*UPDATE PARENT POST*/
	public boolean updateParentPost(Post post) {
		if (post.getParentId() != 0){
			Post parentPost = postRepo.findById(post.getParentId()).orElseThrow(()-> new NoSuchElementException("Parent post not found for updating last update date"));
			parentPost.setUpdateLastUpdate();
			
			try {
				postRepo.save(parentPost);
				return true;
			} catch(DataIntegrityViolationException dive) {
				throw new DataIntegrityViolationException("An error ocurred while updating parent last update date: " + dive);
			}
		}
		else return false;
	}
}
