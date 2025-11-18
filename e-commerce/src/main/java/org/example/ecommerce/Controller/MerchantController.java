package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Service.MerchantService;
import org.example.ecommerce.Service.MerchantStockService;
import org.example.ecommerce.Service.ProductService;
import org.example.ecommerce.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    // create instant variable from service class
    public final MerchantService merchantService;
    public final MerchantStockService merchantStockService;
    public final ProductService productService;
    public final UserService userService;


    // display all merchants information from Virtual DataBase
    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){

        if (merchantService.getMerchants().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchants in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }


    // add merchant information to the Virtual DataBase
    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant information has been added to the DataBase Successfully ! "));
    }


    // update merchant information from the Virtual DataBase
    @PutMapping("/update/{merchantName}")
    public ResponseEntity<?> updateMerchant( @PathVariable String merchantName , @Valid @RequestBody Merchant merchant , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = merchantService.updateMerchant(merchantName, merchant);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant information has been updated Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no merchant with that name in the DataBase is found to update it ! "));
    }


    // delete merchant information from Virtual DataBase
    @DeleteMapping("/delete/{merchantName}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String merchantName){

        boolean isDeleted = merchantService.deleteMerchant(merchantName);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant information has been deleted Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no merchant with that name in the DataBase is found to delete it ! "));
    }



    /// //////////////////////////////////////////////////////////////////////////////////


    // add more stock to a merchant stock
    @PutMapping("/add-stock/{productID}/{merchantID}/{amount}")
    public ResponseEntity<?> addMoreStock(@PathVariable String productID , @PathVariable String merchantID , @PathVariable int amount){

        String result  = merchantService.addMoreStock(productID , merchantID , amount, merchantStockService.getMerchantStocks());
        if (result.equalsIgnoreCase("Stoke has been filled and increased in the DataBase Successfully ! ")){
            return ResponseEntity.status(200).body(new ApiResponse("Stock has been increased Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no merchant stock with that information is found in the DataBase ! "));
    }


/// ////////////////////////////////////////////////////////////////

    // extra point 7  : calculate how much a merchant earned

        @GetMapping("/earnings/{merchantID}")
        public ResponseEntity<?> getMerchantEarnings(@PathVariable String merchantID){

            double earnings = merchantService.getEarnings(merchantID , merchantStockService.getMerchantStocks(), productService.getProducts());

            return ResponseEntity.status(200).body(new ApiResponse("Merchant earned is equal to : " + earnings + " SAR "));
        }



    // extra point 8 : see best and most selling product and how many it sells


    @GetMapping("/most-bought/{merchantID}")
    public ResponseEntity<?> getMostBought(@PathVariable String merchantID){

        String result = merchantService.getMerchantMostBoughtItem(merchantID, merchantStockService.getMerchantStocks(), productService.getProducts(), userService.getPurchaseHistory());

        return ResponseEntity.status(200).body(result);
    }


}
