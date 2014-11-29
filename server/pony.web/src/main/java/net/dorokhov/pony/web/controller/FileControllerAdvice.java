package net.dorokhov.pony.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = FileController.class)
@ResponseBody
public class FileControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleUnexpectedError(Exception aError) {

		log.error("unexpected error occurred", aError);

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
