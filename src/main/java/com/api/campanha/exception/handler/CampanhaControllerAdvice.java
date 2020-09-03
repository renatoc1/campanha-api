package com.api.campanha.exception.handler;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.campanha.exception.CampanhaException;
import com.api.campanha.exception.ExceptionResponse;

@RestControllerAdvice
public class CampanhaControllerAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CampanhaException.class)
	public final ResponseEntity<ExceptionResponse> campanhaException(CampanhaException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
	}
	
}
