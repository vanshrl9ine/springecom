package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }do not need constructor injection with autowired annotation

    @GetMapping("/public/categories")
    public ResponseEntity getAllCategories(){
        List<Category> categories= categoryService.getAllCategories();
        return  new ResponseEntity<>(categories,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity createCategory(@RequestBody Category category){
     categoryService.createCategory(category);
        return  new ResponseEntity<>("Category Added succesfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/public/categories/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable Long categoryId){
        try {
            String status = categoryService.deleteCategory(categoryId);
            return  new ResponseEntity<>(status,HttpStatus.OK);

        }catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity updateCategory(@RequestBody Category category ,@PathVariable long categoryId){
        try{
            Category savedCategory = categoryService.updateCategory(category,categoryId);
            return  new ResponseEntity<>("Category with Category id updated : "+categoryId,HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}
