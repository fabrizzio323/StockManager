package com.stockmanager.serviceImplementation;

import com.stockmanager.dto.ProductDTO;
import com.stockmanager.exception.CustomException;
import com.stockmanager.mapper.ProductMapper;
import com.stockmanager.model.Product;
import com.stockmanager.repository.ProductoRepository;
import com.stockmanager.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductoService {
    @Autowired
    private ProductoRepository repository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> listAllProducts() throws CustomException {
        try{
            List<Product> products = repository.findByStatus(true);
            if(!products.isEmpty()){
                return productMapper.toProductDTOs(products);
            }else{
                throw new CustomException("No active products found.");
            }
        }catch (Exception e){
            throw new CustomException("Error while listing productos: " + e.getMessage());
        }
    }

    @Override
    public ProductDTO getProductById(Long idProduct) throws CustomException {
        try{
            Product product = repository.findByIdAndStatus(idProduct,true).orElseThrow(()-> new CustomException("Product not found with ID: " + idProduct));
            return productMapper.toProductDTO(product);
        }catch (Exception e) {
            throw new CustomException("Error while fetching product by ID: " + idProduct + " - " + e.getMessage());
        }
    }

    @Override
    public void createProduct(ProductDTO productDTO) throws CustomException {
        try{
            Product newProduct = productMapper.toProduct(productDTO);
            if(!nameExists(newProduct.getName())){
                 newProduct.setStatus(true);
                repository.save(newProduct);
            }else{
                throw new CustomException("Product with name " + newProduct.getName() + " already exists.");
            }

        }catch (Exception e){
            throw new CustomException("Error while creating product: " + e.getMessage());
        }

    }

    @Override
    public void deleteProduct(Long idProduct) throws CustomException {
       try{
            Product product = repository.findById(idProduct).orElseThrow(() -> new CustomException("Product not found with ID: " + idProduct));
            if(!product.isStatus()){
                throw new CustomException("Product with ID: " + idProduct + " is already inactive.");
            } else {
                product.setStatus(false);
                repository.save(product);
            }
       }catch (Exception e){
              throw new CustomException("Error while deleting product with ID: " + idProduct + " - " + e.getMessage());
       }
    }

    @Override
    public void updateProduct(Long idProduct, ProductDTO productUpdatedDTO) throws CustomException {
        Product product = repository.findByIdAndStatus(idProduct, true)
                .orElseThrow(() -> new CustomException("Product not found with ID: " + idProduct));

        if (productUpdatedDTO.getName() != null) {
            product.setName(productUpdatedDTO.getName());
        }
        if(productUpdatedDTO.getDescription() != null){
            product.setDescription(productUpdatedDTO.getDescription());
        }
        if (productUpdatedDTO.getPrice()>=0) {
            product.setPrice(productUpdatedDTO.getPrice());
        }else{
            throw new CustomException("Price cannot be negative.");
        }
        if (productUpdatedDTO.getStock() >= 0 && productUpdatedDTO.getStock() != product.getStock()) {
            product.setStock(productUpdatedDTO.getStock());
        }
        repository.save(product);
    }

    @Override
    public boolean nameExists(String name) {
        List<Product> products = repository.findByStatus(true);
        Optional<Product> product = products.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst();
        return product.isPresent();
    }
}
