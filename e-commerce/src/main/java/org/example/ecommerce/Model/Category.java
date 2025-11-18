package org.example.ecommerce.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {


    @NotEmpty(message = "category id can not be empty ! ")
    private String categoryID;


    @NotEmpty(message = "category name can not be empty ! ")
    @Size(min = 3, message = "category name must be at least 3 characters ! ")
    private String categoryName;


}
