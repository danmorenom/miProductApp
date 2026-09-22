package com.mycompany.myproductapp.application.services;

import com.mycompany.myproductapp.application.domain.model.Product;
import com.mycompany.myproductapp.application.domain.exceptions.ProductServiceException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUseCaseTests {

    @Mock
    private com.mycompany.myproductapp.application.ports.driven.ProductPort productPort;

    @InjectMocks
    private ProductServiceUseCase productServiceUseCase;

    @Test
    void getSimilarProductsShouldReturnProductsFromDrivenPort() {
        // Given
        String productId = "1";

        Product product1 = Instancio.create(Product.class);
        Product product2 = Instancio.create(Product.class);

        Set<Product> expectedProducts = Set.of(product1, product2);

        when(productPort.getSimilarProducts(productId))
                .thenReturn(expectedProducts);

        // When
        Set<Product> result =
                productServiceUseCase.getSimilarProducts(productId);

        // Then
        assertSame(expectedProducts, result);

        verify(productPort, times(1))
                .getSimilarProducts(productId);

        verifyNoMoreInteractions(productPort);
    }

    @Test
    void getSimilarProductsShouldReturnEmptySetWhenDrivenPortReturnsEmptySet() {
        // Given
        String productId = "1";

        when(productPort.getSimilarProducts(productId))
                .thenReturn(Set.of());

        // When
        Set<Product> result =
                productServiceUseCase.getSimilarProducts(productId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productPort).getSimilarProducts(productId);
        verifyNoMoreInteractions(productPort);
    }

    @Test
    void getSimilarProductsShouldPropagateException() {
        // Given
        String productId = "1";

        ProductServiceException expectedException =
                new ProductServiceException("Error calling external service");

        when(productPort.getSimilarProducts(productId))
                .thenThrow(expectedException);

        // When / Then
        ProductServiceException thrown = assertThrows(
                ProductServiceException.class,
                () -> productServiceUseCase.getSimilarProducts(productId));

        assertSame(expectedException, thrown);

        verify(productPort).getSimilarProducts(productId);
        verifyNoMoreInteractions(productPort);
    }

    @Test
    void getSimilarProductsShouldCallDrivenPortWithNullProductId() {
        // Given
        when(productPort.getSimilarProducts(null))
                .thenReturn(Set.of());

        // When
        Set<Product> result =
                productServiceUseCase.getSimilarProducts(null);

        // Then
        assertNotNull(result);

        verify(productPort).getSimilarProducts(null);
        verifyNoMoreInteractions(productPort);
    }

}