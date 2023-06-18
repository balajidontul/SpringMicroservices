package com.edu.OrderService.controller;

import com.edu.OrderService.entity.Order;
import com.edu.OrderService.model.OrderRequest;
import com.edu.OrderService.model.OrderResponse;
import com.edu.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        Long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable long id ){
        OrderResponse orderResponse = orderService.getOrder(id);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);

    }


}
