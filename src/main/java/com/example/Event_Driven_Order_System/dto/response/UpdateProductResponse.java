package com.example.Event_Driven_Order_System.dto.response;

import java.util.UUID;

public record UpdateProductResponse(
    UUID id,
    String name,
    double price,
    int discount,
    int quantity
) {}