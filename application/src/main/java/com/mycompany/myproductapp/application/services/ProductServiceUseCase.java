package com.mycompany.myproductapp.application.services;

import com.mycompany.myproductapp.application.domain.model.Product;

import lombok.RequiredArgsConstructor;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceUseCase implements com.mycompany.myproductapp.application.ports.driving.ProductPort {

    private final com.mycompany.myproductapp.application.ports.driven.ProductPort productPort;

    @Override
    public Set<Product> getSimilarProducts(String productId) {
        var similarProducts = productPort.getSimilarProducts(productId);
        // TODO - Validar que el API devuelve todos los campos obligatorios, y que los 2 Strings tienen longitud >= 1
        return similarProducts;
    }
}
