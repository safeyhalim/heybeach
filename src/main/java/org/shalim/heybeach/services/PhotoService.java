package org.shalim.heybeach.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.shalim.heybeach.domain.requests.PhotoUploadRequest;
import org.shalim.heybeach.model.Photo;
import org.shalim.heybeach.model.User;
import org.shalim.heybeach.repositories.PhotoRepository;
import org.shalim.heybeach.util.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;

	private Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);

	public ReturnCode uploadPhotos(PhotoUploadRequest photoUploadRequest, User user) {
		for (MultipartFile photoFile : photoUploadRequest.getPhotoFiles()) {
			// TODO: Save multiple files in one database trip
			Photo photo = new Photo();
			try {
				photo.setFile(new SerialBlob(photoFile.getBytes()));
			} catch (SQLException | IOException e) {
				LOGGER.error("Fatal error: unable to convert a valid file to blob. Exception thrown {}", e);
				return ReturnCode.UNABLE_TO_UPLOAD_FILE;
			}
			photo.setOwner(user);
			photo.setName(UUID.randomUUID().toString()); // TODO: UUID should be function in the file name
			photo.setType(photoFile.getContentType());

			photoRepository.save(photo);
		}
		return ReturnCode.SUCCESS;
	}

	public Set<Photo> getUserPhotos(User user, List<ReturnCode> errors) {
		try {
			return photoRepository.findByOwner(user);
		} catch (Exception e) {
			handlePhotoRetrievalException(user, e, errors);
			return null;
		}
	}

	public Photo getUserPhoto(Long photoId, User user, List<ReturnCode> errors) {
		try {
			Photo photo = photoRepository.findByIdAndOwner(photoId, user);
			if (photo == null) {
				errors.add(ReturnCode.PHOTO_NOT_FOUND);
			}
			return photo;
		} catch (Exception e) {
			handlePhotoRetrievalException(user, e, errors);
			return null;
		}
	}

	private void handlePhotoRetrievalException(User user, Exception e, List<ReturnCode> errors) {
		LOGGER.error("Fatal error: Problem occurred while trying to retrieve photos for user {}. Exception thrown {}",
				user.getEmail(), e);
		errors.add(ReturnCode.UNABLE_TO_RETRIEVE_PHOTOS);
	}
}
