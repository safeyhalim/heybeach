package org.shalim.heybeach.services.validators;

import java.util.List;

import org.shalim.heybeach.domain.requests.IRequest;
import org.shalim.heybeach.domain.requests.PhotoUploadRequest;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoRequestValidator implements IRequestValidator<List<MultipartFile>> {

	@Autowired
	List<String> imageTypes;

	@Override
	public IRequest validateRequest(List<MultipartFile> photoFiles, List<ReturnCode> errors) {
		PhotoUploadRequest photoUploadRequest = new PhotoUploadRequest();
		for (MultipartFile photoFile : photoFiles) {
			if (isPhotoFileValid(photoFile, errors)) {
				photoUploadRequest.addPhoto(photoFile);
			}
		}
		if (photoUploadRequest.getPhotoFiles().isEmpty()) {
			return null;
		}
		return photoUploadRequest;
	}

	private boolean isPhotoFileValid(MultipartFile photoFile, List<ReturnCode> errors) {
		if (photoFile == null || photoFile.isEmpty()) {
			errors.add(ReturnCode.INVALID_PHOTO_SIZE);
			return false;
		}

		ReturnCode contentValidationReturnCode = validateFileType(photoFile);
		if (contentValidationReturnCode != ReturnCode.SUCCESS) {
			errors.add(contentValidationReturnCode);
			return false;
		}

		return true;
	}

	private ReturnCode validateFileType(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType == null) {
			return ReturnCode.UNABLE_TO_DETECT_FILE_TYPE;
		}
		for (String imageType : imageTypes) {
			if (imageType.equals(contentType)) {
				return ReturnCode.SUCCESS;
			}
		}
		return ReturnCode.UNKNOWN_FILE_TYPE_FOR_IMAGE;
	}
}
