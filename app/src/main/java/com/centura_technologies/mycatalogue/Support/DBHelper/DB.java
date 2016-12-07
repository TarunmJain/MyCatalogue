package com.centura_technologies.mycatalogue.Support.DBHelper;

import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Shortlist.Model.ShortlistModel;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 10-08-2016.
 */
public class DB {
    private static InitialModel initialModel = new InitialModel();
    private static Sections sectionmodel = new Sections();
    private static ArrayList<Sections> sectionlist = new ArrayList<Sections>();
    private static CategoryTree tree = new CategoryTree();
    private static ArrayList<CategoryTree> treelist = new ArrayList<CategoryTree>();
    private static BillingProducts billingProducts = new BillingProducts();
    private static ArrayList<BillingProducts> billprodlist = new ArrayList<BillingProducts>();
    private static Products shortlisted = new Products();
    private static ArrayList<Products> shortlistedlist = new ArrayList<Products>();
    private static ArrayList<ShortlistProductModel> shortlistproductmodel = new ArrayList<ShortlistProductModel>();
    private static ShortlistProductModel shortlistProductobj = new ShortlistProductModel();
    private static ShortlistModel shortlistModel = new ShortlistModel();
    private static ArrayList<ShortlistModel> shortlistModels = new ArrayList<ShortlistModel>();


    public static InitialModel getInitialModel() {
        return initialModel;
    }
    public static Sections getSectionmodel() {
        return sectionmodel;
    }
    public static void setSectionmodel(Sections sectionmodel) {
        DB.sectionmodel = sectionmodel;
    }

    public static ArrayList<Sections> getSectionlist() {
        return sectionlist;
    }

    public static void setSectionlist(ArrayList<Sections> sectionlist) {DB.sectionlist = sectionlist;}

    public static Products getShortlisted() {
        return shortlisted;
    }

    public static void setShortlisted(Products shortlisted) {
        DB.shortlisted = shortlisted;
    }

    public static ArrayList<Products> getShortlistedlist() {
        return shortlistedlist;
    }

    public static CategoryTree getTree() {
        return tree;
    }

    public static void setTree(CategoryTree tree) {
        DB.tree = tree;
    }

    public static ArrayList<CategoryTree> getTreelist() {
        return treelist;
    }

    public static void setTreelist(ArrayList<CategoryTree> treelist) {
        DB.treelist = treelist;
    }

    public static BillingProducts getBillingProducts() {
        return billingProducts;
    }

    public static void setBillingProducts(BillingProducts billingProducts) {DB.billingProducts = billingProducts;}

    public static ArrayList<BillingProducts> getBillprodlist() {
        return billprodlist;
    }

    public static void setBillprodlist(ArrayList<BillingProducts> billprodlist) {DB.billprodlist = billprodlist;}

    public static ShortlistModel getShortlistModel() {
        return shortlistModel;
    }

    public static void setShortlistModel(ShortlistModel shortlistModel) {DB.shortlistModel = shortlistModel;}

    public static ArrayList<ShortlistModel> getShortlistModels() {
        return shortlistModels;
    }

    public static void setShortlistModels(ArrayList<ShortlistModel> shortlistModels) {DB.shortlistModels = shortlistModels;}

    public static void setInitialModel(InitialModel initialModel) {DB.initialModel = initialModel;}

    public static void setShortlistedlist(ArrayList<Products> shortlistedlist) {
        DB.shortlistedlist = shortlistedlist;
    }

    public static ArrayList<ShortlistProductModel> getShortlistproductmodel() {
        return shortlistproductmodel;
    }

    public static void setShortlistproductmodel(ArrayList<ShortlistProductModel> shortlistproductmodel) {
        DB.shortlistproductmodel = shortlistproductmodel;
    }

    public static ShortlistProductModel getShortlistProductobj() {
        return shortlistProductobj;
    }

    public static void setShortlistProductobj(ShortlistProductModel shortlistProductobj) {
        DB.shortlistProductobj = shortlistProductobj;
    }

    public static void ClearAllDBData() {
        initialModel = new InitialModel();
        sectionmodel = new Sections();
        sectionlist = new ArrayList<Sections>();
        tree = new CategoryTree();
        treelist = new ArrayList<CategoryTree>();
        billingProducts = new BillingProducts();
        billprodlist = new ArrayList<BillingProducts>();
        shortlisted = new Products();
        shortlistedlist = new ArrayList<Products>();
        shortlistModel = new ShortlistModel();
        shortlistModels = new ArrayList<ShortlistModel>();
        shortlistproductmodel=new ArrayList<ShortlistProductModel>();
        shortlistProductobj=new ShortlistProductModel();
    }
}
