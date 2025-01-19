package com.ecommerce.ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min=5,message="street name must be atleast 5 charachters")
    private String street;

    @NotBlank
    @Size(min=3,message = "building name must be atleast 3 charachters")
    private String buildingName;

    @NotBlank
    @Size(min=3,message = "City name must be atleast 3 charachters")
    private String city;

    @NotBlank
    @Size(min=3,message = "state name must be atleast 3 charachters")
    private String state;

    @NotBlank
    @Size(min=3,message = "country name must be atleast 3 charachters")
    private String country;

    @NotBlank
    @Size(min=6,message = "pincode must be atleast 3 charachters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users=new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
