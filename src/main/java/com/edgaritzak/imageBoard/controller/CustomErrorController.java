package com.edgaritzak.imageBoard.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {
	
	@RequestMapping(path = "/error")
	public String handleError(HttpServletResponse response, Model model) {
		int errorCode = response.getStatus();
		//If was accessed directly, return to Home page
		if(errorCode == HttpStatus.OK.value()) {
			return "redirect:/";
		}
		//Custom 404 page
		if(errorCode == HttpStatus.NOT_FOUND.value()) {
			return "error/404";
		}
		
		//If not 404 error look for error code, use a generic error page
		String errorMessage = switch(errorCode)
		{
			case 500 -> "500 - Internal Server Error";
			case 400 -> "400 - Bad Request";
			case 401 -> "401 - Unauthorized";
			case 403 -> "403 - Forbidden";
			case 502 -> "502 Bad Gateway";
			case 503 -> "503 Service Unavailable";
			default -> "Unknown error";
		};
		
		model.addAttribute("errorMessage", errorMessage);
		return "error/error";
	}	
}
