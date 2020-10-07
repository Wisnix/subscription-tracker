package com.subscriptiontracker.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SubscriptionRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(SubscriptionNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				exception.getMessage(), System.currentTimeMillis());		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ForbiddenException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				exception.getMessage(), System.currentTimeMillis());		
		return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
	}
	
}
