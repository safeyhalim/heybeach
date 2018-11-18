package org.shalim.heybeach.services.validators;

import java.util.List;

import org.shalim.heybeach.domain.requests.IRequest;
import org.shalim.heybeach.util.ReturnCode;

public interface IRequestValidator<T> {
	public IRequest validateRequest(T request, List<ReturnCode> errors);
}
