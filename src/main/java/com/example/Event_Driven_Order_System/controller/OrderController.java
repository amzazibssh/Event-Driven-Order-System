package com.example.Event_Driven_Order_System.controller;

import com.example.Event_Driven_Order_System.dto.dtos.OrderDTO;
import com.example.Event_Driven_Order_System.dto.request.AddOrderRequest;
import com.example.Event_Driven_Order_System.dto.response.AddOrderResponse;
import com.example.Event_Driven_Order_System.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<OrderDTO>> getOrder(){
        List<OrderDTO> response =  orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<AddOrderResponse> addOrder(@Valid @RequestBody AddOrderRequest request){
        AddOrderResponse response = orderService.addOrder(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
