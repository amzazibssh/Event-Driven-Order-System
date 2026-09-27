package com.example.Event_Driven_Order_System.dto.dtos;

public record ProductDTO (
        String name,
        double price,
        int discount,
        int quantity
){
}
