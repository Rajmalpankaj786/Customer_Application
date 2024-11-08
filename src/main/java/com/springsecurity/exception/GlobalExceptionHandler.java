package com.springsecurity.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandler1(Exception ex, WebRequest wr){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimeStamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> exceptionHandler2(NoHandlerFoundException ex, WebRequest wr){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimeStamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorDetails> exceptionHandler3(CustomerException ex, WebRequest wr){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimeStamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
}
