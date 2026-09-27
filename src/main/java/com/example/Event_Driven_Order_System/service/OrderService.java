package com.example.Event_Driven_Order_System.service;

import com.example.Event_Driven_Order_System.dto.request.AddOrderRequest;
import com.example.Event_Driven_Order_System.dto.response.AddOrderResponse;
import com.example.Event_Driven_Order_System.entity.OrderState;
import com.example.Event_Driven_Order_System.entity.Orders;
import com.example.Event_Driven_Order_System.entity.ProductOrder;
import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.repository.OrderRepository;
import com.example.Event_Driven_Order_System.repository.ProductOrderRepository;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public AddOrderResponse addOrder(AddOrderRequest request) {
        double total_price = 0.0;
        Orders order = new Orders(request.date(), OrderState.PENDING, total_price);
        List<ProductOrder> productOrderList = new ArrayList<>();
        List<String> noAvailableProduct = new ArrayList<>();
        for(Map.Entry<UUID, Integer> entry : request.productQuantity().entrySet()){
            Products product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
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
