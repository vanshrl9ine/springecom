package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
