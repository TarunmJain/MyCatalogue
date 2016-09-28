package com.centura_technologies.mycatalogue.Sync.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.centura_technologies.mycatalogue.Catalogue.View.SectionlistAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Sync.View.SyncAdapter;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class Sync extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView sync_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Sync");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sync_list= (RecyclerView) findViewById(R.id.sync_list);
        sync_list.setLayoutManager(new GridLayoutManager(Sync.this, 3));
        sync_list.setAdapter(new SyncAdapter(Sync.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
