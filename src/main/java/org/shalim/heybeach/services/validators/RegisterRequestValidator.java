package org.shalim.heybeach.services.validators;

import java.io.IOException;
import java.util.regex.Pattern;

import org.shalim.heybeach.domain.requests.IRequest;
import org.shalim.heybeach.domain.requests.RegisterRequest;
import org.shalim.heybeach.util.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RegisterRequestValidator implements IRequestValidator {
	private Logger LOGGER = LoggerFactory.getLogger(RegisterRequestValidator.class);
	private static Pattern emailPattern = Pattern
			.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public IRequest validateRequest(String requestJson, Errors errors) {
		if (StringUtils.isEmpty(requestJson)) {
			addError(ReturnCode.INVALID_REQUEST_NO_PARAMS, errors);
			return null;
		}

		RegisterRequest registerRequest = parseRequest(requestJson);
		if (registerRequest == null) {
			addError(ReturnCode.INVALID_REQUEST, errors);
			return null;
		}

		ReturnCode emailValidationCode = validateEmail(registerRequest.getEmail());
		if (emailValidationCode != ReturnCode.SUCCESS) {
			addError(emailValidationCode, errors);
		}

		ReturnCode passwordValidationCode = validatePassword(registerRequest.getPassword(),
				registerRequest.getRepeatedPassword());
		if (passwordValidationCode != ReturnCode.SUCCESS) {
			addError(passwordValidationCode, errors);
		}

		return registerRequest;
	}

	private void addError(ReturnCode returnCode, Errors errors) {
		errors.reject(returnCode.getCode(), returnCode.getMessage());
	}

	private RegisterRequest parseRequest(String requestJson) {
		try {
			return mapper.readValue(requestJson, RegisterRequest.class);
		} catch (IOException e) {
			LOGGER.error("Unable to parse Register request. Exception: {}", e);
			return null;
		}
	}

	private ReturnCode validateEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return ReturnCode.INVALID_REQUEST_MISSING_EMAIL;
		}
		if (emailPattern.matcher(email).matches()) {
			return ReturnCode.SUCCESS;
		}
		return ReturnCode.INVALID_EMAIL_FORMAT;
	}

	private ReturnCode validatePassword(String password, String repeatedPassword) {
		if (StringUtils.isEmpty(password)) {
			return ReturnCode.MISSING_PASSWORD;
		}
		if (StringUtils.isEmpty(repeatedPassword)) {
			return ReturnCode.MISSING_REPEATED_PASSWORD;
		}
		if (!password.equals(repeatedPassword)) {
			return ReturnCode.PASSWORD_MISSMATCH;
		}

		return ReturnCode.SUCCESS;
	}

}
