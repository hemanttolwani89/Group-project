package com.upgrad.quora.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.upgrad.quora.api.Response;
import com.upgrad.quora.service.exception.*;

@ControllerAdvice
@RestController
public class AuthenticationFailedExceptionHandler {
	
	@ExceptionHandler(AuthenticationFailedException.class)
	  public final ResponseEntity<Response> handleUserNotFoundException(AuthenticationFailedException ex, WebRequest request) {
	    Response response = new Response(ex.getCode(),ex.getErrorMessage());
	    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
