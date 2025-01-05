package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.config.AppConstants;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponse;
import com.ecommerce.ecom.service.CategoryService;
import jakarta.validation.Valid;
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



//    @GetMapping("/echo")
//    public ResponseEntity<String> echoMessage(@RequestParam(name="message",defaultValue = "Hello World") String message){
//        return new ResponseEntity<String>("Message received: "+message,HttpStatus.OK);
//    } jus for learning

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber, @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize){
        CategoryResponse categoryResponse= categoryService.getAllCategories(pageNumber,pageSize);
        return  new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){

         CategoryDTO savedCategoryDTO=categoryService.createCategory(categoryDTO);
        return  new ResponseEntity<CategoryDTO>(savedCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){

            CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
            return  new ResponseEntity<CategoryDTO>(deletedCategoryDTO,HttpStatus.OK);

    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory( @Valid @RequestBody CategoryDTO categoryDTO ,@PathVariable Long categoryId){

            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return  new ResponseEntity<CategoryDTO>(savedCategoryDTO,HttpStatus.OK);

    }
}
