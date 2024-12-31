package com.edgaritzak.imageBoard.controller;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.edgaritzak.imageBoard.Exceptions.NoImagesUploadedException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String methodArgumentNotValid(MethodArgumentNotValidException ex, Model model){
		String userFriendlyError = ex.getBindingResult()
																										.getAllErrors()
																										.stream()
																										.map(error -> error.getDefaultMessage())
																										.findFirst()
																										.orElse("An unknown error occurred");
		model.addAttribute("errorMessage", userFriendlyError);
		return "error/errorPage";
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String noSuchElementAdvice(Model model, Exception ex){
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/404";
	}

	//Bad Request
	@ExceptionHandler({IllegalArgumentException.class, HttpRequestMethodNotSupportedException.class , NoImagesUploadedException.class, MaxUploadSizeExceededException.class})
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
