package com.mycompany.myproductapp.api.client.adapters;

import com.mycompany.myproductapp.api.client.generated.api.DefaultApi;
import com.mycompany.myproductapp.api.client.generated.model.ProductDetail;
import com.mycompany.myproductapp.api.client.mappers.ProductApiClientMapper;
import com.mycompany.myproductapp.application.domain.exceptions.ProductNotFoundException;
import com.mycompany.myproductapp.application.domain.exceptions.ProductServiceException;
import com.mycompany.myproductapp.application.domain.model.Product;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApiClientAdapterTests {

    @Mock
    private DefaultApi defaultApi;

    @Mock
    private ProductApiClientMapper productApiClientMapper;

    @InjectMocks
    private ProductApiClientAdapter adapter;

    @Test
    void shouldReturnEmptySetWhenSimilarProductIdsIsNull() {
        // Given
        String productId = "1";

        when(defaultApi.getProductSimilarids(productId))
                .thenReturn(null);

        // When
        Set<Product> result = adapter.getSimilarProducts(productId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(defaultApi).getProductSimilarids(productId);
        verifyNoMoreInteractions(defaultApi);
        verifyNoInteractions(productApiClientMapper);
    }

    @Test
    void shouldReturnMappedProducts() {
        // Given
        String requestedProductId = "1";

        Set<String> similarIds = new LinkedHashSet<>();
        similarIds.add("2");
        similarIds.add("3");

        ProductDetail productDetail1 = Instancio.create(ProductDetail.class);
        ProductDetail productDetail2 = Instancio.create(ProductDetail.class);

        Product product1 = Instancio.create(Product.class);
        Product product2 = Instancio.create(Product.class);

        when(defaultApi.getProductSimilarids(requestedProductId))
                .thenReturn(similarIds);

        when(defaultApi.getProductProductId("2"))
                .thenReturn(productDetail1);

        when(defaultApi.getProductProductId("3"))
                .thenReturn(productDetail2);

        when(productApiClientMapper.mapToDomain(productDetail1))
                .thenReturn(product1);

        when(productApiClientMapper.mapToDomain(productDetail2))
                .thenReturn(product2);

        // When
        Set<Product> result =
                adapter.getSimilarProducts(requestedProductId);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));

        verify(defaultApi).getProductSimilarids(requestedProductId);
        verify(defaultApi).getProductProductId("2");
        verify(defaultApi).getProductProductId("3");

        verify(productApiClientMapper).mapToDomain(productDetail1);
        verify(productApiClientMapper).mapToDomain(productDetail2);

        verifyNoMoreInteractions(defaultApi);
        verifyNoMoreInteractions(productApiClientMapper);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenGetSimilarIdsReturnsNotFound() {
        // Given
        String productId = "1";

        HttpClientErrorException.NotFound exception =
                mock(HttpClientErrorException.NotFound.class);

        when(defaultApi.getProductSimilarids(productId))
                .thenThrow(exception);

        // When
        ProductNotFoundException thrown =
                assertThrows(
                        ProductNotFoundException.class,
                        () -> adapter.getSimilarProducts(productId));

        // Then
        assertEquals(
                String.format(
                        ProductApiClientAdapter.SIMILAR_PRODUCT_EXCEPTION,
                        productId,
                        ProductApiClientAdapter.PRODUCT_NOT_FOUND),
                thrown.getMessage());

        verify(defaultApi).getProductSimilarids(productId);
        verifyNoMoreInteractions(defaultApi);
        verifyNoInteractions(productApiClientMapper);
    }

    @Test
    void shouldThrowProductServiceExceptionWhenGetSimilarIdsFails() {
        // Given
        String productId = "1";

        RuntimeException exception =
                new RuntimeException("Unexpected error");

        when(defaultApi.getProductSimilarids(productId))
                .thenThrow(exception);

        // When
        ProductServiceException thrown =
                assertThrows(
                        ProductServiceException.class,
                        () -> adapter.getSimilarProducts(productId));

        // Then
        assertEquals(
                String.format(
                        ProductApiClientAdapter.SIMILAR_PRODUCT_EXCEPTION,
                        productId,
                        "Unexpected error"),
                thrown.getMessage());

        verify(defaultApi).getProductSimilarids(productId);
        verifyNoMoreInteractions(defaultApi);
        verifyNoInteractions(productApiClientMapper);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductDetailReturnsNotFound() {
        // Given
        String requestedProductId = "1";

        Set<String> similarIds = Set.of("2");

        HttpClientErrorException.NotFound exception =
                mock(HttpClientErrorException.NotFound.class);

        when(defaultApi.getProductSimilarids(requestedProductId))
                .thenReturn(similarIds);

        when(defaultApi.getProductProductId("2"))
                .thenThrow(exception);

        // When
        ProductNotFoundException thrown =
                assertThrows(
                        ProductNotFoundException.class,
                        () -> adapter.getSimilarProducts(requestedProductId));

        // Then
        assertEquals(
                String.format(
                        ProductApiClientAdapter.PRODUCT_DETAIL_EXCEPTION,
                        "2",
                        ProductApiClientAdapter.PRODUCT_NOT_FOUND),
                thrown.getMessage());

        verify(defaultApi).getProductSimilarids(requestedProductId);
        verify(defaultApi).getProductProductId("2");

        verifyNoMoreInteractions(defaultApi);
        verifyNoInteractions(productApiClientMapper);
    }

    @Test
    void shouldThrowProductServiceExceptionWhenProductDetailFails() {
        // Given
        String requestedProductId = "1";

        Set<String> similarIds = Set.of("2");

        when(defaultApi.getProductSimilarids(requestedProductId))
                .thenReturn(similarIds);

        when(defaultApi.getProductProductId("2"))
                .thenThrow(new RuntimeException("Detail error"));

        // When
        ProductServiceException thrown =
                assertThrows(
                        ProductServiceException.class,
                        () -> adapter.getSimilarProducts(requestedProductId));

        // Then
        assertEquals(
                String.format(
                        ProductApiClientAdapter.PRODUCT_DETAIL_EXCEPTION,
                        "2",
                        "Detail error"),
                thrown.getMessage());

        verify(defaultApi).getProductSimilarids(requestedProductId);
        verify(defaultApi).getProductProductId("2");

        verifyNoMoreInteractions(defaultApi);
        verifyNoInteractions(productApiClientMapper);
    }

}