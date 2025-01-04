package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category,Long categoryId);
}
