package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

public class HTMLPage extends AppCompatActivity {
    static WebView productDetailwebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmlpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productDetailwebview = (WebView) findViewById(R.id.productDetailwebview);
        productDetailwebview.getSettings().setJavaScriptEnabled(true);
        productDetailwebview.getSettings().setDomStorageEnabled(true);
        productDetailwebview.addJavascriptInterface(new WebAppInterface(this), "Android");
        productDetailwebview.loadUrl(getIntent().getStringExtra("URL"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setVisible(false);
        MenuItem slideshow = menu.findItem(R.id.slideshow);
        slideshow.setVisible(false);
        MenuItem shortlist = menu.findItem(R.id.shortlist);
        shortlist.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}



class WebAppInterface {

    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void nextScreen(String pro_cat_id) {
        ArrayList<Products> model= new ArrayList<Products>();
        for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
            if (DB.getInitialModel().getProducts().get(i).getSectionId().matches(pro_cat_id)) {
                model.add(DB.getInitialModel().getProducts().get(i));
                break;
            }
        }
        if(model.size()>0)
        {
            StaticData.ClickedProduct=false;
            StaticData.productposition = 0;
            StaticData.SelectedProductsId = pro_cat_id;
            StaticData.Currentproducts=new ArrayList<Products>();
            StaticData.Currentproducts=model;
            ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
        }
        else
            Toast.makeText(mContext,"Product Not Avilable",Toast.LENGTH_SHORT).show();
    }



}

