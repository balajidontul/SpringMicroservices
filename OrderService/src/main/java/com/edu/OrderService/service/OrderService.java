package com.edu.OrderService.service;

import com.edu.OrderService.entity.Order;
import com.edu.OrderService.model.OrderRequest;
import com.edu.OrderService.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrder(long id);
}
