package com.demo.shopping.cart.Exception;

public class TechnicalException extends RuntimeException { 
    public TechnicalException(String errorMessage) {
        super(errorMessage);
    }
}