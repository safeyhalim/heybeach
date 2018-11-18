package org.shalim.heybeach.domain.requests;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PhotoUploadRequest implements IRequest {
	private List<MultipartFile> photoFiles = new ArrayList<MultipartFile>();

	public List<MultipartFile> getPhotoFiles() {
		return photoFiles;
	}

	public void addPhoto(MultipartFile photoFile) {
		photoFiles.add(photoFile);
	}
}
