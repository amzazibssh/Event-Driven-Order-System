package com.example.Event_Driven_Order_System.dto.request;

import com.example.Event_Driven_Order_System.entity.Products;
import jakarta.validation.constraints.*;

public record AddProductRequest(
        @NotBlank
        @Size(max=20)
        String name,
        @PositiveOrZero
        double price,
        @Min(0)
        @Max(100)
        int discount,
        @PositiveOrZero
        int quantity
) {
}
