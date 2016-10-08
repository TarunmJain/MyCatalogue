package com.centura_technologies.mycatalogue.Settings.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Sync.Controller.SyncClass;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class Settings extends AppCompatActivity {
    Toolbar toolbar;
    CardView syncall,syncsections,synccollections,others;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        syncall=(CardView)findViewById(R.id.syncall);
        syncsections=(CardView)findViewById(R.id.syncsections);
        synccollections=(CardView)findViewById(R.id.synccollections);
        others=(CardView)findViewById(R.id.others);
        OnClicks();
    }

    private void OnClicks() {
        syncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sync.initialapi(Settings.this);
                Toast.makeText(Settings.this,"Updated",Toast.LENGTH_SHORT).show();
                syncall.setClickable(false);
                syncsections.setClickable(false);
                synccollections.setClickable(false);
                others.setClickable(false);
            }
        });
        syncsections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, SyncClass.class));
                syncall.setClickable(true);
                syncsections.setClickable(true);
                synccollections.setClickable(true);
                others.setClickable(true);
            }
        });
        synccollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, SyncClass.class));
                syncall.setClickable(true);
                syncsections.setClickable(true);
                synccollections.setClickable(true);
                others.setClickable(true);
            }
        });
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
