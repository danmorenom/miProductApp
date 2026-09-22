package com.mycompany.myproductapp.application.domain.exceptions;


public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String errorDescription) {
        super(errorDescription);
    }

}