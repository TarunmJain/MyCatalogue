package com.centura_technologies.mycatalogue.Support;

import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura on 15-10-2016.
 */
public class ApiData {

    public static void renderCustomers(){
        for (int x=1;x<21;x++)
            StaticData.Customers.add(new CustomerModel("customer no "+x));
    }
}


