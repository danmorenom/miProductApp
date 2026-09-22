package com.mycompany.myproductapp.application.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProductNotFoundExceptionTests {

    @Test
    void shouldCreateExceptionWithProvidedMessage() {
        String errorDescription = "Product not found";

        ProductNotFoundException exception =
                new ProductNotFoundException(errorDescription);

        assertEquals(errorDescription, exception.getMessage());
    }

    @Test
    void shouldExtendRuntimeException() {
        ProductNotFoundException exception =
                new ProductNotFoundException("error");

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void shouldAllowNullMessage() {
        ProductNotFoundException exception =
                new ProductNotFoundException(null);

        assertNull(exception.getMessage());
    }

}