package com.edu.OrderService.service;

import com.edu.OrderService.entity.Order;
import com.edu.OrderService.exception.OrderServiceCustomException;
import com.edu.OrderService.external.Model.PaymentRequest;
import com.edu.OrderService.external.client.PaymentService;
import com.edu.OrderService.external.client.ProductService;
import com.edu.OrderService.model.OrderRequest;
import com.edu.OrderService.model.OrderResponse;
import com.edu.OrderService.model.PaymentResponse;
import com.edu.OrderService.model.ProductResponse;
import com.edu.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Inside Order repository - placing order request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());


        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity()).build();

        Order orderConfirmation = orderRepository.save(order);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                        .orderId(order.getId())
                                .paymentMode(orderRequest.getPaymentMode())
                                        .amount(orderRequest.getTotalAmount()).build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("PAYMENT DONE SUCCESSFULLY");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.info("PAYMENT FAILED");
            orderStatus = "FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);


        log.info("Placed order successfully: {} ", orderConfirmation.getId());
        return orderConfirmation.getId();
    }

    @Override
    public OrderResponse getOrder(long id) {
        Order order = orderRepository.findById(id).
                orElseThrow( () -> new OrderServiceCustomException("Order not found for order Id",
                        "Not Found", 404));


        log.info("Inside getOrder of Order service - Getting Product information for get order call");

        ProductResponse productResponse =  restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), ProductResponse.class );

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .productId(productResponse.getProductId())
                .build();

        log.info("Inside getOrder of Order service - Getting payment information for get order call");

        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+id, PaymentResponse.class);

        OrderResponse.PaymentDetails paymentDetails =
                OrderResponse.PaymentDetails.builder()
                        .paymentId(paymentResponse.getPaymentId())
                        .paymentDate(paymentResponse.getPaymentDate())
                        .paymentStatus(paymentResponse.getPaymentStatus())
                        .amount(paymentResponse.getAmount())
                        .paymentMode(paymentResponse.getPaymentMode())
                        .build();

        return OrderResponse.builder()
                .orderId(order.getId())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
    }
}
