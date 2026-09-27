package com.example.Event_Driven_Order_System.controller;

import com.example.Event_Driven_Order_System.dto.request.AddOrderRequest;
import com.example.Event_Driven_Order_System.dto.response.AddOrderResponse;
import com.example.Event_Driven_Order_System.entity.Orders;
import com.example.Event_Driven_Order_System.service.OrderService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @GetMapping
    public List<Orders> getOrder(){
        return orderService.getAllOrders();
    }


    @PostMapping
    public AddOrderResponse addOrder(@RequestBody AddOrderRequest request){
        return  orderService.addOrder(request);
    }

}
