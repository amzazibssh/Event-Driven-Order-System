package com.example.Event_Driven_Order_System.service;

import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(
            ProductRepository productRepository
    ){
        this.productRepository = productRepository;
    }

    public void addProduct(Products product){
        productRepository.save(product);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
}
