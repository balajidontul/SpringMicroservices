package com.edu.OrderService.service;

import com.edu.OrderService.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}
