package com.example.Event_Driven_Order_System.service;

import com.example.Event_Driven_Order_System.dto.dtos.ProductDTO;
import com.example.Event_Driven_Order_System.dto.mappers.ProductMapper;
import com.example.Event_Driven_Order_System.dto.request.AddProductRequest;
import com.example.Event_Driven_Order_System.dto.request.UpdateProductRequest;
import com.example.Event_Driven_Order_System.dto.response.AddProductResponse;
import com.example.Event_Driven_Order_System.dto.response.UpdateProductResponse;
import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.exception.ProductNotFoundException;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(
            ProductRepository productRepository
    ){
        this.productRepository = productRepository;
    }

    public AddProductResponse addProduct(AddProductRequest productReq){
        Products product = new Products();

        product.setName(productReq.name());
        product.setPrice(productReq.price());
        product.setDiscount(productReq.discount());
        product.setQuantity(product.getQuantity());

        productRepository.save(product);
        return new AddProductResponse(productReq.name(), productReq.price());
    }

    public List<ProductDTO> getAllProducts() {
        List<Products> products = productRepository.findAll();
        List<ProductDTO> productDTOS = ProductMapper.toDTOList(products);
        return productDTOS;
    }

    @Retryable(
            includes = ObjectOptimisticLockingFailureException.class,
            maxRetries = 5,
            delay = 200
    )
    @Transactional
    public UpdateProductResponse updateProduct(UUID id, UpdateProductRequest request) {
        log.info("Attempting update on product {}", id);
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        if (request.quantity() != null) {
            product.setQuantity(request.quantity());
        }
        if (request.discount() != null) {
            product.setDiscount(request.discount());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }

        return new UpdateProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDiscount(), product.getQuantity());
    }
}
