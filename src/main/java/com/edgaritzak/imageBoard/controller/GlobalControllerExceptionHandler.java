package com.edgaritzak.imageBoard.controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	//Bad Request
	@ExceptionHandler({IllegalArgumentException.class, HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String illegalArgumentExceptionHandler(Model model, Exception ex) {
		//response.setStatus(HttpStatus.BAD_REQUEST.value()); -> HttpServletResponse response
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/errorPage";
	}
	
	//Generic Error
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String genericExceptionHandler(Model model, Exception ex) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/errorPage";
	}
}
