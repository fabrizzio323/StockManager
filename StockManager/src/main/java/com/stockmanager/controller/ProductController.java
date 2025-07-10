package com.stockmanager.controller;

import com.stockmanager.dto.ProductDTO;
import com.stockmanager.exception.CustomException;
import com.stockmanager.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
