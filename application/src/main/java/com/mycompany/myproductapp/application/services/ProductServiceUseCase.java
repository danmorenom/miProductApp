package com.mycompany.myproductapp.application.services;

import com.mycompany.myproductapp.application.domain.model.Product;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceUseCase implements com.mycompany.myproductapp.application.ports.driving.ProductPort {

    private final com.mycompany.myproductapp.application.ports.driven.ProductPort productPort;

    @Override
    public Set<Product> getSimilarProducts(String productId) {
        log.debug("Calling getSimilarProducts use case method for product id {}", productId);
        return productPort.getSimilarProducts(productId);
    }

}
