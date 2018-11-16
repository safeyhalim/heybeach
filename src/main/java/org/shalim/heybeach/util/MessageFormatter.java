package org.shalim.heybeach.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class MessageFormatter {

	public static String createErrorMessage(Errors errors) {
		StringBuilder strBuilder = new StringBuilder("Failed with the following errors:\n");
		for (ObjectError error : errors.getAllErrors()) {
			strBuilder.append(error.getCode()).append(": ").append(error.getDefaultMessage()).append("\n");
		}

		return strBuilder.toString();
	}
}
