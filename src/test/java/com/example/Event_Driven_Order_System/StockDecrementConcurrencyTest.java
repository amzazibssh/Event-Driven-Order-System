    package com.example.Event_Driven_Order_System;

import com.example.Event_Driven_Order_System.dto.request.AddOrderRequest;
import com.example.Event_Driven_Order_System.dto.response.AddOrderResponse;
import com.example.Event_Driven_Order_System.entity.OrderState;
import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import com.example.Event_Driven_Order_System.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StockDecrementConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void concurrentOrdersDoNotOversellStock() throws InterruptedException {
        int threads = 10;
        int startingStock = threads - 1; // 9 in stock, 10 buyers — exactly one must fail

        Products product = new Products();
        product.setName("Contested Item");
        product.setPrice(10.0);
        product.setDiscount(0);
        product.setQuantity(startingStock);
        productRepository.save(product);

        UUID productId = product.getId();

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        AtomicInteger confirmed = new AtomicInteger(0);
        AtomicInteger failed = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    AddOrderRequest request = new AddOrderRequest(Map.of(productId, 1));
                    AddOrderResponse response = orderService.addOrder(request);

                    if (response.status() == OrderState.CONFIRMED) {
                        confirmed.incrementAndGet();
                    } else if (response.status() == OrderState.FAILED) {
                        failed.incrementAndGet();
                    }
                } catch (Exception e) {
                    System.out.println("Thread threw exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        Products result = productRepository.findById(productId).orElseThrow();

        System.out.println("Confirmed: " + confirmed.get());
        System.out.println("Failed: " + failed.get());
        System.out.println("Final quantity: " + result.getQuantity());

        assertEquals(startingStock, confirmed.get(), "Exactly starting stock count of orders should succeed");
        assertEquals(1, failed.get(), "Exactly one order should fail due to insufficient stock");
        assertEquals(0, result.getQuantity(), "Final stock should be exactly zero — no overselling, no lost decrements");
    }
}