package org.example.ecommerce.Service;


import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    // create Virtual DataBase
    ArrayList<Merchant> merchants = new ArrayList<>();


    // display all merchants information from Virtual DataBase
    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }


    // add merchant information to the Virtual DataBase
    public void addMerchant(Merchant merchant){
        merchants.add(merchant);
    }


    // update merchant information from the Virtual DataBase
    public boolean updateMerchant(String merchantName , Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getMerchantName().equalsIgnoreCase(merchantName)){
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }


    // delete merchant information from the Virtual DataBase
    public boolean deleteMerchant(String merchantName){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getMerchantName().equalsIgnoreCase(merchantName)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }



    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // add more stock to a specific merchant stock in the Virtual DataBase
    public String addMoreStock(String productID , String merchantID , int amount , ArrayList<MerchantStock> merchantStocks){

        for (int i = 0 ; i < merchantStocks.size(); i++) {

            if (merchantStocks.get(i).getProductID().equalsIgnoreCase(productID) && merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID)) {

                merchantStocks.get(i).setMerchantStock( merchantStocks.get(i).getMerchantStock() + amount );
                return "Stoke has been filled and increased in the DataBase Successfully ! ";
            }

        }

        return "error no merchant stoke with that information is found in the DataBase to fill and increase it to the DataBase ! ";
    }




        /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // extra point 7 : calculate how much a merchant earned
        public double getEarnings(String merchantID , ArrayList<MerchantStock> merchantStocks , ArrayList<Product> products){

            double totalEarnings = 0;

            for (int i = 0; i < merchantStocks.size(); i++) {

                if (merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID)){

                    int currentStock = merchantStocks.get(i).getMerchantStock();
                    int initialStock = 10;

                    int sold = initialStock - currentStock;

                    if (sold > 0){

                        for (int j = 0; j < products.size(); j++) {
                            if (products.get(j).getProductID().equalsIgnoreCase(merchantStocks.get(i).getProductID())){

                                totalEarnings += sold * products.get(j).getProductPrice();
                                break;
                            }
                        }

                    }
                }
            }

            return totalEarnings;
        }

    // extra point 8 : see best and most selling product and how many it sells

    public String getMerchantMostBoughtItem(String merchantID, ArrayList<MerchantStock> merchantStocks, ArrayList<Product> products, ArrayList<String> purchaseHistory) {


        int maxCount = 0;
        String mostBoughtProductID = null;


       // see stocks of this merchant

        for (int i = 0; i < merchantStocks.size(); i++) {

            if (merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID)) {

                String productID = merchantStocks.get(i).getProductID();
                int count = 0;

                // count purchases from history
                for (int j = 0; j < purchaseHistory.size(); j++) {
                    if (purchaseHistory.get(j).contains(":" + productID + ":")) {
                        count++;
                    }
                }

                if (count > maxCount) {
                    maxCount = count;
                    mostBoughtProductID = productID;
                }
            }
        }

        if (mostBoughtProductID == null) {
            return "This merchant has no items purchased yet ! ";
        }

        // get product name
        String productName = "";

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equalsIgnoreCase(mostBoughtProductID)) {
                productName = products.get(i).getProductName();
                break;
            }
        }

        return ( productName + " was bought " + maxCount + " times" ) ;
    }






}
