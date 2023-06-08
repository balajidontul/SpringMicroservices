package com.edu.productService.service;

import com.edu.productService.model.ProductRequest;
import com.edu.productService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);
}
