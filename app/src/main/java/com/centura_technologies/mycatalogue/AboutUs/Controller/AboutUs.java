package com.centura_technologies.mycatalogue.AboutUs.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        GenericData.DrawerOnClicks(AboutUs.this);


    }
}
