package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.centura_technologies.mycatalogue.Catalogue.View.SectionCatalogueAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionCatalogue extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectioncatalogue);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Sections");
        setSupportActionBar(toolbar);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);
        sharedPreferences = getSharedPreferences(GenericData.MyPref, SectionCatalogue.this.MODE_PRIVATE);

        mDrawerToggle = new ActionBarDrawerToggle(SectionCatalogue.this, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
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

        recyclerView=(RecyclerView)findViewById(R.id.section_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(SectionCatalogue.this,3));
        recyclerView.setAdapter(new SectionCatalogueAdapter(SectionCatalogue.this, DB.getInitialModel().getSections()));

        GenericData.DrawerOnClicks(SectionCatalogue.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            GenericData.logout(SectionCatalogue.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
