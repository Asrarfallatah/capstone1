package org.example.ecommerce.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "merchant stock id can not be empty ! ")
    private String merchantStockID;

    @NotEmpty(message = "product Id can not be empty !")
    private String productID;

    @NotEmpty(message = "merchant id can not be empty ! ")
    private String merchantID;

    @NotNull(message = "merchant stock can not be empty ! ")
    @Min(value = 10, message = "merchant stock must be at least 10 ! ")
    private int merchantStock;


}
