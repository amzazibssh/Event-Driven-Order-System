package com.example.Event_Driven_Order_System.service;

import com.example.Event_Driven_Order_System.dto.dtos.OrderDTO;
import com.example.Event_Driven_Order_System.dto.mappers.OrderMapper;
import com.example.Event_Driven_Order_System.dto.request.AddOrderRequest;
import com.example.Event_Driven_Order_System.dto.response.AddOrderResponse;
import com.example.Event_Driven_Order_System.entity.OrderState;
import com.example.Event_Driven_Order_System.entity.Orders;
import com.example.Event_Driven_Order_System.entity.ProductOrder;
import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.exception.ProductNotFoundException;
import com.example.Event_Driven_Order_System.repository.OrderRepository;
import com.example.Event_Driven_Order_System.repository.ProductOrderRepository;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductOrderRepository productOrderRepository,
            ProductRepository productRepository
    ){
        this.orderRepository = orderRepository;
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }


    @Transactional
    public List<OrderDTO> getAllOrders(){
        List<Orders> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = OrderMapper.toDTOList(orders);
        return orderDTOList;
    }

    @Transactional
    public AddOrderResponse addOrder(AddOrderRequest request) {
        double total_price = 0.0;
        Orders order = new Orders(new Date(), OrderState.PENDING, total_price);
        List<ProductOrder> productOrderList = new ArrayList<>();
        List<String> noAvailableProduct = new ArrayList<>();
        for(Map.Entry<UUID, Integer> entry : request.productQuantity().entrySet()){
            Products product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ProductNotFoundException("Product with id " + entry.getKey() +  " not found"));
            if(entry.getValue() <= 0){
                throw new IllegalArgumentException(product.getName() + " required quantity must be positive");
            }
            if(product.getQuantity() >= entry.getValue()){
                total_price += entry.getValue() * product.getPrice();
                ProductOrder productOrder = new ProductOrder(order, product, product.getPrice(), entry.getValue());
                productOrderList.add(productOrder);
                product.setQuantity(product.getQuantity() - entry.getValue());
            }else{
                noAvailableProduct.add(product.getName());
            }

        }
        if (noAvailableProduct.size() == request.productQuantity().size()) {
            order.setStatus(OrderState.FAILED);
        } else {
            order.setStatus(OrderState.CONFIRMED);
        }
        order.setTotal_price(total_price);
        order.setProductOrders(productOrderList);
        orderRepository.save(order);
        return new AddOrderResponse(total_price, noAvailableProduct, order.getStatus());
    }

}
