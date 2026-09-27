package com.example.Event_Driven_Order_System.dto.request;

import com.example.Event_Driven_Order_System.entity.OrderState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record AddOrderRequest(
        @NotEmpty
        Map<UUID, Integer> productQuantity) {
}
