package org.example.ecommerce.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {


    @NotEmpty(message = "merchant id can not be empty ! ")
    private String merchantID;


    @NotEmpty(message = "merchant name can not be empty ! ")
    @Size(min = 3, message = "merchant name must be at least 3 characters ! ")
    private String merchantName;


}
