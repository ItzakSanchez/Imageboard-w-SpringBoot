package com.edgaritzak.imageBoard.controller;

import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
