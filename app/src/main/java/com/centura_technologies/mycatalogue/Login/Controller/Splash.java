package com.centura_technologies.mycatalogue.Login.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Order.Model.SalesmanModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Sync.Controller.SyncClass;
import com.centura_technologies.mycatalogue.configuration.DataVersion;
import com.centura_technologies.mycatalogue.configuration.StorageConfiguration;
import com.google.gson.Gson;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class Splash extends Activity {
    static SharedPreferences sharedPreferences;
    static TextView info;
    static ProgressBar progressBar;
    public static final String MyPref = "MyPref";
    public static final int SPLASH_DISPLAY_LENGTH = 1000;
    DbHelper db;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        info = (TextView) findViewById(R.id.info);
        GenericData.requestStorage(Splash.this);
        db = new DbHelper(Splash.this);
        db.loadinitialmodel();
        db.loadcustomers();
        gson= new Gson();
        sharedPreferences = this.getSharedPreferences(GenericData.MyPref, this.MODE_PRIVATE);
        ConfigData.selectedStoregePath=sharedPreferences.getString(GenericData.Sp_StoragePath,"");
        ConfigData.selectedStoregelocation=sharedPreferences.getString(GenericData.Sp_StorageLoaction,"");
        ConfigData.selectedStoregefolder=sharedPreferences.getString(GenericData.Sp_StorageFolder,"");
        StaticData.CurrentSalesMan=gson.fromJson(sharedPreferences.getString(GenericData.Sp_Userdata,""), SalesmanModel.class);
        DataVersion.SectionVersion=Integer.parseInt(sharedPreferences.getString(GenericData.Sp_SectionVersion,"0"));
        String asd=sharedPreferences.getString(GenericData.Sp_ProductVersion,"0");
        DataVersion.ProductVersion=Integer.parseInt(sharedPreferences.getString(GenericData.Sp_ProductVersion,"0"));
        DataVersion.CategoryVersion=Integer.parseInt(sharedPreferences.getString(GenericData.Sp_CategoryVersion,"0"));
        DataVersion.SectionVersion=Integer.parseInt(sharedPreferences.getString(GenericData.Sp_SectionVersion,"0"));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /*if (sharedPreferences.getString(GenericData.Configration, "").matches("Completed")) {
            ConfigData.selectedStoregePath=sharedPreferences.getString(GenericData.StoragePath,"");
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                     if (sharedPreferences.getString(GenericData.Sp_Status, "").matches("LoggedIn")) {
                            StaticData.Options = "Catalogue";
                            StaticData.DrawerTextDisable = "Catalogue";
                            startActivity(new Intent(Splash.this, IntroductionClass.class));
                        } else {
                            Intent intent =new Intent(Splash.this, Login.class);
                        startActivity(intent);
                    }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
