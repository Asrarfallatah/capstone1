package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {


    // create instant variable from service class
    public final ProductService productService;







    // display all products information from Virtual DataBase
    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){

        if (productService.getProducts().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(productService.getProducts());
    }







    // add product information to the Virtual DataBase
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("Product information has been added to the DataBase Successfully ! "));
    }







    // update product information from the Virtual DataBase
    @PutMapping("/update/{productName}")
    public ResponseEntity<?> updateProduct(@PathVariable String productName ,@Valid @RequestBody Product product , Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = productService.updateProduct(productName, product);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Product information has been updated Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no product with that name in the DataBase is found to update it ! "));
    }






    // delete product information from Virtual DataBase
    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productName){

        boolean isDeleted = productService.deleteProduct(productName);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product information has been deleted Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no product with that name in the DataBase is found to delete it ! "));
    }





}
