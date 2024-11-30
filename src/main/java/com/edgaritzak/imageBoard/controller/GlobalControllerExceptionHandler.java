package com.edgaritzak.imageBoard.controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	//Bad Request
	@ExceptionHandler({IllegalArgumentException.class, HttpRequestMethodNotSupportedException.class})
	public String illegalArgumentExceptionHandler(Model model, HttpServletResponse response, Exception ex) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/errorPage";
	}
	//Generic Error
	@ExceptionHandler(Exception.class)
	public String genericExceptionHandler(Model model, HttpServletResponse response, Exception ex) {
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/errorPage";
	}
}
