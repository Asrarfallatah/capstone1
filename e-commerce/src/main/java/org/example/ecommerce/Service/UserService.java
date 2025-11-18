package org.example.ecommerce.Service;

import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {


    //create Virtual DataBase
    ArrayList<User> users = new ArrayList<>();


    // display all users information from Virtual DataBase
    public ArrayList<User> getUsers(){
        return users;
    }


    // add user information to the Virtual DataBase
    public void addUser(User user){
        users.add(user);
    }


    // update user information from the Virtual DataBase
    public boolean updateUser(String userName, User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equalsIgnoreCase(userName)){
                users.set(i, user);
                return true;
            }
        }
        return false;
    }


    // delete user information from Virtual DataBase
    public boolean deleteUser (String userName){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equalsIgnoreCase(userName)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }


    /// //////////////////////////////////////////////////////////////////

    // buy product from a merchant stock
    public int buyProduct(String userID , String productID , String merchantID , ArrayList<Product> products , ArrayList<MerchantStock> merchantStocks){

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserID().equalsIgnoreCase(userID)){

                User user = users.get(i);

                // now check product inside user loop
                for (int j = 0; j < products.size(); j++) {

                    if (products.get(j).getProductID().equalsIgnoreCase(productID)){

                        Product product = products.get(j);

                        // check merchant stock
                        for (int k = 0; k < merchantStocks.size(); k++) {

                            if (merchantStocks.get(k).getProductID().equalsIgnoreCase(productID)
                                    && merchantStocks.get(k).getMerchantID().equalsIgnoreCase(merchantID)){

                                MerchantStock merchantStock = merchantStocks.get(k);

                                // check stock
                                if (merchantStock.getMerchantStock() <= 0){
                                    return 5; // out of stock
                                }

                                // check balance
                                if (user.getUserBalance() < product.getProductPrice()){
                                    return 6; // not enough balance
                                }

                                // decrease stock
                                merchantStock.setMerchantStock(merchantStock.getMerchantStock() - 1);

                                // deduct price
                                user.setUserBalance(user.getUserBalance() - product.getProductPrice());

                                return 1; // success
                            }
                        }

                        return 4; // merchant does not have this product
                    }
                }

                return 3; // product not found
            }
        }

        return 2; // user not found
    }




    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////

   //  extra point 1 : make user see product from cheap to expensive (

             public ArrayList<Product> getSortedProducts(ArrayList<Product> products){

                 // create new list
                 ArrayList<Product> sortedProducts = new ArrayList<>(products);


                 for (int i = 0; i < sortedProducts.size(); i++) {

                             for (int j = 0; j < sortedProducts.size() - 1; j++) {

                                 if (sortedProducts.get(j).getProductPrice() > sortedProducts.get(j + 1).getProductPrice()) {

                                     // switch
                                     Product temp = sortedProducts.get(j);
                                     sortedProducts.set(j, sortedProducts.get(j + 1));
                                     sortedProducts.set(j + 1, temp);
                                 }
                             }
                 }

                 return sortedProducts;
             }



    // extra point 2 : see all product for a specific  category by category name
    public  ArrayList<Product> getByCategory (String categoryName , ArrayList<Category> categories, ArrayList<Product> products){

        ArrayList<Product> foundProducts = new ArrayList<>();

        //find categoryID by categoryName
        String categoryID = "not found";
        for (int i = 0; i < categories.size(); i++) {

            if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)){
                categoryID = categories.get(i).getCategoryID();
                break;
            }
        }

        // if categoryID not found
        if (categoryID.equalsIgnoreCase( "not found")){
            return foundProducts;
        }

        //check category product
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getCategoryID().equalsIgnoreCase(categoryID)){
                foundProducts.add(products.get(i));
            }
        }

        return foundProducts;
    }




    // extra point 3 : add money to the balance to a specific user by user id
    public boolean addToBalance(String userID,  double userBalance){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equalsIgnoreCase(userID)){
                users.get(i).setUserBalance(userBalance);
                return true;
            }
        }
        return false;
    }




    // extra point 4  : see all in stock product

    public ArrayList<MerchantStock> getProductInStock (ArrayList<MerchantStock> merchantStocks){

        ArrayList<MerchantStock> isInStock = new ArrayList<>();

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMerchantStock() > 0 ){
                isInStock.add(merchantStocks.get(i));
            }
        }
        return isInStock;
    }




    // extra point 5  : search for a specific product by product name and see its information
     public  Product foundProduct(String productName, ArrayList<Product> product ){

             for (int i = 0; i < product.size(); i++) {

                 if (product.get(i).getProductName().equalsIgnoreCase(productName)){
                     return product.get(i);
                 }
             }

             return null;
         }



         /// //////////////////////////////////////////////////////////////////////////////////////////////////////////



}
