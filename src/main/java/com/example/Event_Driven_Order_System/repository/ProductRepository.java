package com.example.Event_Driven_Order_System.repository;

import com.example.Event_Driven_Order_System.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID> {
}
