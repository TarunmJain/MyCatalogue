package com.centura_technologies.mycatalogue.Login.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Order.Controller.OrdersList;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Shortlist.Controller.CustomerShortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 02-11-2016.
 */

public class IntroductionClass extends Activity {
    TextView catalogtext,shortlisttext,ordertext,settingstext,logouttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        catalogtext=(TextView)findViewById(R.id.cataloguetext);
        shortlisttext=(TextView)findViewById(R.id.shortlisttext);
        ordertext=(TextView)findViewById(R.id.orderstext);
        settingstext=(TextView)findViewById(R.id.settingstext);
        logouttext=(TextView)findViewById(R.id.logouttext);
        OnClicks();
    }

    private void OnClicks() {
        catalogtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.SelectedCategoryId = "-1";
                startActivity(new Intent(IntroductionClass.this, SectionCatalogue.class));
            }
        });
        ordertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionClass.this, OrdersList.class));
            }
        });
        settingstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionClass.this, Settings.class));
            }
        });
        shortlisttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionClass.this, CustomerShortlist.class));
            }
        });
        logouttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericData.logout(IntroductionClass.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
