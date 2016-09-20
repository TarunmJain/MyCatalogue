package com.centura_technologies.mycatalogue.Support;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by tarun jain on 06-01-2016.
 */
public class ApplicationClass extends Application {
   public static SharedPreferences sharedPreferences;
   public static RequestQueue mRequestQueue;
   private static Context context;
   public static JsonObjectRequest lastreq;



   @Override
   public void onCreate() {
      super.onCreate();
      context=getApplicationContext();
      sharedPreferences=context.getSharedPreferences(GenericData.MyPref, context.MODE_PRIVATE);
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
   }

   /*public static void makerequest(JsonObjectRequest req,Context context){
      GenericData.NetCheck(context);
      if(mRequestQueue!=null){
         req.setRetryPolicy(new DefaultRetryPolicy(5000,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         mRequestQueue.add(req);
      }
      else {
         makerequest(req,context);
      }
   }*/

   public static void makerequest(JsonObjectRequest req)  {
      lastreq=null;
      lastreq=req;
      GenericData.NetCheck(context);
      req.setRetryPolicy(new DefaultRetryPolicy(5000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      mRequestQueue.add(req);
   }

}
