package com.stockmanager.service;

import com.stockmanager.dto.ProductDTO;
import com.stockmanager.exception.CustomException;

import java.util.List;

public interface ProductoService {
    public List<ProductDTO> listAllProducts() throws CustomException;
    public ProductDTO getProductById(Long idProduct) throws CustomException;
    public void createProduct(ProductDTO productDTO) throws CustomException;
    public void deleteProduct(Long idProduct) throws CustomException;
    public void updateProduct(Long idProduct, ProductDTO productUpdatedDTO) throws CustomException;
    public boolean nameExists(String name);
}
