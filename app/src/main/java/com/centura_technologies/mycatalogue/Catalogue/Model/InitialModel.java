package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 23-08-2016.
 */
public class InitialModel {
    ArrayList<Sections> Sections=new ArrayList<Sections>();
    ArrayList<Categories> Categories=new ArrayList<Categories>();
    ArrayList<Products> Products=new ArrayList<Products>();

    public ArrayList<com.centura_technologies.mycatalogue.Catalogue.Model.Sections> getSections() {
        return Sections;
    }

    public void setSections(ArrayList<com.centura_technologies.mycatalogue.Catalogue.Model.Sections> sections) {
        Sections = sections;
    }

    public ArrayList<Categories> getCategories() {
        return Categories;
    }

    public void setCategories(ArrayList<com.centura_technologies.mycatalogue.Catalogue.Model.Categories> categories) {
        Categories = categories;
    }

    public ArrayList<com.centura_technologies.mycatalogue.Catalogue.Model.Products> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<com.centura_technologies.mycatalogue.Catalogue.Model.Products> products) {
        Products = products;
    }
}
