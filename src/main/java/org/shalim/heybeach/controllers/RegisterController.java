package org.shalim.heybeach.controllers;

import java.util.HashMap;
import java.util.Map;

import org.shalim.heybeach.controllers.requests.RegisterRequestParams;
import org.shalim.heybeach.services.validators.RegisterRequestValidator;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heybeach/welcome/")
public class RegisterController {

	@Autowired
	RegisterRequestValidator registerRequestValidator;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(@RequestBody String email, @RequestBody String password,
			@RequestBody String repeatedPassword) {

		ReturnCode validationResult = registerRequestValidator
				.validateRequest(createRegisterRequestParams(email, password, repeatedPassword));
		if (validationResult != ReturnCode.SUCCESS) {
			return validationResult.getMessage();
		}

		return ReturnCode.SUCCESS.getMessage();
	}

	private Map<String, String> createRegisterRequestParams(String email, String password, String repeatedPassword) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put(RegisterRequestParams.EMAIL.getParamName(), email);
				put(RegisterRequestParams.PASSWORD.getParamName(), password);
				put(RegisterRequestParams.REPEATED_PASSWORD.getParamName(), repeatedPassword);
			}
		};
	}
}
