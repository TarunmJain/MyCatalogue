package com.centura_technologies.mycatalogue.Support.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Support.ApplicationClass;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences = ApplicationClass.sharedPreferences;
    public static final String DATABASE_NAME = "MyCatalogue.db";
    private String StoreCode_var;
    Gson gson = new Gson();
    ContentValues contentValues = new ContentValues();
    public static String TableName = "TableName";
    public static String Data = "Data";
    public static String InitialData = "InitialData";

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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(image, path);
        editor.commit();
    }

    public String returnImage(String path) {
        /*SQLiteDatabase db = this.getReadableDatabase();
        String pathtoimage="";
        Cursor res =  db.rawQuery("select * from " + Table_Images + " where ImageURL = ?", new String[]{path});
        if(res.getCount()>0)
        while(res.isAfterLast() == false){
            pathtoimage=(res.getString(1));
            res.moveToNext();
        }*/
        String data = sharedPreferences.getString(path, "");
        return data;
    }
    private void saveSections(){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Sections");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getSections()).getAsJsonArray().toString());
        db.delete(this.InitialData,"TableName=?",new String[]{"Sections"});
        db.insert(this.InitialData, null, contentValues);

    }
    private void saveCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Categories");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getCategories()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Categories"});
        db.insert(this.InitialData, null, contentValues);
    }
    private void saveproducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Products");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getProducts()).getAsJsonArray().toString());
        db.delete(this.InitialData,"TableName=?",new String[]{"Products"});
        db.insert(this.InitialData, null, contentValues);
    }
    private void savecollections(){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Collections");
        contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getCollections()).getAsJsonArray().toString());
        db.delete(this.InitialData,"TableName=?",new String[]{"Collections"});
        db.insert(this.InitialData, null, contentValues);
    }
    public void saveinitialmodel() {
        saveSections();
        saveCategories();
        saveproducts();
        savecollections();
    }

    private void loadSections(InitialModel initialModel){
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where "+this.TableName+"=?",new String[]{"Sections"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Sections>>(){}.getType();
            ArrayList<Sections> sec=new ArrayList<Sections>();
            sec=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(),listType);
            initialModel.setSections(sec);
            res.moveToNext();
        }
    }
    private void loadCategories(InitialModel initialModel){
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where "+this.TableName+"=?",new String[]{"Categories"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setCategories(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Categories>>(){}.getType();
            ArrayList<Categories> cat=new ArrayList<Categories>();
            cat=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(),listType);
            initialModel.setCategories(cat);
            res.moveToNext();
        }
    }
    private void loadproducts(InitialModel initialModel){
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where "+this.TableName+"=?",new String[]{"Products"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Type listType = new TypeToken<ArrayList<Products>>(){}.getType();
            ArrayList<Products> temp=new ArrayList<Products>();
            temp=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(),listType);
            initialModel.setProducts(temp);
           //String test=initialModel.getProducts().get(0).getTitle();
            res.moveToNext();
        }
    }

    private void loadcollections(InitialModel initialModel){
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where "+this.TableName+"=?",new String[]{"Collections"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Type listType = new TypeToken<ArrayList<CollectionModel>>(){}.getType();
            ArrayList<CollectionModel> temp=new ArrayList<CollectionModel>();
            temp=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(),listType);
            initialModel.setCollections(temp);
            //String test=initialModel.getProducts().get(0).getTitle();
            res.moveToNext();
        }
    }

    public  void loadinitialmodel() {
        InitialModel initialModel=new InitialModel();
        loadSections(initialModel);
        loadCategories(initialModel);
        loadproducts(initialModel);
        loadcollections(initialModel);
        DB.setInitialModel(initialModel);
    }
}
