package com.finance.backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
		
		ErrorResponse error = new ErrorResponse(
				ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(),
				LocalDateTime.now()
		);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
