package com.example.Event_Driven_Order_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

    public ProductOrder(
            Orders order,
            Products product,
            double unitPrice,
            int quantity
    ) {
        this.order = order;
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;

    private double unitPrice;
    private int quantity;
}

