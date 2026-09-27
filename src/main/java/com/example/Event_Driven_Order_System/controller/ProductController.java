package com.example.Event_Driven_Order_System.controller;

import com.example.Event_Driven_Order_System.dto.dtos.ProductDTO;
import com.example.Event_Driven_Order_System.dto.request.AddProductRequest;
import com.example.Event_Driven_Order_System.dto.request.UpdateProductRequest;
import com.example.Event_Driven_Order_System.dto.response.AddProductResponse;
import com.example.Event_Driven_Order_System.dto.response.UpdateProductResponse;
import com.example.Event_Driven_Order_System.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<AddProductResponse> addProduct(@Valid @RequestBody AddProductRequest request){
        AddProductResponse response =  productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProductResponse> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request) {
        UpdateProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }
}
