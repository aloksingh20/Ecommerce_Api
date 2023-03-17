package com.alok91340.ecommerceapi.Exception;

import org.springframework.http.HttpStatus;

public class EcommerceApiException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public EcommerceApiException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public EcommerceApiException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
