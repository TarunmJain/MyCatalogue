package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.centura_technologies.mycatalogue.Catalogue.View.IndividualProdImageAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.ViewPagerAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 24-08-2016.
 */
public class ImageViewer extends AppCompatActivity {
    //   RecyclerView recyclerView1;
    Toolbar toolbar;
    ArrayList<String> Images;
    ViewPager mpager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ViewPagerAdapter madapter_viewPager;
    ViewFlipper myViewFlipper;
    float initialXPoint, finalx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Product Images");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Images = new ArrayList<String>();
        if (StaticData.SelectedProductImage)
            Images = CatalogueDetails.image;
        else
            Images = IndividualProdImageAdapter.otherImages;
        mpager = (ViewPager) findViewById(R.id.pager);
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        for (int i = 0; i < Images.size(); i++) {
            ImageView imageView = new ImageView(ImageViewer.this);
            GenericData.setImage(Images.get(i), imageView, ImageViewer.this);
            myViewFlipper.addView(imageView);
        }
        myViewFlipper.setAutoStart(true);
        myViewFlipper.setFlipInterval(5000);
        myViewFlipper.startFlipping();
        /*madapter_viewPager = new ViewPagerAdapter(ImageViewer.this, Images);
        mpager.setAdapter(madapter_viewPager);
        final float density = getResources().getDisplayMetrics().density;
        NUM_PAGES =Images.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mpager.setCurrentItem(currentPage++, true);
               *//* if(currentPage<NUM_PAGES) {
                    mpager.setCurrentItem(currentPage++, true);
                }*//*
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 5000);*/


        //   recyclerView1=(RecyclerView)findViewById(R.id.my_recycler_view);
       /* LinearLayoutManager layoutManagerimages
                = new LinearLayoutManager(ImageViewer.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManagerimages);
        recyclerView1.setAdapter(new imageAdapter(ImageViewer.this, Images));*/
        /*mpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialXPoint = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                finalx = event.getX();
                if (initialXPoint > finalx) {
                    if (myViewFlipper.getDisplayedChild() == Images.size())
                        break;
                    myViewFlipper.showNext();
                } else {
                    if (myViewFlipper.getDisplayedChild() == 0)
                        break;
                    myViewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1 = menu.findItem(R.id.slideshow);
        register1.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
