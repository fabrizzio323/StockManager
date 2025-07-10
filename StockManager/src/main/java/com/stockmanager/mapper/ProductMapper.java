package com.stockmanager.mapper;

import com.stockmanager.dto.ProductDTO;
import com.stockmanager.model.Product;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stock", target = "stock")
    ProductDTO toProductDTO(Product product);

    @InheritConfiguration
    Product toProduct(ProductDTO productDTO);
    List<ProductDTO> toProductDTOs(List<Product> products);
    List<Product> toProducts(List<ProductDTO> productDTOs);
}

