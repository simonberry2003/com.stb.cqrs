package com.stb.cqrs;

/**
 * Wraps an {@link Exception} in a {@link RuntimeException} unless it is already a {@link RuntimeException}
 */
public class ExceptionWrapper {
	public static RuntimeException wrap(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException)e;
		}
		return new RuntimeException(e);
	}
}
