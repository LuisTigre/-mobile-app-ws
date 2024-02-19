package com.apps.developerblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = -7305027319723179739L;
	
	public UserServiceException(String message) {
		
		super(message);
	}

}
