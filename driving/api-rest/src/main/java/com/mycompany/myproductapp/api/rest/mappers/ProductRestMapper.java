package com.mycompany.myproductapp.api.rest.mappers;

import com.mycompany.myproductapp.api.rest.generated.model.ProductDetail;
import com.mycompany.myproductapp.application.domain.model.Product;

import java.util.Set;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    Set<ProductDetail> mapFromDomain(Set<Product> productsSet);

}
