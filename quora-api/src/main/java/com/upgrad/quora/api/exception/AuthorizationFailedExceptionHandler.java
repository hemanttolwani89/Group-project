package com.upgrad.quora.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.upgrad.quora.api.Response;
import com.upgrad.quora.service.exception.AuthorizationFailedException;

@ControllerAdvice
@RestController
public class AuthorizationFailedExceptionHandler {
	
	@ExceptionHandler(AuthorizationFailedException.class)
	  public final ResponseEntity<Response> handleUserNotFoundException(AuthorizationFailedException ex, WebRequest request) {
	    Response response = new Response(ex.getCode(),ex.getErrorMessage());
	    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

}
