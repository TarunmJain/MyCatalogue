package com.centura_technologies.mycatalogue.Order.Model;

import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;

import java.util.ArrayList;

/**
 * Created by Centura on 15-10-2016.
 */
public class OrderModel {
    public CustomerModel customer = new CustomerModel();
    public ArrayList<BillingProducts> billingProducts = new ArrayList<BillingProducts>();
    public SalesmanModel salesman = new SalesmanModel();
    public String OrderDate;
    public String OrderNumber;
    public Double Amount;
}
