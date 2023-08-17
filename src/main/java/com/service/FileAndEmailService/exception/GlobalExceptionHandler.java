package com.service.FileAndEmailService.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.FileAndEmailService.util.ApiResponse;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, List<String>> fieldErrors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
		}
		return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOExceptionExceptions(IOException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<?> handleMessagingException(MessagingException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(TemplateException.class)
	public ResponseEntity<?> handleTemplateException(TemplateException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<?> emailExcetion(EmailException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TemplateNotFound.class)
	public ResponseEntity<?> gradeIdNotPresent(TemplateNotFound ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileManagerException.class)
	public ResponseEntity<?> gradeIdNotPresent(FileManagerException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, LocalDateTime.now());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

}