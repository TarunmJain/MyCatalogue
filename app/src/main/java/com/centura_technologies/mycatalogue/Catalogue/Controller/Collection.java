package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.View.CollectionAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.CollectionProductsAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;

import java.util.ArrayList;

public class Collection extends AppCompatActivity {
    RecyclerView collection_gridview;
    public static RecyclerView collectionproducts_recyclerview;
    ArrayList<CollectionModel> collectionmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collectionmodel = new ArrayList<CollectionModel>();
        for (int k = 0; k < DB.getInitialModel().getCollections().size(); k++) {
            collectionmodel.add(DB.getInitialModel().getCollections().get(k));
        }
        collection_gridview=(RecyclerView)findViewById(R.id.collection_gridview);
        collectionproducts_recyclerview=(RecyclerView)findViewById(R.id.collectionproducts_recyclerview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        collection_gridview.setLayoutManager(new LinearLayoutManager(Collection.this));
        collection_gridview.setAdapter(new CollectionProductsAdapter(Collection.this,collectionmodel));
        collectionproducts_recyclerview.setLayoutManager(new LinearLayoutManager(Collection.this,LinearLayoutManager.VERTICAL,false));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
