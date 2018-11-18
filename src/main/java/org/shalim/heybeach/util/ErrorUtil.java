package org.shalim.heybeach.util;

import java.util.List;

public class ErrorUtil {

	public static String createErrorMessage(List<ReturnCode> errors) {
		StringBuilder strBuilder = new StringBuilder("Failed with the following errors:\n");
		for (ReturnCode error : errors) {
			strBuilder.append(error.getCode()).append(": ").append(error.getMessage()).append("\n");
		}

		return strBuilder.toString();
	}
}
