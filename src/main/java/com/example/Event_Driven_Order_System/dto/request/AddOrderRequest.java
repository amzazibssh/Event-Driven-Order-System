package com.example.Event_Driven_Order_System.dto.request;

import com.example.Event_Driven_Order_System.entity.OrderState;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record AddOrderRequest(Date date, Map<UUID, Integer> productQuantity) {
}
