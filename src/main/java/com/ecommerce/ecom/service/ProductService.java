package com.ecommerce.ecom.service;

import com.ecommerce.ecom.payload.ProductDTO;
import com.ecommerce.ecom.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO product, Long productId,Long categoryId);

    ProductDTO deleteProduct(Long productId);
}
