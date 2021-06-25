package com.demo.shopping.cart.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND )
public class BuisnessException extends RuntimeException { 
    public BuisnessException(String errorMessage) {
        super(errorMessage);
    }
}