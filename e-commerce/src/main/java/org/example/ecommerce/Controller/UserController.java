package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.example.ecommerce.Service.CategoryService;
import org.example.ecommerce.Service.MerchantStockService;
import org.example.ecommerce.Service.ProductService;
import org.example.ecommerce.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {


    // create instant variable from service class
    public final UserService userService;
    public final ProductService productService;
    public final MerchantStockService merchantStockService;
    public final CategoryService categoryService;



    // display all users information from Virtual DataBase
    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){

        if (userService.getUsers().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no users in the DataBase to show their information !"));
        }

        return  ResponseEntity.status(200).body(userService.getUsers());

    }




    // add user information to the Virtual DataBase
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user information has been added to the DataBase Successfully ! "));
    }





    // update user information from the Virtual DataBase
    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName , @Valid @RequestBody User user , Errors errors){

      if (errors.hasErrors()){
          String message = errors.getFieldError().getDefaultMessage();
          return ResponseEntity.status(400).body(new ApiResponse(message));
      }

      boolean isUpdated = userService.updateUser(userName,user);
      if (isUpdated){
          userService.updateUser(userName, user);
          return ResponseEntity.status(200).body(new ApiResponse("user information has been updated Successfully ! "));
      }

      return ResponseEntity.status(400).body(new ApiResponse("no user with that UserName in the DataBase  is found to update it ! "));
    }





    // delete user information from Virtual DataBase
    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){

        boolean isDeleted = userService.deleteUser(userName);
        if (isDeleted){
            userService.deleteUser(userName);
            return ResponseEntity.status(200).body(new ApiResponse("user information has been deleted Successfully ! "));
        }

        return ResponseEntity.status(400).body(new ApiResponse("no user with that UserName in the DataBase is found to delete it ! "));

    }





    /// //////////////////////////////////////////////////

    // user buy a product
    @PutMapping("/buy/{userID}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProduct(@PathVariable String userID , @PathVariable String productID , @PathVariable String merchantID){


        int result = userService.buyProduct(userID , productID , merchantID , productService.getProducts() , merchantStockService.getMerchantStocks());

        switch (result){

            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("Product has been purchased Successfully !"));

            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("User ID is not found in the DataBase !"));

            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Product ID is not found in the DataBase !"));

            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant does not have this product in the stock !"));

            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("This product is out of stock !"));

            case 6:
                return ResponseEntity.status(400).body(new ApiResponse("User balance is less than product price !"));

            default:
                return ResponseEntity.status(400).body(new ApiResponse("some error happened !"));
        }


    }




    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // extra point 1 : make user see product from cheap to expensive
    @GetMapping("/cheap-to-expensive")
    public ResponseEntity<?> getSortedProducts(){

        ArrayList<Product> sorted = userService.getSortedProducts(productService.getProducts());

        if (sorted.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products in the DataBase !"));
        }

        return ResponseEntity.status(200).body(sorted);
    }









    // extra point 2  : add money to the balance by id
    @PutMapping("/add-balance/{userID}/{amount}")
    public ResponseEntity<?> addBalance(@PathVariable String userID , @PathVariable double amount){

        boolean isAdded = userService.addToBalance(userID , amount);

        if (isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Balance has been added Successfully !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("No user with that ID in the DataBase !"));
    }






    // extra point 3  : see all product for a specific  category by category name
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryName){

        ArrayList<Product> products = userService.getByCategory(categoryName , categoryService.getCategories() , productService.getProducts());

        if (products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No products with that category name in the DataBase !"));
        }

        return ResponseEntity.status(200).body(products);
    }






    // extra point 4  : Search for a product by product name
    @GetMapping("/search-product/{productName}")
    public ResponseEntity<?> searchProduct(@PathVariable String productName){

        Product product = userService.foundProduct(productName , productService.getProducts());

        if (product == null){
            return ResponseEntity.status(400).body(new ApiResponse("No product with that name in the DataBase !"));
        }

        return ResponseEntity.status(200).body(product);
    }






    // extra point 5  : see product that are in stock only
    @GetMapping("/in-stock")
    public ResponseEntity<?> getProductsInStock(){

        ArrayList<MerchantStock> inStock = userService.getProductInStock(merchantStockService.getMerchantStocks());

        if (inStock.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products in stock in the DataBase !"));
        }

        return ResponseEntity.status(200).body(inStock);
    }



    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////







}
