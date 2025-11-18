package org.example.ecommerce.Service;

import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {


    // create Virtual DataBase
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();


    // display all merchant stocks information from Virtual DataBase
    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }


    // add merchant stock information to the Virtual DataBase
    public void addMerchantStock(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);
    }


    // update merchant stock information from the Virtual DataBase
    public boolean updateMerchantStock(String merchantStockID , MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMerchantStockID().equalsIgnoreCase(merchantStockID)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }


    // delete merchant stock information from the Virtual DataBase
    public boolean deleteMerchantStock(String merchantStockID){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMerchantStockID().equalsIgnoreCase(merchantStockID)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }


    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





}
