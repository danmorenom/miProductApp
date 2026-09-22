package com.mycompany.myproductapp.api.client.mappers;

import com.mycompany.myproductapp.api.client.generated.model.ProductDetail;
import com.mycompany.myproductapp.application.domain.model.Product;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductApiClientMapper {

    Product mapToDomain(ProductDetail productDetail);

}
