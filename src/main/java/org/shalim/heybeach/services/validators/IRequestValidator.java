package org.shalim.heybeach.services.validators;

import org.shalim.heybeach.domain.requests.IRequest;
import org.springframework.validation.Errors;

public interface IRequestValidator {
	IRequest validateRequest(String requestJson, Errors errors);
}
