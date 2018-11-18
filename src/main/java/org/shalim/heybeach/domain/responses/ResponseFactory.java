package org.shalim.heybeach.domain.responses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.shalim.heybeach.model.Photo;
import org.shalim.heybeach.util.ErrorUtil;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
	public static ResponseEntity<?> createResponse(List<ReturnCode> errors) {
		if (!errors.isEmpty()) {
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

	public static ResponseEntity<?> createErrorResponse(List<ReturnCode> errors) {
		return new ResponseEntity<>(ErrorUtil.createErrorMessage(errors), HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<?> createListPhotosResponse(Set<Photo> photos, List<ReturnCode> errors) {
		if (photos == null || !errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		List<PhotoDto> photoDtos = new ArrayList<PhotoDto>();
		for (Photo photo : photos) {
			photoDtos.add(new PhotoDto(photo));
		}
		return new ResponseEntity<>(photoDtos, HttpStatus.OK);
	}

	public static ResponseEntity<?> createPhotoDownloadResponse(Photo photo, List<ReturnCode> errors) {
		if (photo == null || !errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(photo.getType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
					.body(new InputStreamResource(photo.getFile().getBinaryStream()));
		} catch (SQLException e) {
			errors.add(ReturnCode.INTERNAL_ERROR);
			return ResponseFactory.createErrorResponse(errors);
		}
	}
}
