package org.example.ecommerce.Model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "product Id can not be empty !")
    private String productID;

    @NotEmpty(message = "product name can not be empty ! ")
    @Size(min = 3, message = "product name must be at least 3 characters ! ")
    private String productName;

    @NotNull(message = "product price can not be empty !")
    @Min(value = 0 , message = " product price can not be less than zero ! ")
    private double productPrice;

    @NotEmpty(message = "category id can not be empty ! ")
    private String categoryID;

}
