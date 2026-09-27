package com.example.Event_Driven_Order_System.dto.mappers;

import com.example.Event_Driven_Order_System.dto.dtos.OrderDTO;
import com.example.Event_Driven_Order_System.dto.dtos.ProductDTO;
import com.example.Event_Driven_Order_System.entity.Orders;
import com.example.Event_Driven_Order_System.entity.Products;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Products product) {
        return new ProductDTO(
                product.getName(),
                product.getPrice(),
                product.getDiscount(),
                product.getQuantity()
        );
    }

    public static Products toEntity(ProductDTO dto) {
        Products product = new Products();

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDiscount(dto.discount());
        product.setQuantity(dto.quantity());

        return product;
    }

    public static List<ProductDTO> toDTOList(List<Products> orders) {
        return orders.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}