package com.example.Event_Driven_Order_System.dto.response;

import com.example.Event_Driven_Order_System.entity.OrderState;

import java.util.List;

public record AddOrderResponse(double total_price, List<String> noAvailabeProduct, OrderState status) {
}
