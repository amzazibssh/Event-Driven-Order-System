package com.example.Event_Driven_Order_System.controller;

import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(
            ProductService productService
    ){
        this.productService = productService;
    }

    @GetMapping
    public List<Products> getAllProducts(){
        return productService.getAllProducts();
    }
    @PostMapping
    public String addProduct(@RequestBody Products product){
        productService.addProduct(product);
        return "Done";
    }
}
