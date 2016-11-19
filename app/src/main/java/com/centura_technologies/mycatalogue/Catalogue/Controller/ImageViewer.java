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
    Toolbar toolbar;
    ArrayList<String> Images;
    ViewPager mpager;
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
        Images = CatalogueDetails.image;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1 = menu.findItem(R.id.slideshow);
        register1.setVisible(false);
        MenuItem register2 = menu.findItem(R.id.shortlist);
        register2.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
