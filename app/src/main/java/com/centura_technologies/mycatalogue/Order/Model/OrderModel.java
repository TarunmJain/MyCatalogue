package com.centura_technologies.mycatalogue.Order.Model;

import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;

/**
 * Created by Centura on 15-10-2016.
 */
public class OrderModel {
    CustomerModel customer = new CustomerModel();
    BillingProducts billingProducts = new BillingProducts();
    String BillDate;
    String BillNumber;
    SalesmanModel salesman = new SalesmanModel();

}
