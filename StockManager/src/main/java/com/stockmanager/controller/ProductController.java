package com.stockmanager.controller;

import com.stockmanager.dto.ProductDTO;
import com.stockmanager.exception.CustomException;
import com.stockmanager.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductoService service;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts(){
        try{
            List<ProductDTO> products = service.listAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = service.getProductById(id);
            return new ResponseEntity<>(product,HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProductDTO) {
        try {
            service.createProduct(newProductDTO);
            return new ResponseEntity<>("Product created successfully",HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            service.deleteProduct(id);
            return new ResponseEntity<>("product deleted successfully", HttpStatus.NO_CONTENT);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productUpdatedDTO) {
        try {
            service.updateProduct(id, productUpdatedDTO);
            return new ResponseEntity<>("product updates successfully", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
