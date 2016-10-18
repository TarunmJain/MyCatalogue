package com.centura_technologies.mycatalogue.Support.Apis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttributeClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Valuepair;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.GetImageFromUrl;
import com.centura_technologies.mycatalogue.Support.ImageCache;
import com.centura_technologies.mycatalogue.Sync.Model.SyncSectionsClass;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Centura User1 on 22-08-2016.
 */
public class Sync {

    static Gson gson = new Gson();
    static SharedPreferences sharedPreferences;
    static DbHelper db;

    public static ArrayList<String> SelectedSectionSync = new ArrayList<>();
    public static boolean SyncCollections = false;

    public static void syncinitial(final Context context) {
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        //if(GenericData.NetCheck(context)&& sharedPreferences.getString(GenericData.Sp_Status,"").matches("LoggedIn")){
        initialapi(context);
        /*}else if(sharedPreferences.getString(GenericData.Sp_Status,"").matches("LoggedIn")){
            db.loadinitialmodel();
        }*/
    }

    public static void initialapi(final Context context) {
        db = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        ArrayList<Sections> model = new ArrayList<Sections>();
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("StoreCode", sharedPreferences.getString(GenericData.Sp_StoreCode, ""));
        //GenericData.ShowDialog(context, "Loading...", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.Initial, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //GenericData.ShowDialog(context,"Loading...",false);
                if (response.optString("IsSuccess").matches("true")) {
                    try {
                        InitialModel temp = new InitialModel();
                        JSONObject jsonObject = response.getJSONObject("Data");
                        temp = gson.fromJson(jsonObject.toString(), InitialModel.class);
                        for (int i = 0; i < temp.getProducts().size(); i++) {
                            ImageCache param = new ImageCache(temp.getProducts().get(i).getImageUrl(), temp.getProducts().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                            getImageFromUrl.execute(param);
                            for (int d = 0; d < temp.getProducts().get(i).getProductImages().size(); d++) {
                                param = new ImageCache(temp.getProducts().get(i).getProductImages().get(d), temp.getProducts().get(i).getId() + d + "", context);
                                getImageFromUrl = new GetImageFromUrl();
                                getImageFromUrl.execute(param);
                            }
                        }
                        for (int i = 0; i < temp.getSections().size(); i++) {
                            ImageCache param1 = new ImageCache(temp.getSections().get(i).getImageUrl(), temp.getSections().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl1 = new GetImageFromUrl();
                            getImageFromUrl1.execute(param1);
                        }
                        for (int i = 0; i < temp.getCategories().size(); i++) {
                            ImageCache param2 = new ImageCache(temp.getCategories().get(i).getImageUrl(), temp.getCategories().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl2 = new GetImageFromUrl();
                            getImageFromUrl2.execute(param2);
                        }
                        for (int j = 0; j < temp.getCollections().size(); j++) {
                            ImageCache param3 = new ImageCache(temp.getCollections().get(j).getImageUrl(), temp.getCollections().get(j).getId(), context);
                            GetImageFromUrl getImageFromUrl3 = new GetImageFromUrl();
                            getImageFromUrl3.execute(param3);
                        }
                        GenericData.imagesChached = true;
                        DB.setInitialModel(temp);
                        db.saveinitialmodel();
                        db.loadinitialmodel();


                        //ArrayList<Products> tempprod=DB.getInitialModel().getProducts();
                        /*ArrayList<Sections> tempsec=DB.getInitialModel().getSections();
                        ArrayList<Categories> tempcat=DB.getInitialModel().getCategories();
                        ArrayList<Products> tempprod=DB.getInitialModel().getProducts();
                        ArrayList<Categories> tempcats=DB.getInitialModel().getCategories();*/
                        //DB.setCataloguemodel(gson.fromJson(jsonObject.toString(), Sections.class));

                        /*temp=gson.fromJson(jsonObject.toString(), Sections.class);
                        DB.getCategoryData().add(temp);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //GenericData.ShowDialog(context,"Loading...",false);
                Log.d("Error", "Error");
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void SyncSectionList(final Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(GenericData.MyPref, mContext.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("StoreCode", sharedPreferences.getString(GenericData.Sp_StoreCode, ""));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.SectionList, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.optString("IsSuccess").matches("true")) {
                    ArrayList<Sections> model = new ArrayList<Sections>();
                    InitialModel temp = new InitialModel();
                    try {
                        JSONObject jsonObject = response.getJSONObject("Data");
                        temp = gson.fromJson(jsonObject.toString(), InitialModel.class);
                        model = (temp.getSections());
                        for (int i = 0; i < model.size(); i++) {
                            ImageCache param1 = new ImageCache(model.get(i).getImageUrl(), model.get(i).getId(), mContext);
                            GetImageFromUrl getImageFromUrl1 = new GetImageFromUrl();
                            getImageFromUrl1.execute(param1);
                        }
                        GenericData.imagesChached = true;
                        DB.setSectionlist(model);
                        db = new DbHelper(mContext);
                        db.savesectionlist();
                        db.loadsectionlist();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error", "Error");
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void syncFilters(Context mContext, ArrayList<Products> model) {
        StaticData.filter = "";
        ArrayList<Products> prod = new ArrayList<Products>();
        for (int j = 0; j < model.size(); j++) {
            prod.add(model.get(j));
        }
        Double max = getMax(prod);

        StaticData.filtermodel.setMaxprice(max);
        StaticData.filtermodel.setMinprice(0.0);

        StaticData.filtermodel.item = new ArrayList<FilterItem>();
        ArrayList<FilterItem> finalitems = new ArrayList<FilterItem>();

        //ArrayList<AttributesList> attribute;
        //ArrayList<attributesmodel> attributes;

        ArrayList<AttributeClass> allattr = new ArrayList<AttributeClass>();
        ArrayList<FilterItem> list = new ArrayList<FilterItem>();
        ArrayList<String> AttName = new ArrayList<String>();

        list.clear();
        for (int j = 0; j < prod.size(); j++) {
            for (int q = 0; q < prod.get(j).getAttributes().size(); q++) {
                allattr.add(prod.get(j).getAttributes().get(q));
                AttName.add(prod.get(j).getAttributes().get(q).getAttributeName());
            }
        }
        Set<String> attrname = new HashSet<String>(AttName);
        for (String str : attrname) {
            FilterItem att = new FilterItem();
            ArrayList<String> Values = new ArrayList<String>();
            att.setTitle(str);
            for (AttributeClass data : allattr) {
                if (data.getAttributeName().matches(str)) {
                    String tempvaluepair = data.getAttributeValue();
                    if (!tempvaluepair.matches(""))
                        Values.add(tempvaluepair);
                }
            }
            Set<String> attrval = new HashSet<String>(Values);
            ArrayList<Valuepair> finalValues = new ArrayList<Valuepair>();
            for (String tempvalue : attrval) {
                Valuepair tempvalpair = new Valuepair();
                tempvalpair.ValueName = tempvalue;
                finalValues.add(tempvalpair);
            }
            att.setValue(finalValues);
            list.add(att);
        }


       /* FilterItem temp = new FilterItem();
        Valuepair choices = new Valuepair();
        temp.Value = new ArrayList<Valuepair>();

        temp.setTitle("Brand");
        choices.ValueName = "Hindware";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Kohler";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Parryware";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Jaguar";
        temp.Value.add(choices);
        choices = new Valuepair();
        finalitems.add(temp);


        temp = new FilterItem();
        temp.Value = new ArrayList<Valuepair>();
        temp.setTitle("Color");
        choices.ValueName = "White";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Black";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Light Blue";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Almond";
        temp.Value.add(choices);
        choices = new Valuepair();
        finalitems.add(temp);


        temp = new FilterItem();

        temp.Value = new ArrayList<Valuepair>();
        temp.setTitle("Material");
        choices.ValueName = "Ceramic";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Acrylic";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Fiberglass";
        temp.Value.add(choices);
        choices = new Valuepair();

        choices.ValueName = "Porcelain on Steel";
        temp.Value.add(choices);
        finalitems.add(temp);*/
        StaticData.filtermodel.setItem(list);

    }

    public static Double getMax(ArrayList<Products> list) {
        Double max = Double.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSellingPrice() > max) {
                max = list.get(i).getSellingPrice();
            }
        }
        return max;
    }

    public static void syncroniseCollections(final Context context) {
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        ArrayList<Sections> model = new ArrayList<Sections>();
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("StoreCode", sharedPreferences.getString(GenericData.Sp_StoreCode, ""));
        //GenericData.ShowDialog(context, "Loading...", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.collectionlist, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //GenericData.ShowDialog(context,"Loading...",false);
                if (response.optString("IsSuccess").matches("true")) {
                    try {
                        InitialModel temp = new InitialModel();
                        JSONObject jsonObject = response.getJSONObject("Data");
                        temp = gson.fromJson(jsonObject.toString(), InitialModel.class);
                        for (int i = 0; i < temp.getProducts().size(); i++) {
                            ImageCache param = new ImageCache(temp.getProducts().get(i).getImageUrl(), temp.getProducts().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                            getImageFromUrl.execute(param);
                            for (int d = 0; d < temp.getProducts().get(i).getProductImages().size(); d++) {
                                param = new ImageCache(temp.getProducts().get(i).getProductImages().get(d), temp.getProducts().get(i).getId() + d + "", context);
                                getImageFromUrl = new GetImageFromUrl();
                                getImageFromUrl.execute(param);
                            }
                        }

                        for (int i = 0; i < temp.getCategories().size(); i++) {
                            ImageCache param = new ImageCache(temp.getCategories().get(i).getImageUrl(), temp.getCategories().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                            getImageFromUrl.execute(param);
                        }

                        for (int j = 0; j < temp.getCollections().size(); j++) {
                            ImageCache param3 = new ImageCache(temp.getCollections().get(j).getImageUrl(), temp.getCollections().get(j).getId(), context);
                            GetImageFromUrl getImageFromUrl3 = new GetImageFromUrl();
                            getImageFromUrl3.execute(param3);
                        }
                        GenericData.imagesChached = true;
                        updateInitialmodel(temp, context);
                        if (SelectedSectionSync.size() > 0)
                            syncroniseSections(context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //GenericData.ShowDialog(context,"Loading...",false);
                Log.d("Error", "Error");
            }
        });
        queue.add(jsonObjectRequest);
    }


    public static void syncroniseSections(final Context context) {
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        ArrayList<Sections> model = new ArrayList<Sections>();
        RequestQueue queue = Volley.newRequestQueue(context);
        SyncSectionsClass obj = new SyncSectionsClass();
        obj.StoreCode = sharedPreferences.getString(GenericData.Sp_StoreCode, "");
        obj.SectionIds = SelectedSectionSync;
        /*params.put("StoreCode", sharedPreferences.getString(GenericData.Sp_StoreCode, ""));
        params.put("SectionIds", String.valueOf(SelectedSectionSync));*/
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.sectiondata, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //GenericData.ShowDialog(context,"Loading...",false);
                if (response.optString("IsSuccess").matches("true")) {
                    try {
                        InitialModel temp = new InitialModel();
                        JSONObject jsonObject = response.getJSONObject("Data");
                        temp = gson.fromJson(jsonObject.toString(), InitialModel.class);
                        for (String id : SelectedSectionSync) {
                            for (Sections section : DB.getSectionlist()) {
                                if(section.getId().matches(id)){
                                    temp.getSections().add(section);
                                    break;
                                }
                            }
                        }

                        for (int i = 0; i < temp.getCategories().size(); i++) {
                            ImageCache param = new ImageCache(temp.getCategories().get(i).getImageUrl(), temp.getCategories().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                            getImageFromUrl.execute(param);
                        }

                        for (int i = 0; i < temp.getProducts().size(); i++) {
                            ImageCache param = new ImageCache(temp.getProducts().get(i).getImageUrl(), temp.getProducts().get(i).getId(), context);
                            GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                            getImageFromUrl.execute(param);
                            for (int d = 0; d < temp.getProducts().get(i).getProductImages().size(); d++) {
                                param = new ImageCache(temp.getProducts().get(i).getProductImages().get(d), temp.getProducts().get(i).getId() + d + "", context);
                                getImageFromUrl = new GetImageFromUrl();
                                getImageFromUrl.execute(param);
                            }
                        }

                        for (int j = 0; j < temp.getCollections().size(); j++) {
                            ImageCache param3 = new ImageCache(temp.getCollections().get(j).getImageUrl(), temp.getCollections().get(j).getId(), context);
                            GetImageFromUrl getImageFromUrl3 = new GetImageFromUrl();
                            getImageFromUrl3.execute(param3);
                        }
                        GenericData.imagesChached = true;
                        updateInitialmodel(temp, context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //GenericData.ShowDialog(context,"Loading...",false);
                Log.d("Error", "Error");
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void updateInitialmodel(InitialModel temp, Context context) {

        for (CollectionModel newcollection : temp.getCollections()) {
            boolean matched = false;
            for (int i = 0; i < DB.getInitialModel().getCollections().size(); i++) {
                CollectionModel maincollection = new CollectionModel();
                maincollection = DB.getInitialModel().getCollections().get(i);
                if (newcollection.getId().matches(maincollection.getId())) {
                    matched = true;
                    DB.getInitialModel().getCollections().remove(maincollection);
                    DB.getInitialModel().getCollections().add(i, newcollection);
                    break;
                }
            }
            if (!matched)
                DB.getInitialModel().getCollections().add(newcollection);
        }

        for (Products newproduct : temp.getProducts()) {
            boolean matched = false;
            for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
                Products mainproduct = new Products();
                mainproduct = DB.getInitialModel().getProducts().get(i);
                if (newproduct.getId().matches(mainproduct.getId())) {
                    matched = true;
                    DB.getInitialModel().getProducts().remove(mainproduct);
                    DB.getInitialModel().getProducts().add(i, newproduct);
                    break;
                }
            }
            if (!matched)
                DB.getInitialModel().getProducts().add(newproduct);
        }

        for (Sections newSection : temp.getSections()) {
            boolean matched = false;
            for (int i = 0; i < DB.getInitialModel().getSections().size(); i++) {
                Sections mainSection = new Sections();
                mainSection = DB.getInitialModel().getSections().get(i);
                if (newSection.getId().matches(mainSection.getId())) {
                    matched = true;
                    DB.getInitialModel().getSections().remove(mainSection);
                    DB.getInitialModel().getSections().add(i, newSection);
                    break;
                }
            }
            if (!matched)
                DB.getInitialModel().getSections().add(newSection);
        }

        for (Categories newCategory : temp.getCategories()) {
            boolean matched = false;
            for (int i = 0; i < DB.getInitialModel().getCategories().size(); i++) {
                Categories mainCategory = new Categories();
                mainCategory = DB.getInitialModel().getCategories().get(i);
                if (newCategory.getId().matches(mainCategory.getId())) {
                    matched = true;
                    DB.getInitialModel().getCategories().remove(mainCategory);
                    DB.getInitialModel().getCategories().add(i, newCategory);
                    break;
                }
            }
            if (!matched) DB.getInitialModel().getCategories().add(newCategory);
        }
        db = new DbHelper(context);
        db.saveinitialmodel();
        db.loadinitialmodel();
        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
    }

    public static void BillingProducts(){
        ArrayList<BillingProducts> billprodlist;
        BillingProducts billprod;
        billprodlist=new ArrayList<BillingProducts>();
        for(int i=0;i<DB.getInitialModel().getProducts().size();i++){
            billprod=new BillingProducts();
            billprod.setId(DB.getInitialModel().getProducts().get(i).getId());
            billprod.setTitle(DB.getInitialModel().getProducts().get(i).getTitle());
            billprod.setDescription(DB.getInitialModel().getProducts().get(i).getDescription());
            billprod.setSectionId(DB.getInitialModel().getProducts().get(i).getSectionId());
            billprod.setCategoryId(DB.getInitialModel().getProducts().get(i).getCategoryId());
            billprod.setSKU(DB.getInitialModel().getProducts().get(i).getSKU());
            billprod.setBarCode(DB.getInitialModel().getProducts().get(i).getBarCode());
            billprod.setImageUrl(DB.getInitialModel().getProducts().get(i).getImageUrl());
            billprod.setVideoUrl(DB.getInitialModel().getProducts().get(i).getVideoUrl());
            billprod.setPdfUrl(DB.getInitialModel().getProducts().get(i).getPdfUrl());
            billprod.setMRP(DB.getInitialModel().getProducts().get(i).getMRP());
            billprod.setAmount(0.0);
            billprod.setQuantity(0);
            billprod.setPrice(DB.getInitialModel().getProducts().get(i).getSellingPrice());
            billprod.setSellingPrice(DB.getInitialModel().getProducts().get(i).getSellingPrice());
            billprod.setTags(DB.getInitialModel().getProducts().get(i).getTags());
            billprod.setStatus(DB.getInitialModel().getProducts().get(i).getStatus());
            billprod.setWeight(DB.getInitialModel().getProducts().get(i).getWeight());
            billprod.setWishList(DB.getInitialModel().getProducts().get(i).isWishList());
            billprod.setSelectedVarient(DB.getInitialModel().getProducts().get(i).getSelectedVarient());
            billprod.setProductImages(DB.getInitialModel().getProducts().get(i).getProductImages());
            billprod.setAttributes(DB.getInitialModel().getProducts().get(i).getAttributes());
            billprod.setVariants(DB.getInitialModel().getProducts().get(i).getVariants());
            billprodlist.add(billprod);
        }
        DB.setBillprodlist(billprodlist);
    }
}
