package com.mycompany.myproductapp.api.rest.adapters;

import com.mycompany.myproductapp.api.rest.generated.model.ProductDetail;
import com.mycompany.myproductapp.api.rest.mappers.ProductRestMapper;
import com.mycompany.myproductapp.application.domain.model.Product;
import com.mycompany.myproductapp.application.ports.driving.ProductPort;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerAdapterTests {

    @Mock
    private ProductPort productPort;

    @Mock
    private ProductRestMapper productRestMapper;

    @InjectMocks
    private ProductRestControllerAdapter adapter;

    @Test
    void shouldReturnOkResponseWithMappedProducts() {
        // Given
        String productId = "123";

        Set<Product> domainProducts = Set.of(
                Instancio.create(Product.class)
        );

        Set<ProductDetail> apiProducts = Set.of(
                Instancio.create(ProductDetail.class)
        );

        when(productPort.getSimilarProducts(productId))
                .thenReturn(domainProducts);

        when(productRestMapper.mapFromDomain(domainProducts))
                .thenReturn(apiProducts);

        // When
        ResponseEntity<Set<ProductDetail>> response =
                adapter.getProductSimilar(productId);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(apiProducts, response.getBody()),
                () -> assertNotNull(response.getBody())
        );

        verify(productPort).getSimilarProducts(productId);
        verify(productRestMapper).mapFromDomain(domainProducts);

        verifyNoMoreInteractions(productPort, productRestMapper);
    }

    @Test
    void shouldReturnOkResponseWithEmptySet() {
        // Given
        String productId = "456";

        Set<Product> domainProducts = Set.of();
        Set<ProductDetail> apiProducts = Set.of();

        when(productPort.getSimilarProducts(productId))
                .thenReturn(domainProducts);

        when(productRestMapper.mapFromDomain(domainProducts))
                .thenReturn(apiProducts);

        // When
        ResponseEntity<Set<ProductDetail>> response =
                adapter.getProductSimilar(productId);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert response.getBody() != null;
                    assertTrue(response.getBody().isEmpty());
                }
        );

        verify(productPort).getSimilarProducts(productId);
        verify(productRestMapper).mapFromDomain(domainProducts);

        verifyNoMoreInteractions(productPort, productRestMapper);
    }

}