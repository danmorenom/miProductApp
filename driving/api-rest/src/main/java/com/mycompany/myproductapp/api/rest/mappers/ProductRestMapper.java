package com.mycompany.myproductapp.api.rest.mappers;

import com.mycompany.myproductapp.application.domain.model.Product;
import com.mycompany.myproductapp.api.rest.generated.model.ProductDetail;

import org.mapstruct.Mapper;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    Set<ProductDetail> mapFromDomain(Set<Product> productsSet);
}
