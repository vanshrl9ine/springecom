package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.payload.ProductDTO;
import com.ecommerce.ecom.payload.ProductResponse;
import com.ecommerce.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

     @Autowired
     private ProductService productService;
     @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
        ProductDTO productDTO=productService.addProduct(categoryId,product);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.CREATED);

     }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getProducts() {
        ProductResponse productResponse=productService.getAllProducts();
        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);

    }
    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
         ProductResponse productResponse=productService.searchByCategory(categoryId);
         return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }
}
