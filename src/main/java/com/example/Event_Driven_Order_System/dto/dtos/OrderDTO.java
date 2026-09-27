package com.example.Event_Driven_Order_System.dto.dtos;

import com.example.Event_Driven_Order_System.entity.OrderState;
import com.example.Event_Driven_Order_System.entity.ProductOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

public record OrderDTO(
        Date order_date,
        OrderState status,
        double total_price,
        List<OrderItemDTO> items
){}
