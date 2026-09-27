package com.example.Event_Driven_Order_System.service;

import com.example.Event_Driven_Order_System.dto.dtos.ProductDTO;
import com.example.Event_Driven_Order_System.dto.mappers.ProductMapper;
import com.example.Event_Driven_Order_System.dto.request.AddProductRequest;
import com.example.Event_Driven_Order_System.dto.response.AddProductResponse;
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

    public AddProductResponse addProduct(AddProductRequest productReq){
        Products product = new Products(null, productReq.name(), productReq.price(), productReq.discount(), productReq.quantity());
        productRepository.save(product);
        return new AddProductResponse(productReq.name(), productReq.price());
    }

    public List<ProductDTO> getAllProducts() {
        List<Products> products = productRepository.findAll();
        List<ProductDTO> productDTOS = ProductMapper.toDTOList(products);
        return productDTOS;
    }
}
