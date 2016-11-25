package com.centura_technologies.mycatalogue.configuration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.centura_technologies.mycatalogue.Login.Controller.IntroductionClass;
import com.centura_technologies.mycatalogue.Login.Controller.Login;
import com.centura_technologies.mycatalogue.Login.Controller.Splash;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.ApplicationClass;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

public class SyncAll extends AppCompatActivity {
    ImageView next, previous, laterimage, syncimage;
    LinearLayout SyncYes, SyncLater;
    static Context context;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_all);
        overridePendingTransition(R.anim.fade_in_config, R.anim.fadeout);
        context = SyncAll.this;
        SyncYes = (LinearLayout) findViewById(R.id.SyncYes);
        SyncLater = (LinearLayout) findViewById(R.id.SyncLater);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        laterimage = (ImageView) findViewById(R.id.laterimage);
        syncimage = (ImageView) findViewById(R.id.syncimage);
        sharedPreferences = getSharedPreferences(GenericData.MyPref, MODE_PRIVATE);
        if (ConfigData.SYNCNOW) {
            laterimage.setImageResource(R.drawable.roundedhallow);
            syncimage.setImageResource(R.drawable.roundedsolid);
        } else {
            syncimage.setImageResource(R.drawable.roundedhallow);
            laterimage.setImageResource(R.drawable.roundedsolid);
        }

        SyncYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigData.SYNCNOW = true;
                laterimage.setImageResource(R.drawable.roundedhallow);
                syncimage.setImageResource(R.drawable.roundedsolid);
            }
        });

        SyncLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigData.SYNCNOW = false;
                syncimage.setImageResource(R.drawable.roundedhallow);
                laterimage.setImageResource(R.drawable.roundedsolid);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(GenericData.StoragePath,ConfigData.selectedStoregePath);
                editor.putString(GenericData.Configration,"Completed");
                editor.commit();
                if (ConfigData.SYNCNOW) {
                    Sync.initialapi(SyncAll.this);
                } else
                    done();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SyncAll.this, FolderInfo.class));
                finish();
            }
        });

    }

    public static void done() {
        ConfigData.SYNCNOW = false;
        StaticData.Options = "Catalogue";
        StaticData.DrawerTextDisable = "Catalogue";
        ((Activity) context).startActivity(new Intent(context, IntroductionClass.class));
        ((Activity) context).finish();
    }

}
