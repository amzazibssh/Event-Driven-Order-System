package com.example.Event_Driven_Order_System.repository;

import com.example.Event_Driven_Order_System.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {
}
