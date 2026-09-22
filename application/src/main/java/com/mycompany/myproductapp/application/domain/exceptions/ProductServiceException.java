package com.mycompany.myproductapp.application.domain.exceptions;


public class ProductServiceException extends RuntimeException {

    public ProductServiceException(String errorDescription) {
        super(errorDescription);
    }

}
