package com.mycompany.myproductapp.api.rest.handlers;

import com.mycompany.myproductapp.application.domain.exceptions.ProductNotFoundException;
import com.mycompany.myproductapp.application.domain.exceptions.ProductServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestExceptionHandlerTests {

    private final RestExceptionHandler restExceptionHandler =
            new RestExceptionHandler();

    @Test
    void handleProductNotFoundShouldReturnNotFoundStatusAndMessage() {
        // Given
        String errorMessage = "Product not found";

        ProductNotFoundException exception =
                new ProductNotFoundException(errorMessage);

        // When
        ResponseEntity<String> response =
                restExceptionHandler.handleProductNotFound(exception);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertEquals(errorMessage, response.getBody())
        );
    }

    @Test
    void handleProductNotFoundShouldReturnNullBodyWhenExceptionMessageIsNull() {
        // Given
        ProductNotFoundException exception =
                new ProductNotFoundException(null);

        // When
        ResponseEntity<String> response =
                restExceptionHandler.handleProductNotFound(exception);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void handleProductServiceExceptionShouldReturnInternalServerErrorAndMessage() {
        // Given
        String errorMessage = "Error calling external service";

        ProductServiceException exception =
                new ProductServiceException(errorMessage);

        // When
        ResponseEntity<String> response =
                restExceptionHandler.handleProductServiceException(exception);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertEquals(errorMessage, response.getBody())
        );
    }

    @Test
    void handleProductServiceExceptionShouldReturnNullBodyWhenExceptionMessageIsNull() {
        // Given
        ProductServiceException exception =
                new ProductServiceException(null);

        // When
        ResponseEntity<String> response =
                restExceptionHandler.handleProductServiceException(exception);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

}