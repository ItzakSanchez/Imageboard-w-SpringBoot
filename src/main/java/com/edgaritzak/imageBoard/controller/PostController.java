package com.edgaritzak.imageBoard.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.edgaritzak.imageBoard.dto.RequestReplyDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PostController {
	
	int pageSize = 2;
	
	@Autowired
	private ThreadService threadService;


	@GetMapping("/favicon.ico")
	public ResponseEntity<Resource> getFavicon() throws MalformedURLException {
			Path faviconPath = Paths.get("src/main/resources/static/favicon.ico");
			Resource faviconResource = new UrlResource(faviconPath.toUri());
			if (faviconResource.exists() && faviconResource.isReadable()) {
					return ResponseEntity.ok()
									.contentType(MediaType.IMAGE_PNG) 
									.body(faviconResource);
			} else {
					return ResponseEntity.notFound().build();
			}
	}

	@GetMapping("/postThread")
	public String postThread() {
		return "postThread";
	}
	
	@GetMapping("/postReply")
	public String postReply() {
		return "postReply";
	}

	@PostMapping("/uploadThread")
	public String uploadThread(@ModelAttribute RequestThreadDTO requestThreadDTO, @RequestParam("images") List<MultipartFile> images, HttpServletRequest request, HttpServletResponse response) throws IOException {
		requestThreadDTO.setAuthorId(getCookie(request, response));
		threadService.processNewThread(requestThreadDTO, images);
		return "postedSuccessfully";
	}


	@PostMapping("/uploadReply")
	public String uploadReply(@ModelAttribute RequestReplyDTO requestReplyDTO, @RequestParam("images") List<MultipartFile> images, HttpServletRequest request, HttpServletResponse response) throws IOException{
		requestReplyDTO.setAuthorId(getCookie(request,response));
		threadService.processNewReply(requestReplyDTO, images);
		return "postedSuccessfully";
	}
	
	
	 @GetMapping("/imageController")
	 public String imageController() {
		 return "imageController";
	 }
	 

    
    @RequestMapping(path ={"/image/{filename}","posts/image/{filename}"}, method = RequestMethod.GET)
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
    
    
	 @GetMapping("/cookie")
	 public String cookie(Model model, HttpServletRequest request, HttpServletResponse response) {
		 model.addAttribute("cookie",getCookie(request, response));
		 return "cookie";
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
