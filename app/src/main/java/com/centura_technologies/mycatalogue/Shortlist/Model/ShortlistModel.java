package com.centura_technologies.mycatalogue.Shortlist.Model;

import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.Model.SalesmanModel;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 19-10-2016.
 */
public class ShortlistModel {
    public CustomerModel customer ;
    public ArrayList<ShortlistProductModel> shortlistedproducts ;
    public SalesmanModel salesman ;
    public String ShortlistedDate;
    public String ShortlistNumber;

    public ShortlistModel(){
        customer = new CustomerModel();
        shortlistedproducts = new ArrayList<ShortlistProductModel>();
        salesman = new SalesmanModel();
        ShortlistedDate="";
        ShortlistNumber="";
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ArrayList<ShortlistProductModel> getShortlistedproducts() {
        return shortlistedproducts;
    }

    public void setShortlistedproducts(ArrayList<ShortlistProductModel> shortlistedproducts) {
        this.shortlistedproducts = shortlistedproducts;
    }

    public SalesmanModel getSalesman() {
        return salesman;
    }

    public void setSalesman(SalesmanModel salesman) {
        this.salesman = salesman;
    }

    public String getShortlistedDate() {
        return ShortlistedDate;
    }

    public void setShortlistedDate(String shortlistedDate) {
        ShortlistedDate = shortlistedDate;
    }

    public String getShortlistNumber() {
        return ShortlistNumber;
    }

    public void setShortlistNumber(String shortlistNumber) {
        ShortlistNumber = shortlistNumber;
    }
}
