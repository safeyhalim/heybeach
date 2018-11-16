package org.shalim.heybeach.controllers;

import org.shalim.heybeach.domain.requests.RegisterRequest;
import org.shalim.heybeach.domain.responses.ResponseFactory;
import org.shalim.heybeach.services.UserService;
import org.shalim.heybeach.services.validators.RegisterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heybeach/welcome/")
public class RegisterController {

	@Autowired
	RegisterRequestValidator registerRequestValidator;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerNewUser(@RequestBody String registerRequestJson, Errors errors) {
		RegisterRequest registerRequest = (RegisterRequest) registerRequestValidator
				.validateRequest(registerRequestJson, errors);
		if (errors.hasErrors()) {
			return ResponseFactory.createErrorResponse(errors);
		}

		return ResponseFactory.createResponse(userService.save(registerRequest));
	}
}
