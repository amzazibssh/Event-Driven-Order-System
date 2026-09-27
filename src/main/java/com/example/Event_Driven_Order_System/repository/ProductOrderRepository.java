package com.example.Event_Driven_Order_System.repository;

import com.example.Event_Driven_Order_System.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, UUID> {
}
