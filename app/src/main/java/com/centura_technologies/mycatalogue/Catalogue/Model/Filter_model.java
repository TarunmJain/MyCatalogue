package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 29-04-2016.
 */
public class Filter_model {
       public Double minprice;
       public Double maxprice;
       public ArrayList<FilterItem> item;

    public Filter_model() {
        minprice = 0.0;
        maxprice = 0.0;
        ArrayList<FilterItem> item=new ArrayList<>();
    }

    public Double getMinprice() {
        return minprice;
    }

    public void setMinprice(Double minprice) {
        this.minprice = minprice;
    }

    public Double getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(Double maxprice) {
        this.maxprice = maxprice;
    }

    public ArrayList<FilterItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<FilterItem> item) {
        this.item = item;
    }
    /*public Double getMinprice() {
        minprice:0;
        return minprice;
    }
    public Double getMaxprice(){
        maxprice:5000;
        return maxprice;
    }*/
}
