package com.fisa.ctm.exception;

public class CTMException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CTMException(String msg) {
		super(msg);
	}
}