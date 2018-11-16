package org.shalim.heybeach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heybeach/home/")
public class PhotosController {

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	@ResponseBody
	public String loadPhotos() {
		return "Hello";
	}
}
