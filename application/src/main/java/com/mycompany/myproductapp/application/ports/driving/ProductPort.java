package com.mycompany.myproductapp.application.ports.driving;

import com.mycompany.myproductapp.application.domain.model.Product;

import java.util.Set;


public interface ProductPort {
    Set<Product> getSimilarProducts(String productId);
}
