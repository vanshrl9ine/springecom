package com.ecommerce.ecom.service;

import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.payload.ProductDTO;
import com.ecommerce.ecom.repositories.CategoryRepository;
import com.ecommerce.ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice= product.getPrice()*((100 - product.getDiscount())/100);
        product.setSpecialPrice(specialPrice);
        Product savedProduct=productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);


    }
}
