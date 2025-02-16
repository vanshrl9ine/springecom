package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.config.AppConstants;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.payload.ProductDTO;
import com.ecommerce.ecom.payload.ProductResponse;
import com.ecommerce.ecom.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

     @Autowired
     private ProductService productService;
     @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid  @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        ProductDTO savedproductDTO=productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<ProductDTO>(savedproductDTO,HttpStatus.CREATED);

     }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy
            , @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
            ) {

        ProductResponse productResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);

    }
    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId, @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy
            , @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
        ) {
         ProductResponse productResponse=productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
         return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }
    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword, @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy
            , @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ) {
        ProductResponse productResponse=productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }
    @PutMapping("/admin/products/{productId}/categories/{categoryId}")
    public ResponseEntity<ProductDTO> updateProduct( @Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId,@PathVariable Long categoryId) {
         ProductDTO updatedProductDTO=productService.updateProduct(productDTO,productId,categoryId);
         return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);

    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
         ProductDTO deletedProductDTO=productService.deleteProduct(productId);
         return new ResponseEntity<>(deletedProductDTO,HttpStatus.OK);
    }
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> uploadProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
         ProductDTO updatedProductDTO=productService.updateProductImage(productId, image);
         return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);


    }
}
