package com.ecommerce.ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "name>=3 chars")
    private String productName;

    @NotBlank
    @Size(min = 6, message = "Description>=6 chars")
    private String description;

    private double price;
    private Integer quantity;
    private double specialPrice;
    private double discount;
    private String image;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;


}
