package com.example.Event_Driven_Order_System.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(
    @Positive Double price,
    @Min(0) @Max(100) Integer discount,
    @PositiveOrZero Integer quantity
) {}
