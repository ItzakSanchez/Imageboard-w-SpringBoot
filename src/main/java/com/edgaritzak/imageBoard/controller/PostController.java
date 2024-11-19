package com.edgaritzak.imageBoard.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.edgaritzak.imageBoard.dto.PostDTO;
import com.edgaritzak.imageBoard.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PostController {
	
	@Autowired
	private PostService postSvc;
	
	@GetMapping("/posts")
	public String posts(Model model) {
		model.addAttribute("posts",postSvc.findAllMainPosts());
		return "posts";
	}
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	
	@PostMapping("/upload")
	public String newPost(@ModelAttribute PostDTO postDto, @RequestParam(value="image") MultipartFile image, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String idPoster = getCookie(request, response);
		postDto.setIdposter(idPoster);
		postSvc.processNewPost(postDto, image);
		return "postedSuccessfully";
	}
	
	 @GetMapping("/imageController")
	 public String imageController() {
		 return "imageController";
	 }
	 
	 @GetMapping("/cookie")
	 public String cookie(Model model, HttpServletRequest request, HttpServletResponse response) {
		 model.addAttribute("cookie",getCookie(request, response));
		 return "cookie";
	 }
    
    @GetMapping("/image/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
    	String IMAGE_DIR = "src\\main\\uploaded_images\\";
        Path imagePath = Paths.get(IMAGE_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(imagePath.toUri());
        if (resource.exists() || resource.isReadable()) {
        	String contentType = Files.probeContentType(imagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	public String getCookie(HttpServletRequest request, HttpServletResponse response) {
		String idPoster;
		if(getIdPosterFromCookie(request) != null) {
			idPoster = getIdPosterFromCookie(request);
		} else {
			idPoster = createIdPosterCookie(response);
		}		
		return idPoster;
	}
	
	public String getIdPosterFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();	
		if (cookies != null) {
			for(Cookie cookie:cookies) {
				if ("imageboard_idPoster".equals(cookie.getName())){
					return(cookie.getValue());
				}
			}
		}
		return null;
	}
	
	public String createIdPosterCookie (HttpServletResponse response) {
		String idPoster = String.valueOf(UUID.randomUUID());
		Cookie cookie = new Cookie("imageboard_idPoster", idPoster);
		cookie.setMaxAge(60*60*24*180);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		return idPoster;
	}
}
