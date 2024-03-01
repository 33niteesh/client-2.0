package com.client.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	String message;
    public UserNotFoundException(String message) {
        super();
        this.message=message;
    }
    public String getMessage() {
		return message;
	}
    
}