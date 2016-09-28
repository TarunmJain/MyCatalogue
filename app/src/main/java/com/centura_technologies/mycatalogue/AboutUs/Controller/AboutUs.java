package com.centura_technologies.mycatalogue.AboutUs.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.BuildConfig;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 18-09-2016.
 */
public class AboutUs extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    TextView version;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);
        version=(TextView)findViewById(R.id.version);
        version.setText("VERSION "+ BuildConfig.VERSION_NAME);

        mDrawerToggle = new ActionBarDrawerToggle(AboutUs.this, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
       // GenericData.DrawerOnClicks(AboutUs.this);


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
