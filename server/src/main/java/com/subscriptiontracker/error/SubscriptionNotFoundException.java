package com.subscriptiontracker.error;

public class SubscriptionNotFoundException extends RuntimeException {

	public SubscriptionNotFoundException() {
	}

	public SubscriptionNotFoundException(String message) {
		super(message);
	}

	public SubscriptionNotFoundException(Throwable cause) {
		super(cause);
	}

	public SubscriptionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubscriptionNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
