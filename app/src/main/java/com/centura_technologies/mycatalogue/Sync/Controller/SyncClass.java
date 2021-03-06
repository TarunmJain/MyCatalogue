package com.centura_technologies.mycatalogue.Sync.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Sync.View.SyncAdapter;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class SyncClass extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView sync_list;
    TextView appysync, cancel,toolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        Sync.SyncSectionList(SyncClass.this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        appysync = (TextView) findViewById(R.id.appysync);
        cancel = (TextView) findViewById(R.id.cancel_sync);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("Sync");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        sync_list = (RecyclerView) findViewById(R.id.sync_list);
        sync_list.setLayoutManager(new GridLayoutManager(SyncClass.this, 3));
        sync_list.setAdapter(new SyncAdapter(SyncClass.this));
        setOnClinks();
    }

    private void setOnClinks() {
        appysync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sync.SyncCollections)
                    Sync.syncroniseCollections(SyncClass.this);
                else
                    Sync.syncroniseSections(SyncClass.this);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
