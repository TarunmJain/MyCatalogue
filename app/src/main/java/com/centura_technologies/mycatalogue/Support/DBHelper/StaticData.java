package com.centura_technologies.mycatalogue.Support.DBHelper;

import android.content.Context;

import com.centura_technologies.mycatalogue.Catalogue.Model.AttachmentGroup;
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


    public static int ApiVersion = 10;
    public static String DeviceId = "";
    public static int ViewPosition = -1;
    public static boolean editlead = false;
    public static boolean edittask = false;
    public static boolean editevent = false;
    public static boolean editcall = false;
    public static boolean ProductsInList = false;
    public static boolean ProductsInGrid = false;
    public static Filter_model filtermodel = new Filter_model();
    public static ArrayList<Products> wishlistData = new ArrayList<Products>();
    public static String filter = "";
    public static int productposition = -1;
    public static Context context;
    public static int position = 0;
    public static String SelectedSectionId = "";
    public static String SelectedCategoryId = "";
    public static String SelectedProductsId = "";
    public static ArrayList<String> SelectedCollectionProducts;
    public static boolean SelectedProductImage = false;           //CatalogueDetails Image
    public static boolean SelectedSection = false;                //Clicked Section
    public static boolean ClickedProduct = false;
    public static boolean SelectedCollection = false;
    public static boolean Shortlisted = false;
    public static boolean customershortlistedview = false;
    public static int customershortlistpos = 0;
    public static boolean vieworder = false;
    public static String Options = "";
    public static String DrawerTextDisable = "";
    public static boolean ShortlistedOrder=false;
    public static SalesmanModel CurrentSalesMan = new SalesmanModel();
    public static CustomerModel SelectedCustomers = new  CustomerModel();
    public static ArrayList<CustomerModel> TempCustomers = new ArrayList<CustomerModel>();
    public static ArrayList<OrderModel> orders = new ArrayList<OrderModel>();
    public static boolean syncall=false;
    public static boolean syncsection=false;
    public static ArrayList<Products> Currentproducts = new ArrayList<Products>();

    public static void ClearAllStaticData() {
        ViewPosition = -1;
        editlead = false;
        edittask = false;
        editevent = false;
        editcall = false;
        ProductsInList = false;
        ProductsInGrid = false;
        filtermodel = new Filter_model();
        wishlistData = new ArrayList<Products>();
        filter = "";
        productposition = -1;
        position = 0;
        SelectedSectionId = "";
        SelectedCategoryId = "";
        SelectedProductsId = "";
        SelectedCollectionProducts = new ArrayList<>();
        SelectedProductImage = false;           //CatalogueDetails Image
        SelectedSection = false;                //Clicked Section
        ClickedProduct = false;
        SelectedCollection = false;
        Shortlisted = false;
        customershortlistedview = false;
        customershortlistpos = 0;
        vieworder = false;
        Options = "";
        DrawerTextDisable = "";
        CurrentSalesMan = new SalesmanModel();

        orders = new ArrayList<OrderModel>();
    }
}
