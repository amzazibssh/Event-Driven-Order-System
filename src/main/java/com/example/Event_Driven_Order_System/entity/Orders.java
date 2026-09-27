package com.example.Event_Driven_Order_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class Orders {

    public Orders(
            Date orderDate,
            OrderState status,
            double totalPrice
    ) {
        this.order_date = orderDate;
        this.status = status;
        this.total_price = totalPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date order_date;
    private OrderState status;
    private double total_price;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ProductOrder> productOrders;
}
