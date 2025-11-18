package org.example.ecommerce.Service;

import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {




    // create Virtual DataBase
    ArrayList<Product> products = new ArrayList<>();





    // display all products information from Virtual DataBase
    public ArrayList<Product> getProducts(){
        return products;
    }





    // add product information to the Virtual DataBase
    public void addProduct(Product product){
        products.add(product);
    }





    // update product information from the Virtual DataBase
    public boolean updateProduct(String productName , Product product){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductName().equalsIgnoreCase(productName)){
                products.set(i, product);
                return true;
            }
        }
        return false;
    }






    // delete product information from the Virtual DataBase
    public boolean deleteProduct(String productName){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductName().equalsIgnoreCase(productName)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }







}
