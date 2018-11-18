package org.shalim.heybeach.util;

public enum ReturnCode {
	SUCCESS("0", "SUCCESS"), USER_ALREADY_EXISTS("1", "User already exists"),
	INVALID_REQUEST_NO_PARAMS("2", "Invalid Request: no parameters"),
	INVALID_REQUEST_MISSING_EMAIL("3", "Invalid Request: missing email"),
	INVALID_EMAIL_FORMAT("4", "Invalid Request: invalid email format"),
	MISSING_PASSWORD("5", "Invalid Request: missing password"),
	MISSING_REPEATED_PASSWORD("6", "Invalid Request: missing repeated password"),
	PASSWORD_MISSMATCH("7", "Invalid Request: password and repeated password are not identical"),
	INVALID_REQUEST("8", "Invalid Request"), INVALID_PHOTO_SIZE("9", "Invalid photo size"),
	UNABLE_TO_DETECT_FILE_TYPE("10", "Unable to detect file type"),
	UNKNOWN_FILE_TYPE_FOR_IMAGE("11", "Unknown file type for image"), USER_NOT_FOUND("12", "User not found"),
	UNABLE_TO_UPLOAD_FILE("13", "Unable to upload photo file"),
	UNABLE_TO_RETRIEVE_PHOTOS("14", "Unable to retrieve user photos"), PHOTO_NOT_FOUND("15", "Photo not found"),
	INTERNAL_ERROR("16", "Internal error");

	private String code;
	private String message;

	private ReturnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
