package com.centura_technologies.mycatalogue.Support.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


import com.centura_technologies.mycatalogue.Catalogue.Model.AttachmentGroup;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttchmentClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttributeClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.DescriptionMenuClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Catalogue.Model.Valuepair;
import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Shortlist.Model.ShortlistModel;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.ApplicationClass;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.github.barteksc.pdfviewer.util.ArrayUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbHelper extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences = ApplicationClass.sharedPreferences;
    public static final String DATABASE_NAME = "MyCatalogue.db";
    private String StoreCode_var;
    Gson gson = new Gson();
    ContentValues contentValues = new ContentValues();
    public static String TableName = "TableName";
    public static String Data = "Data";
    public static String ImagesTable = "Images";
    public static String ImageURL = "ImageURL";
    public static String InitialData = "InitialData";
    public static String LocalURL = "LocalURL";
    public static String SectionList = "SectionList";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        StoreCode_var = sharedPreferences.getString(GenericData.Sp_StoreCode, "");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table IF NOT EXISTS InitialData " +
                        "(" +
                        "TableName text, " +
                        "Data text)"
        );

        db.execSQL(
                "create table IF NOT EXISTS Images " +
                        "(" +
                        "ImageURL text primary key, " +
                        "LocalURL text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS CartData");
        db.execSQL("DROP TABLE IF EXISTS Images");
        onCreate(db);
    }

    public void saveImage(String image, String path) {
     /*   SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ImageURL", image);
        contentValues.put("LocalURL", path);
        if(CheckImageExist(image))
        {
            db.update(Table_CartData, contentValues, "ImageURL = ? ", new String[]{image});
            refresh();
        }
        else {
            db.insert("Images", null, contentValues);
        }*/

        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.ImageURL, image);
        contentValues.put(this.LocalURL, path);
        db.delete(this.ImagesTable, "ImageURL=?", new String[]{image});
        db.insert(this.ImagesTable, null, contentValues);
        db.close();
    }

    public String returnImage(String imagepath) {
        String data = "";
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + this.ImagesTable + " where " + this.ImageURL + "=?", new String[]{imagepath});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            data = res.getString(res.getColumnIndex(this.LocalURL)).toString();
            res.moveToNext();
        }
        db.close();
        return data;
    }

    private void saveSections() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Sections");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getSections()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Sections"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Categories");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getCategories()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Categories"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveproducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Products");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getProducts()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Products"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void savecollections() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Collections");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getCollections()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Collections"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveSectionList() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "SectionsList");
        contentValues.put(this.Data, gson.toJsonTree(DB.getSectionlist()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"SectionsList"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    public void saveinitialmodel() {
        saveSections();
        saveCategories();
        saveproducts();
        savecollections();
    }

    public void savesectionlist() {
        saveSectionList();
    }


    private void loadSections(InitialModel initialModel) {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Sections"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Sections>>() {
            }.getType();
            ArrayList<Sections> sec = new ArrayList<Sections>();
            sec = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            initialModel.setSections(sec);
            res.moveToNext();
        }
        db.close();
    }

    private void loadCategories(InitialModel initialModel) {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Categories"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setCategories(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Categories>>() {
            }.getType();
            ArrayList<Categories> cat = new ArrayList<Categories>();
            cat = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            initialModel.setCategories(cat);
            res.moveToNext();
        }
        db.close();
    }

    private void loadproducts(InitialModel initialModel) {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Products"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Type listType = new TypeToken<ArrayList<Products>>() {
            }.getType();
            ArrayList<Products> temp = new ArrayList<Products>();
            temp = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            initialModel.setProducts(temp);
            //String test=initialModel.getProducts().get(0).getTitle();
            res.moveToNext();
        }
        db.close();
    }

    private void loadcollections(InitialModel initialModel) {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Collections"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Type listType = new TypeToken<ArrayList<CollectionModel>>() {
            }.getType();
            ArrayList<CollectionModel> temp = new ArrayList<CollectionModel>();
            temp = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            initialModel.setCollections(temp);
            //String test=initialModel.getProducts().get(0).getTitle();
            res.moveToNext();
        }
        db.close();
    }

    private void loadSections() {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"SectionsList"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Sections>>() {
            }.getType();
            ArrayList<Sections> sec = new ArrayList<Sections>();
            sec = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            DB.setSectionlist(sec);
            res.moveToNext();
        }
        db.close();
    }

    public void loadinitialmodel() {
        InitialModel initialModel = new InitialModel();
        loadSections(initialModel);
        loadCategories(initialModel);
        loadproducts(initialModel);
        loadcollections(initialModel);
        DB.setInitialModel(initialModel);
        loadsectionlist();
        Sync.BillingProducts();
        loadOrders();
        loadCustomerShortlist();
        ArrayList<CategoryTree> temptree = new ArrayList<CategoryTree>();
        CategoryTree model;
        String id;
        for (int i = 0; i < DB.getInitialModel().getSections().size(); i++) {
            id = DB.getInitialModel().getSections().get(i).getId();
            model = new CategoryTree();
            model.setId(DB.getInitialModel().getSections().get(i).getId());
            model.setTitle(DB.getInitialModel().getSections().get(i).getTitle());
            model.setImageUrl(DB.getInitialModel().getSections().get(i).getImageUrl());
            model.setPriority(DB.getInitialModel().getSections().get(i).getPriority());
            model.setSelected(DB.getInitialModel().getSections().get(i).isSelected());
            for (int j = 0; j < DB.getInitialModel().getCategories().size(); j++) {
                if (id.matches(DB.getInitialModel().getCategories().get(j).getSectionId())) {
                    model.categories.add(DB.getInitialModel().getCategories().get(j));
                }
            }
            temptree.add(model);
        }
        DB.setTreelist(temptree);
        RestructureAttachments();
    }

    public void RestructureAttachments() {

        ArrayList<String> AttName = new ArrayList<String>();

        for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
            for (int q = 0; q < DB.getInitialModel().getProducts().get(j).getAttachments().size(); q++) {
                String MimeString = DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getType();
                if (MimeString != null)
                    if (!MimeString.matches("")) {
                        if (MimeString.toLowerCase().contains(("image").toLowerCase()))
                            if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()) != null)
                                if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()).contains(("image").toLowerCase()))
                                    DB.getInitialModel().getProducts().get(j).getAttachments().get(q).MediaType = AttchmentClass.TYPE_IMAGE;

                        if (MimeString.toLowerCase().contains(("video").toLowerCase()))
                            if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()) != null)
                                if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()).contains(("video").toLowerCase()))
                                    DB.getInitialModel().getProducts().get(j).getAttachments().get(q).MediaType = AttchmentClass.TYPE_VEDIO;

                        if (MimeString.toLowerCase().contains(("html").toLowerCase()))
                            if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()) != null)
                                if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()).contains(("html").toLowerCase()))
                                    DB.getInitialModel().getProducts().get(j).getAttachments().get(q).MediaType = AttchmentClass.TYPE_WEB;


                        if (MimeString.toLowerCase().contains(("pdf").toLowerCase()))
                            if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()) != null)
                                if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()).contains(("pdf").toLowerCase()))
                                    DB.getInitialModel().getProducts().get(j).getAttachments().get(q).MediaType = AttchmentClass.TYPE_PDF;

                        if (MimeString.toLowerCase().contains(("Panaroma").toLowerCase()))
                            if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()) != null)
                                if (AttchmentClass.getMimeType(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getAttachmentUrl()).contains(("image").toLowerCase()))
                                    DB.getInitialModel().getProducts().get(j).getAttachments().get(q).MediaType = AttchmentClass.TYPE_Panorama;
                    }


                if (DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getGroup() != null)
                    if (!DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getGroup().matches(""))
                        AttName.add(DB.getInitialModel().getProducts().get(j).getAttachments().get(q).getGroup());
            }

        }
        Set<String> attrname = new HashSet<String>(AttName);
        for (int productposition = 0; productposition < DB.getInitialModel().getProducts().size(); productposition++) {
            boolean groupfound = false;
            ArrayList<AttachmentGroup> allgroups = new ArrayList<AttachmentGroup>();
            for (String groupname : attrname) {
                boolean foundattachment = false;
                AttachmentGroup tempAttachmentTree = new AttachmentGroup();
                tempAttachmentTree.setGroupTitle(groupname);
                for (int attachmentposition = 0; attachmentposition < DB.getInitialModel().getProducts().get(productposition).getAttachments().size(); attachmentposition++) {
                    if (DB.getInitialModel().getProducts().get(productposition).getAttachments().get(attachmentposition).getGroup().toLowerCase().matches(groupname.toLowerCase()) && DB.getInitialModel().getProducts().get(productposition).getAttachments().get(attachmentposition).MediaType != -1) {
                        foundattachment = true;
                        tempAttachmentTree.attchments.add(DB.getInitialModel().getProducts().get(productposition).getAttachments().get(attachmentposition));
                    }
                }
                if (foundattachment) {
                    allgroups.add(tempAttachmentTree);
                    groupfound = true;
                }
            }
            if (groupfound)
                DB.getInitialModel().getProducts().get(productposition).setAttachementTree(allgroups);
        }




           /* for (int productposition=0; productposition< DB.getInitialModel().getProducts().size();productposition++) {
            for (AttchmentClass attchment : DB.getInitialModel().getProducts().get(productposition).getAttachments()) {
                boolean found = false;
                for (AttachmentGroup Group : DB.getInitialModel().getProducts().get(productposition).getAttachementTree())
                    if (attchment.Group.toLowerCase().matches(Group.GroupTitle.toLowerCase())) {
                        found = true;
                        break;
                    }
                if (!found)
                    DB.getInitialModel().getProducts().get(productposition).AttachementTree.add(new AttachmentGroup());
            }
        }
        for (int productposition=0; productposition< DB.getInitialModel().getProducts().size();productposition++) {
            for (int grpposition=0;grpposition< DB.getInitialModel().getProducts().get(productposition).getAttachementTree().size();grpposition++)
                for (int attachmentposition=0;attachmentposition< DB.getInitialModel().getProducts().get(productposition).getAttachments().size();attachmentposition++)
                    if (DB.getInitialModel().getProducts().get(productposition).getAttachementTree().get(grpposition).GroupTitle.toLowerCase().matches(DB.getInitialModel().getProducts().get(productposition).getAttachments().get(attachmentposition).Group.toLowerCase()))
                        DB.getInitialModel().getProducts().get(productposition).getAttachementTree().get(grpposition).attchments.add(DB.getInitialModel().getProducts().get(productposition).getAttachments().get(attachmentposition));
        }*/
    }


    public void loadsectionlist() {
        loadSections();
    }

    public void saveOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Orders");
        contentValues.put(this.Data, gson.toJsonTree(StaticData.orders).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Orders"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    public void saveShortlisted() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Shortlists");
        contentValues.put(this.Data, gson.toJsonTree(DB.getShortlistModels()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Shortlists"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    public void loadOrders() {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Orders"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<OrderModel>>() {
            }.getType();
            StaticData.orders = new ArrayList<OrderModel>();
            StaticData.orders = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            res.moveToNext();
        }
        db.close();
    }

    public void loadCustomerShortlist() {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Shortlists"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<ShortlistModel>>() {
            }.getType();
            ArrayList<ShortlistModel> shortlistModelArrayList = new ArrayList<ShortlistModel>();
            shortlistModelArrayList = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            DB.setShortlistModels(shortlistModelArrayList);
            res.moveToNext();
        }
        db.close();
    }

    public void ClearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, null, null);
        db.delete(this.ImagesTable, null, null);
        db.close();
        File dir = (Environment.getExternalStoragePublicDirectory("/MyCatalogueLocalData"));
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }
}
