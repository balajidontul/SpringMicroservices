package com.edu.OrderService.exception;


import com.edu.OrderService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleOrderServiceCustomException(OrderServiceCustomException exception){
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatus.valueOf(exception.getStatus())
                );


    }

}
