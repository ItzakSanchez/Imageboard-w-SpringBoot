package com.edgaritzak.imageBoard.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

import com.edgaritzak.imageBoard.dto.RequestThreadDTO;
import com.edgaritzak.imageBoard.service.BoardService;
import com.edgaritzak.imageBoard.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.edgaritzak.imageBoard.dto.RequestReplyDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class PostController {
		
	@Autowired
	private ThreadService threadService;
	@Autowired
	private BoardService boardService;

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "/";
	}
	

	@RequestMapping("/static/**")
  @ResponseBody
	public ResponseEntity<?>  obtenerArchivo(HttpServletRequest request) {
		String archivoPath = request.getRequestURI().substring("/static".length());

		// File archivo = new File("src/main/resources/static" + archivoPath); //UNUSED
		try {
			Path cssPath = Paths.get("src/main/resources/static"+archivoPath);
			byte[] cssContent = Files.readAllBytes(cssPath);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.valueOf("text/css"));
			return new ResponseEntity<>(cssContent, headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}

		//ClassPathResource archivo = new ClassPathResource("static" + archivoPath); //JAR FILE
		// String contentType = "text/css";

		// return ResponseEntity.ok()
		// 					.header("Content-Type", contentType)
		// 					.body(archivo);
	}

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

	@PostMapping("/uploadThread")
	public String uploadThread(@ModelAttribute RequestThreadDTO requestThreadDTO, @RequestParam("images") List<MultipartFile> images, HttpServletRequest request, HttpServletResponse response) throws IOException,GeneralSecurityException{
		requestThreadDTO.setAuthorId(getCookie(request, response));
		threadService.processNewThread(requestThreadDTO, images);

		try{
			Long boardId = requestThreadDTO.getBoardId();
			String codeName =boardService.findBoardById(boardId).get().getCodeName();
			return "redirect:/"+codeName;
		} catch (Exception Ex){
			return "redirect:/";
		}
	}


	@PostMapping("/uploadReply")
	public String uploadReply(@ModelAttribute RequestReplyDTO requestReplyDTO, @RequestParam("images") List<MultipartFile> images, HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException{
		requestReplyDTO.setAuthorId(getCookie(request,response));
		threadService.processNewReply(requestReplyDTO, images);


		try{
			Long threadId = requestReplyDTO.getThreadId();
			Long boardId = threadService.findThreadById(threadId).get().getBoard().getId();
			String codeName =boardService.findBoardById(boardId).get().getCodeName();
			return "redirect:/"+codeName;
		} catch (Exception Ex){
			return "redirect:/";
		}
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
    
    
	//  @GetMapping("/cookie")
	//  public String cookie(Model model, HttpServletRequest request, HttpServletResponse response) {
	// 	 model.addAttribute("cookie",getCookie(request, response));
	// 	 return "cookie";
	//  }
	
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
