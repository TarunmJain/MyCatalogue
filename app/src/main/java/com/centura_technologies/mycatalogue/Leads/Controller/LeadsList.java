package com.centura_technologies.mycatalogue.Leads.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Leads.View.LeadsAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 09-08-2016.
 */
public class LeadsList extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    LeadsAdapter adapter;
    FloatingActionButton fab;
    LinearLayout addleads, importleads,emptyleadslayout;
    RelativeLayout filterlayout;
    ImageView filter, grid_list;
    public static boolean grid_to_listflag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Leads");
        setSupportActionBar(toolbar);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);
        sharedPreferences = getSharedPreferences(GenericData.MyPref, LeadsList.this.MODE_PRIVATE);

        //Recyclerview Initialization
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        filter = (ImageView) findViewById(R.id.filtericon);
        grid_list = (ImageView) findViewById(R.id.grid_listicon);
        filterlayout = (RelativeLayout) findViewById(R.id.filterlayout);
        emptyleadslayout = (LinearLayout) findViewById(R.id.emptyleadslayout);


        recyclerView.setLayoutManager(new GridLayoutManager(LeadsList.this, 3));
        adapter = new LeadsAdapter(LeadsList.this);

        //Fab Initialization
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mDrawerToggle = new ActionBarDrawerToggle(LeadsList.this, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
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

        //Onlicks Of Drawer
        GenericData.DrawerOnClicks(LeadsList.this);

        //Calling Adapter
        if (DB.getLeads().size() != 0) {
            filter.setVisibility(View.VISIBLE);
            filterlayout.setVisibility(View.VISIBLE);
            grid_list.setVisibility(View.VISIBLE);
            emptyleadslayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }else{
            filter.setVisibility(View.GONE);
            filterlayout.setVisibility(View.GONE);
            grid_list.setVisibility(View.GONE);
            emptyleadslayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        //Fab Button Onclicks
        Onclicks();
    }

    private void Onclicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LeadsList.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_leads);
                dialog.show();
                addleads = (LinearLayout) dialog.findViewById(R.id.addleads);
                importleads = (LinearLayout) dialog.findViewById(R.id.importleads);
                addleads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LeadsList.this, AddLeads.class));
                        dialog.cancel();
                    }
                });
                importleads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(LeadsList.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterlayout.getVisibility() == View.VISIBLE)
                    filterlayout.setVisibility(View.GONE);
                else
                    filterlayout.setVisibility(View.VISIBLE);
            }
        });

        grid_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grid_to_listflag) {
                    recyclerView.setLayoutManager(new GridLayoutManager(LeadsList.this, 3));
                    grid_list.setImageResource(R.drawable.ic_format_list_bulleted_white_24dp);
                    grid_to_listflag=false;
                } else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(LeadsList.this));
                    grid_list.setImageResource(R.drawable.ic_view_grid_white_24dp);
                    grid_to_listflag=true;
                }
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            GenericData.logout(LeadsList.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DB.getLeads().size() != 0) {
            filter.setVisibility(View.VISIBLE);
            filterlayout.setVisibility(View.VISIBLE);
            grid_list.setVisibility(View.VISIBLE);
            emptyleadslayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }else{
            filter.setVisibility(View.GONE);
            filterlayout.setVisibility(View.GONE);
            grid_list.setVisibility(View.GONE);
            emptyleadslayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
