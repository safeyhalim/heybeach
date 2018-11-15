package org.shalim.heybeach.util;

public enum ReturnCode {
	SUCCESS(0, "SUCCESS"), USER_ALREADY_EXISTS(1, "User already exists"),
	INVALID_REQUEST_NO_PARAMS(2, "Invalid Request: no parameters"),
	INVALID_REQUEST_MISSING_EMAIL(3, "Invalid Request: missing email"),
	INVALID_EMAIL_FORMAT(4, "Invalid Request: invalid email format"),
	MISSING_PASSWORD(5, "Invalid Request: missing password"),
	MISSING_REPEATED_PASSWORD(6, "Invalid Request: missing repeated password"),
	PASSWORD_MISSMATCH(7, "Invalid Request: password and repeated password are not identical");

	private int code;
	private String message;

	private ReturnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
