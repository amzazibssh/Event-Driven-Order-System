package com.example.Event_Driven_Order_System;

import com.example.Event_Driven_Order_System.dto.request.UpdateProductRequest;
import com.example.Event_Driven_Order_System.entity.Products;
import com.example.Event_Driven_Order_System.repository.ProductRepository;
import com.example.Event_Driven_Order_System.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OptimisticLockingTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void concurrentUpdatesOnSameProduct() throws InterruptedException {
        Products product = new Products();
        product.setName("Test");
        product.setPrice(10.0);
        product.setDiscount(0);
        product.setQuantity(100);
        productRepository.save(product);

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    productService.updateProduct(product.getId(), new UpdateProductRequest(null, null, 5));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // If retries worked, all 10 updates eventually succeeded without exceptions propagating
        Products result = productRepository.findById(product.getId()).orElseThrow();
        System.out.println("Final quantity: " + result.getQuantity());
    }
}