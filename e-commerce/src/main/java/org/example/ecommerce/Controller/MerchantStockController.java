package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    // create instant variable from service class
    public final MerchantStockService merchantStockService;


    // display all merchant stocks information from Virtual DataBase
    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks(){

        if (merchantStockService.getMerchantStocks().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchant stocks in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }


    // add merchant stock information to the Virtual DataBase
    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock information has been added to the DataBase Successfully ! "));
    }


    // update merchant stock information from the Virtual DataBase
    @PutMapping("/update/{merchantStockID}")
    public ResponseEntity<?> updateMerchantStock( @PathVariable String merchantStockID ,@Valid @RequestBody MerchantStock merchantStock , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = merchantStockService.updateMerchantStock(merchantStockID, merchantStock);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock information has been updated Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no merchant stock with that ID in the DataBase is found to update it ! "));
    }


    // delete merchant stock information from Virtual DataBase
    @DeleteMapping("/delete/{merchantStockID}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String merchantStockID){

        boolean isDeleted = merchantStockService.deleteMerchantStock(merchantStockID);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock information has been deleted Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no merchant stock with that ID in the DataBase is found to delete it ! "));
    }


    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////







}
