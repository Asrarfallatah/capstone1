package org.example.ecommerce.Service;

import org.example.ecommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {




    // create Virtual DataBase
    ArrayList<Category> categories = new ArrayList<>();




    // display all categories information from Virtual DataBase
    public ArrayList<Category> getCategories(){
        return categories;
    }




    // add category information to the Virtual DataBase
    public void addCategory(Category category){
        categories.add(category);
    }




    // update category information from the Virtual DataBase
    public boolean updateCategory(String categoryName , Category category){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }




    // delete category information from the Virtual DataBase
    public boolean deleteCategory(String categoryName){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }






}
