package com.ecommerce.ecom.service;

import com.ecommerce.ecom.exceptions.APIException;
import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
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



    @Autowired
    private CategoryRepository categoryRepository;
//    Spring creates a proxy class (a dynamically generated implementation) at runtime so that we can directly use object of a normal interface.

    @Override
    public List<Category> getAllCategories() {

        List<Category> savedList=categoryRepository.findAll();
        if(savedList.isEmpty()){
            throw new APIException("No categories found");
        }
        return savedList;
    }
    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new APIException("Category with provided name already exists");
        }
        //this method doesnt exist naturally  so we need to extend with JPA
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(category);
        return "category with categoryId: "+categoryId+"deleted succesfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(categoryId);

        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
