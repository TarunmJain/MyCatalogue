package com.centura_technologies.mycatalogue.Shortlist.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.View.ShortlistAdapter;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class Shortlist extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView shortlistrecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortlist);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Shortlist");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shortlistrecyclerview = (RecyclerView) findViewById(R.id.shortlistrecyclerview);
        shortlistrecyclerview.setLayoutManager(new GridLayoutManager(Shortlist.this, 3));
        shortlistrecyclerview.setAdapter(new ShortlistAdapter(Shortlist.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == R.id.slideshow) {
            if(StaticData.wishlistData.size()!=0){
                startActivity(new Intent(Shortlist.this, SlideShow.class));
            }else {
                Toast.makeText(Shortlist.this,"Please add Products for Shortlist",Toast.LENGTH_SHORT).show();
            }
        }
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
