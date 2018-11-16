package org.shalim.heybeach.domain.responses;

import org.shalim.heybeach.util.MessageFormatter;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class ResponseFactory {
	public static ResponseEntity<?> createResponse(Errors errors) {
		if (errors.hasErrors()) {
			createErrorResponse(errors);
		}
		return new ResponseEntity<>(ReturnCode.SUCCESS, HttpStatus.OK);
	}

	public static ResponseEntity<?> createResponse(ReturnCode returnCode) {
		if (returnCode == ReturnCode.SUCCESS) {
			return createSuccessResponse();
		}
		return new ResponseEntity<>(returnCode.getCode() + ": " + returnCode.getMessage(), HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<?> createSuccessResponse() {
		return new ResponseEntity<>(ReturnCode.SUCCESS, HttpStatus.OK);
	}

	public static ResponseEntity<?> createErrorResponse(Errors errors) {
		return new ResponseEntity<>(MessageFormatter.createErrorMessage(errors), HttpStatus.BAD_REQUEST);
	}
}
