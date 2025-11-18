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

            if ((merchantStocks.get(i).getProductID().equalsIgnoreCase(productID)) && (merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID))) {

                merchantStocks.get(i).setMerchantStock( (merchantStocks.get(i).getMerchantStock() + amount) ) ;
                return "stoke has been filled and increased in the DataBase Successfully ! ";
            }

        }

        return "error no merchant stoke with that information is found in the DataBase to fill and increase it to the DataBase ! ";
    }


    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    // extra credit 2  : calculate how much a merchant earned
//
//    public double getMerchantEarnings(String merchantID , ArrayList<MerchantStock> merchantStocks , ArrayList<Product> products){
//
//        double totalEarnings = 0;
//
//        for (int i = 0; i < merchantStocks.size(); i++) {
//
//
//            if (merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID)){
//
//                int currentStock = merchantStocks.get(i).getMerchantStock();
//                int initialStock = 10;
//
//                int sold = initialStock - currentStock;
//
//                // find product price
//                for (int j = 0; j < products.size(); j++) {
//
//                    if (products.get(j).getProductID().equalsIgnoreCase(merchantStocks.get(i).getProductID())){
//
//                        totalEarnings += sold * products.get(j).getProductPrice();
//                        break;
//                    }
//
//
//                }
//
//
//
//            }
//
//        }
//
//        return totalEarnings;
//    }


}
