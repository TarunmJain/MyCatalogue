package com.centura_technologies.mycatalogue.Login.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.Collection;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Order.Controller.OrdersList;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Shortlist.Controller.CustomerShortlist;
import com.centura_technologies.mycatalogue.Shortlist.Controller.SlideShow;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.test.StorageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Centura User1 on 02-11-2016.
 */

public class IntroductionClass extends Activity {
    TextView productstext, shortlisttext, ordertext, settingstext, logouttext, catalougetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        productstext = (TextView) findViewById(R.id.cataloguetext);
        shortlisttext = (TextView) findViewById(R.id.shortlisttext);
        ordertext = (TextView) findViewById(R.id.orderstext);
        settingstext = (TextView) findViewById(R.id.settingstext);
        logouttext = (TextView) findViewById(R.id.logouttext);
        catalougetext = (TextView) findViewById(R.id.collectiontext);
       /* myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        myImageList = new ArrayList<>();
        myImageList.add(R.drawable.background_1);
        myImageList.add(R.drawable.background_2);
        myImageList.add(R.drawable.background_3);
        myImageList.add(R.drawable.background_4);
        myImageList.add(R.drawable.background_5);
        myImageList.add(R.drawable.background_6);
        myImageList.add(R.drawable.background_7);
        for (int i = 0; i < myImageList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(myImageList.get(i));
            myViewFlipper.addView(imageView);
        }
        myViewFlipper.setAutoStart(true);
        myViewFlipper.setFlipInterval(8000);
        myViewFlipper.startFlipping();*/
        OnClicks();
    }

    private void OnClicks() {
        productstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DB.getInitialModel().getProducts().size() > 0) {
                    StaticData.SelectedCategoryId = "-1";
                    startActivity(new Intent(IntroductionClass.this, Catalogue.class));
                } else
                    Toast.makeText(IntroductionClass.this, "No Projects found Please Syncronise !", Toast.LENGTH_SHORT).show();

            }
        });
        catalougetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DB.getInitialModel().getCollections().size() > 0)
                    startActivity(new Intent(IntroductionClass.this, Collection.class));
                else
                    Toast.makeText(IntroductionClass.this, "No Catalougues found Please Syncronise !", Toast.LENGTH_SHORT).show();

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
