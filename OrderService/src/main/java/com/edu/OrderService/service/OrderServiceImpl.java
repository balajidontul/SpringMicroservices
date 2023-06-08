package com.edu.OrderService.service;

import com.edu.OrderService.entity.Order;
import com.edu.OrderService.model.OrderRequest;
import com.edu.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Inside Order repository - placing order request: {}", orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity()).build();

        Order orderConfirmation = orderRepository.save(order);
        log.info("Placed order successfully: {} ", orderConfirmation.getId());
        return orderConfirmation.getId();
    }
}