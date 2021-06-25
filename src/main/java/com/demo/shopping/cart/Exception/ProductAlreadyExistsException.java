package com.demo.shopping.cart.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class ProductAlreadyExistsException extends RuntimeException { 
    public ProductAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}