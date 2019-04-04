package com.reinhold.fs.exceptions;

public class PluginLoadingException extends Exception {

	private static final long serialVersionUID = 3404024647320686501L;

	public PluginLoadingException() {
		super();
	}

	public PluginLoadingException(String message) {
		super(message);
	}

	public PluginLoadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginLoadingException(Throwable cause) {
		super(cause);
	}
}
