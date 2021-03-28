package com.cg.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.exceptions.ContactException;
import com.cg.exceptions.ErrorInfo;
import com.cg.exceptions.UserException;

@RestControllerAdvice
public class AddressAdviceController {
	
	Logger logger = LoggerFactory.getLogger(AddressAdviceController.class);
	
	@ExceptionHandler({UserException.class,ContactException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorInfo handleException(Exception ex) {
		logger.error(ex.getMessage());
		return new ErrorInfo(ex.getMessage());
	}
}
