package com.mycompany.myproductapp.api.client.adapters;

import com.mycompany.myproductapp.api.client.generated.api.DefaultApi;
import com.mycompany.myproductapp.api.client.mappers.ProductApiClientMapper;
import com.mycompany.myproductapp.application.domain.exceptions.ProductNotFoundException;
import com.mycompany.myproductapp.application.domain.exceptions.ProductServiceException;
import com.mycompany.myproductapp.application.domain.model.Product;
import com.mycompany.myproductapp.application.ports.driven.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class ProductApiClientAdapter implements ProductPort {
    public static final String SIMILAR_PRODUCT_EXCEPTION = "Error getting similar product ids for Product Id: %s - Error Description: %s";
    public static final String PRODUCT_DETAIL_EXCEPTION = "Error getting product detail for Product Id: %s - Error Description: %s";

    private final DefaultApi defaultApi;
    private final ProductApiClientMapper productApiClientMapper;

    @Override
    public Set<Product> getSimilarProducts(String productId) {
        Set<String> productIds;

        try {
            productIds = defaultApi.getProductSimilarids(productId);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ProductNotFoundException(String.format(SIMILAR_PRODUCT_EXCEPTION, productId, "N/A"));
        } catch (Exception ex) {
            throw new ProductServiceException(String.format(SIMILAR_PRODUCT_EXCEPTION, productId, ex.getMessage()));
        }

        Set<Product> productsSet;
        if (productIds == null) {
            productsSet = Collections.emptySet();
        } else {
            productsSet = new LinkedHashSet<>();
            productIds.forEach(id -> {
                try {
                    var productDetail = defaultApi.getProductProductId(id);
                    productsSet.add(productApiClientMapper.mapToDomain(productDetail));
                } catch (HttpClientErrorException.NotFound ex) {
                    throw new ProductNotFoundException(String.format(PRODUCT_DETAIL_EXCEPTION, id, "N/A"));
                } catch (Exception ex) {
                    throw new ProductServiceException(String.format(PRODUCT_DETAIL_EXCEPTION, id, ex.getMessage()));
                }
            });
        }

        return productsSet  ;
    }
}
