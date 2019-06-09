package io.codelirium.telecom.kata.domain.dto.response.builder;

import io.codelirium.telecom.kata.domain.dto.response.RESTSuccessResponseBody;
import io.codelirium.telecom.kata.domain.dto.response.RESTFailureResponseBody;
import java.util.Collection;

import static io.codelirium.telecom.kata.domain.dto.response.builder.RESTResponseBodyBuilder.ResponseStatus.failure;
import static io.codelirium.telecom.kata.domain.dto.response.builder.RESTResponseBodyBuilder.ResponseStatus.success;
import static java.util.Objects.nonNull;


public final class RESTResponseBodyBuilder {

	public enum ResponseStatus { success, failure }


	private RESTResponseBodyBuilder() { }


	public static <T> RESTSuccessResponseBody<T> success(final String type, final Collection<T> data) {

		final RESTSuccessResponseBody<T> successBody = new RESTSuccessResponseBody<>();

		successBody.setStatus(success.name());
		successBody.setType(type);
		successBody.setCount(nonNull(data) ? data.size() : 0);
		successBody.setData(data);


		return successBody;
	}


	public static <T> RESTFailureResponseBody<T> failure(final String type, final T message) {

		final RESTFailureResponseBody<T> failureBody = new RESTFailureResponseBody<>();

		failureBody.setStatus(failure.name());
		failureBody.setType(type);
		failureBody.setMessage(message);


		return failureBody;
	}
}
