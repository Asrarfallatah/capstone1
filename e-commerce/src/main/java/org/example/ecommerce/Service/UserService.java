package org.example.ecommerce.Service;

import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {


    // 48 hour challenge no AI just focus,  search, see educational vid ,  look for idea, if fails idea delete it and go to safer one  !! goal t win !!

    //create Virtual DataBase
    ArrayList<User> users = new ArrayList<>();

    private final ArrayList<String> purchaseHistory = new ArrayList<>();


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

                // Check if the user is admin
                if (user.getUserRole().equalsIgnoreCase("admin")){
                    return 7; // Admin can not buy
                }

                // now check product inside user loop
                for (int j = 0; j < products.size(); j++) {

                    if (products.get(j).getProductID().equalsIgnoreCase(productID)){

                        Product product = products.get(j);

                        // check merchant stock
                        for (int k = 0; k < merchantStocks.size(); k++) {

                            if (merchantStocks.get(k).getProductID().equalsIgnoreCase(productID) && merchantStocks.get(k).getMerchantID().equalsIgnoreCase(merchantID)){

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

                                // Log purchase to history
                                purchaseHistory.add(userID + ":" + product.getProductName() + ":" + product.getProductPrice());

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




   //  extra point 1 : make user see product from cheap to expensive by category using bubble sort

            public ArrayList<Product> sortProductsByCategory(String categoryName, String order, ArrayList<Category> categories, ArrayList<Product> products) {

                ArrayList<Product> sorted = new ArrayList<>();

                // get categoryID
                String categoryID = null;
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)) {
                        categoryID = categories.get(i).getCategoryID();
                        break;
                    }
                }

                if (categoryID == null) {
                    return sorted;
                }

                // add products of that category
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getCategoryID().equalsIgnoreCase(categoryID)) {
                        sorted.add(products.get(i));
                    }
                }

                // bubble sort
                for (int i = 0; i < sorted.size(); i++) {
                    for (int j = 0; j < sorted.size() - 1; j++) {

                        if (order.equalsIgnoreCase("asc")) {
                            // cheap to expensive
                            if (sorted.get(j).getProductPrice() > sorted.get(j + 1).getProductPrice()) {
                                Product tmp = sorted.get(j);
                                sorted.set(j, sorted.get(j + 1));
                                sorted.set(j + 1, tmp);
                            }
                        } else {
                            // expensive to cheap
                            if (sorted.get(j).getProductPrice() < sorted.get(j + 1).getProductPrice()) {
                                Product tmp = sorted.get(j);
                                sorted.set(j, sorted.get(j + 1));
                                sorted.set(j + 1, tmp);
                            }
                        }

                    }
                }

                return sorted;
            }








    // extra point 2 : see all product for a specific  category by category name


        public  ArrayList<Product> getByCategory (String categoryName , ArrayList<Category> categories, ArrayList<Product> products){

            ArrayList<Product> foundProducts = new ArrayList<>();

            //find categoryID by category name
            String categoryID = null;
            for (int i = 0; i < categories.size(); i++) {

                if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)){
                    categoryID = categories.get(i).getCategoryID();
                    break;
                }
            }

            // if categoryID not found
            if (categoryID == null){
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
                double oldBalance = users.get(i).getUserBalance();
                users.get(i).setUserBalance( (oldBalance + userBalance) );
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




    // extra point 5  :  to search for a specific product by product name and see its information
     public  Product foundProduct(String productName, ArrayList<Product> product ){

             for (int i = 0; i < product.size(); i++) {

                 if (product.get(i).getProductName().equalsIgnoreCase(productName)){
                     return product.get(i);
                 }
             }

             return null;
         }





    // extra point 6 : history

    public ArrayList<String> getUserPurchaseHistory(String userID) {

        ArrayList<String> userHistory = new ArrayList<>();

        for (int i = 0; i < purchaseHistory.size(); i++) {
            String record = purchaseHistory.get(i);
            // Check userID
            if (record.startsWith(userID + ":")) {
                userHistory.add(record);
            }
        }
        return userHistory;
    }

         /// //////////////////////////////////////////////////////////////////////////////////////////////////////////

         //this is for extra point 8 in merchant  service and controller

         public ArrayList<String> getPurchaseHistory(){
             return purchaseHistory;
         }



}
