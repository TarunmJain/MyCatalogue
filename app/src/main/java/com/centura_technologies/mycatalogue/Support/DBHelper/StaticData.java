package com.centura_technologies.mycatalogue.Support.DBHelper;

import android.content.Context;

import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Filter_model;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Order.Model.SalesmanModel;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class StaticData {

    public static int ApiVersion=10;
    public static String DeviceId="";
    public static boolean editlead=false;
    public static boolean edittask=false;
    public static boolean editevent=false;
    public static boolean editcall=false;
    public static boolean ProductsInList=false;
    public static boolean ProductsInGrid=false;
    public static Filter_model filtermodel=new Filter_model();
    public static ArrayList<Products> wishlistData=new ArrayList<Products>();
    public static String filter="";
    public static int productposition=-1;
    public static Context context;
    public static int position=0;
    public static String SelectedSectionId="";
    public static String SelectedCategoryId="";
    public static String SelectedProductsId="";
    public static ArrayList<String> SelectedCollectionProducts;
    public static boolean SelectedProductImage=false;           //CatalogueDetails Image
    public static boolean SelectedSection=false;                //Clicked Section
    public static boolean ClickedProduct=false;
    public static boolean SelectedCollection=false;
    public static boolean Shortlisted=false;
    public static boolean shortlistedorders=false;
    public static String Options="";

    public static SalesmanModel CurrentSalesMan = new SalesmanModel();
    public static ArrayList<CustomerModel> Customers = new ArrayList<CustomerModel>();
    public static ArrayList<OrderModel> orders=new ArrayList<OrderModel>();
}
