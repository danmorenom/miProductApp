package com.mycompany.myproductapp.application.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProductServiceExceptionTests {

    @Test
    void shouldCreateExceptionWithProvidedMessage() {
        // Given
        String errorDescription = "Error calling Product Service";

        // When
        ProductServiceException exception =
                new ProductServiceException(errorDescription);

        // Then
        assertEquals(errorDescription, exception.getMessage());
    }

    @Test
    void shouldExtendRuntimeException() {
        // When
        ProductServiceException exception =
                new ProductServiceException("error");

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void shouldAllowNullMessage() {
        // When
        ProductServiceException exception =
                new ProductServiceException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    void shouldPreserveEmptyMessage() {
        // Given
        String errorDescription = "";

        // When
        ProductServiceException exception =
                new ProductServiceException(errorDescription);

        // Then
        assertEquals("", exception.getMessage());
    }

}