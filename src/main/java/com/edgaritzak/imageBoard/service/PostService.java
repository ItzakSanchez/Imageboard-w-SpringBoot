package com.edgaritzak.imageBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edgaritzak.imageBoard.dto.PostCreationDTO;
import com.edgaritzak.imageBoard.model.Post;
import com.edgaritzak.imageBoard.repository.PostRepo;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	
	private PostRepo postRepo; 
	
	private final int MAX_NUMBER_PAGES = 9;
	private final int MAX_RESPONSES = 10;
	private final int PAGE_SIZE = 2;
	
	@Autowired
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
	/*TODO 
	* FETCH LAST 3 RESPONSES FOR EACH THREAD"
	* CREATE DTO OR USE A LIST OF LISTS 
	**/
	public List<Post> findAllMainPosts(int page){
		
		int numberOfPages = numberOfPagesForMainPosts();

		if(page < 1 || page > numberOfPages) {
			throw new IndexOutOfBoundsException("No posts availables for page: "+page);
		}
		Sort sort = Sort.by(Sort.Order.desc("isPinned"),Sort.Order.desc("updatedAt"));
		Pageable pageable = PageRequest.of(page-1, PAGE_SIZE, sort);

		try {
			return postRepo.findAllMainPosts(pageable);
		}catch(NoSuchElementException nsee) {
			throw new NoSuchElementException("No main post found: "+nsee);
		}
	}
	
	@Transactional
	public Post processNewPost(PostCreationDTO PostCreationDTO, MultipartFile image) throws IOException {
		Post post = savePost(PostCreationDTO);
		String filename = saveImage(image);
		post = updateImagePath(post, filename);
		updateParentPost(post);
		return post;
	}
	
	/*SAVE POST*/
	private Post savePost(PostCreationDTO PostCreationDTO) {
		if(PostCreationDTO.getIdposter() == null) {
			PostCreationDTO.setIdposter("0");
		}
		if(PostCreationDTO.getContent().isBlank() || PostCreationDTO.getContent() == null || PostCreationDTO.getContent().length()>8000) {
			throw new IllegalArgumentException("Posts must be at least 1 characters long and 8000 max");
		}
		if(PostCreationDTO.getParentId() == null) {
			throw new NullPointerException("An error has occurred while getting post's parentID");
		}
		System.out.println("PostCreationDTO Nickname: "+PostCreationDTO.getNickname());
		Post newPost = new Post.Builder(PostCreationDTO)
				.nickname(PostCreationDTO.getNickname())
				.title(PostCreationDTO.getTitle())
				.build();
		System.out.println("newPost Nickname: "+newPost.getNickname());

		try {
			Post savedPost = postRepo.save(newPost);
			return(savedPost);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("An error occurred while saving the publication to the database");
		}
	}
	
	/*SAVE IMAGE*/
	private String saveImage(MultipartFile image) throws IOException {
		if(image.isEmpty() || image == null) {
			return null;
			//throw new IOException("Can not update the file.");
		}
		if(!image.getContentType().startsWith("image/")) {
			throw new IOException("Only image files.");
		}
		
		String randomUuid = UUID.randomUUID()+"-"+image.getOriginalFilename();
		Path imagePath = Paths.get("src/main/uploaded_images/"+randomUuid);
		Files.write(imagePath, image.getBytes());
		return String.valueOf(randomUuid);
	}
	
	/*UPDATE IMAGE PATH*/
	private Post updateImagePath(Post post, String imagePath) {
		post.setImagePath(imagePath);
		try {
			return postRepo.save(post);
		} catch(DataIntegrityViolationException dive) {
			throw new DataIntegrityViolationException("An error occurred while updating the imagePath:" + dive);
		}
	}
	
	/*UPDATE PARENT POST*/
	private boolean updateParentPost(Post post) {
		//if the post has a parent (is a reply) then update parent post date and reply count
		if (post.getParentId() != 0){
			Post parentPost = postRepo.findById(post.getParentId()).orElseThrow(()-> new NoSuchElementException("Parent post not found for updating last update date"));
			
			if(parentPost.getResponseCount() < MAX_RESPONSES) {
				parentPost.setUpdateLastUpdate();
			}
			
			parentPost.setResponseCount(parentPost.getResponseCount()+1);
			
			try {
				postRepo.save(parentPost);
				return true;
			} catch(DataIntegrityViolationException dive) {
				throw new DataIntegrityViolationException("An error ocurred while updating parent last update date: " + dive);
			}
		}
		else return false;
	}
	
	public int numberOfPagesForMainPosts() {
		int numberOfItems = postRepo.findAllMainPosts().size();
		int numberOfPages =  (numberOfItems+PAGE_SIZE-1)/PAGE_SIZE;

		if (numberOfPages>MAX_NUMBER_PAGES) {
			numberOfPages = MAX_NUMBER_PAGES;
		}
		
		return numberOfPages;
	}
}
