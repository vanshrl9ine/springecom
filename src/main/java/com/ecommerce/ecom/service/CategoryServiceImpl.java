package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories=new ArrayList<>();
    private long nextId=1;
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }
    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category=categories.stream().
                filter(c->c.getCategoryId().equals(categoryId)).findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        categories.remove(category);
        return "category with categoryId: "+categoryId+"deleted succesfully";
    }

    @Override
    public Category updateCategory(Category category, long categoryId) {
        Optional<Category> optionalCategory=categories.stream().
                filter(c->c.getCategoryId().equals(categoryId)).findFirst();
        if(optionalCategory.isPresent()){
            Category existingCategory=optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found");
        }
    }
}
