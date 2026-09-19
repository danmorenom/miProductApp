package com.mycompany.myproductapp.api.rest.adapters;

import com.mycompany.myproductapp.api.rest.generated.api.DefaultApi;
import com.mycompany.myproductapp.api.rest.generated.model.ProductDetail;
import com.mycompany.myproductapp.api.rest.mappers.ProductRestMapper;
import com.mycompany.myproductapp.application.ports.driving.ProductPort;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequiredArgsConstructor
public class ProductRestControllerAdapter implements DefaultApi {

    private final ProductPort productPort;
    private final ProductRestMapper productRestMapper;

    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(@NonNull String productId) {
        var similarIds = productPort.getSimilarProducts(productId);
        return ResponseEntity.ok( productRestMapper.mapFromDomain(similarIds));
    }
}