package org.shalim.heybeach.services.validators;

import java.util.Map;
import java.util.regex.Pattern;

import org.shalim.heybeach.controllers.requests.RegisterRequestParams;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterRequestValidator implements IRequestValidator {
	private static Pattern emailPattern = Pattern
			.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

	@Override
	public ReturnCode validateRequest(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return ReturnCode.INVALID_REQUEST_NO_PARAMS;
		}
		String email = params.get(RegisterRequestParams.EMAIL.getParamName());
		String password = params.get(RegisterRequestParams.PASSWORD.getParamName());
		String repeatedPassword = params.get(RegisterRequestParams.REPEATED_PASSWORD.getParamName());

		ReturnCode emailValidationCode = validateEmail(email);
		if (emailValidationCode != ReturnCode.SUCCESS) {
			return emailValidationCode;
		}

		return validatePassword(password, repeatedPassword);
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
