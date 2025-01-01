package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
//    private List<Category> categories=new ArrayList<>();
    private Long nextId=1L;

    @Autowired
    private CategoryRepository categoryRepository;
//    Spring creates a proxy class (a dynamically generated implementation) at runtime so that we can directly use object of a normal interface.

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public void createCategory(Category category) {

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        categoryRepository.delete(category);
        return "category with categoryId: "+categoryId+"deleted succesfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory=categoryRepository.findById(categoryId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        category.setCategoryId(categoryId);

        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
