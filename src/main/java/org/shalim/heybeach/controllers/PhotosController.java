package org.shalim.heybeach.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.shalim.heybeach.domain.requests.PhotoUploadRequest;
import org.shalim.heybeach.domain.responses.ResponseFactory;
import org.shalim.heybeach.model.User;
import org.shalim.heybeach.services.PhotoService;
import org.shalim.heybeach.services.UserService;
import org.shalim.heybeach.services.validators.PhotoRequestValidator;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/heybeach/home/")
public class PhotosController {

	@Autowired
	PhotoRequestValidator photoRequestValidator;

	@Autowired
	UserService userService;

	@Autowired
	PhotoService photoService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadOnePhoto(@RequestParam("photo") MultipartFile photo) {
		List<ReturnCode> errors = new ArrayList<ReturnCode>();
		PhotoUploadRequest photoUploadRequest = (PhotoUploadRequest) photoRequestValidator
				.validateRequest(Arrays.asList(photo), errors);
		if (!errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		User user = userService.getCurrentUser(errors);
		if (user == null || !errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		return ResponseFactory.createResponse(photoService.uploadPhotos(photoUploadRequest, user));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> listAllPhotos() {
		List<ReturnCode> errors = new ArrayList<ReturnCode>();

		User user = userService.getCurrentUser(errors);
		if (user == null || !errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		return ResponseFactory.createListPhotosResponse(photoService.getUserPhotos(user, errors), errors);
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> download(@RequestParam Long id) {
		List<ReturnCode> errors = new ArrayList<ReturnCode>();

		User user = userService.getCurrentUser(errors);
		if (user == null || !errors.isEmpty()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		return ResponseFactory.createPhotoDownloadResponse(photoService.getUserPhoto(id, user, errors), errors);
	}
}
