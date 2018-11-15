package org.shalim.heybeach.services.validators;

import java.util.Map;

import org.shalim.heybeach.util.ReturnCode;

public interface IRequestValidator {
	ReturnCode validateRequest(Map<String, String> params);
}
