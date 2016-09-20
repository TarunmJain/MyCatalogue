package com.centura_technologies.mycatalogue.Support.Apis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttributeClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.InitialModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Valuepair;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.GetImageFromUrl;
import com.centura_technologies.mycatalogue.Support.ImageCache;
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

    public static void syncinitial(final Context context) {
        db = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        //if(GenericData.NetCheck(context)&& sharedPreferences.getString(GenericData.Sp_Status,"").matches("LoggedIn")){
        initialapi(context);
        /*}else if(sharedPreferences.getString(GenericData.Sp_Status,"").matches("LoggedIn")){
            db.loadinitialmodel();
        }*/
    }

    public static void initialapi(final Context context) {
        sharedPreferences = context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
        ArrayList<Sections> model = new ArrayList<Sections>();
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("StoreCode", "92sc93");
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

    public static void syncFilters(Context mContext, ArrayList<Products> model) {
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
            ArrayList<Valuepair> Values = new ArrayList<Valuepair>();
            att.setTitle(str);
            for (AttributeClass data : allattr) {
                if (data.getAttributeName().matches(str)){
                    Valuepair tempvaluepair=new Valuepair();
                    tempvaluepair.ValueName=data.getAttributeValue();
                    Values.add(tempvaluepair);
                }
            }
            att.setValue(Values);
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
}
