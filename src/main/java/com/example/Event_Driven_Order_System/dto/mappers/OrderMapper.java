package com.example.Event_Driven_Order_System.dto.mappers;

import com.example.Event_Driven_Order_System.dto.dtos.OrderDTO;
import com.example.Event_Driven_Order_System.dto.dtos.OrderItemDTO;
import com.example.Event_Driven_Order_System.entity.Orders;

import java.util.List;
import java.util.stream.Collectors;
public class OrderMapper {
    public static OrderDTO toDTO(Orders order) {
        List<OrderItemDTO> items = order.getProductOrders().stream()
                .map(po -> new OrderItemDTO(
                        po.getProduct().getName(),
                        po.getQuantity(),
                        po.getUnitPrice()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrder_date(),
                order.getStatus(),
                order.getTotal_price(),
                items
        );
    }

    public static List<OrderDTO> toDTOList(List<Orders> orders) {
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}