package com.ecommerce.ecom.repositories;

import com.ecommerce.ecom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Long> {

}
