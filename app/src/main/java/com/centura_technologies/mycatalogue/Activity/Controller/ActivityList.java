package com.centura_technologies.mycatalogue.Activity.Controller;

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

import com.centura_technologies.mycatalogue.Activity.View.ActivityListAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 16-08-2016.
 */
public class ActivityList extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    FloatingActionButton fab;
    LinearLayout create_task,create_event ,create_call;
    static RecyclerView recyclerView;
    static ActivityListAdapter adapter;
    static LinearLayout filterlayout;
    static LinearLayout emptyactivitylayout;
    static ImageView filter;
    static ImageView grid_list;
    public static boolean grid_to_listflag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylist);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Activities");
        setSupportActionBar(toolbar);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);
        sharedPreferences = getSharedPreferences(GenericData.MyPref, ActivityList.this.MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.activity_recyclerview);
        filter = (ImageView) findViewById(R.id.filtericon);
        grid_list = (ImageView) findViewById(R.id.grid_listicon);
        filterlayout = (LinearLayout) findViewById(R.id.filterlayout);
        emptyactivitylayout = (LinearLayout) findViewById(R.id.emptyactivitylayout);

        recyclerView.setLayoutManager(new GridLayoutManager(ActivityList.this, 3));
        adapter = new ActivityListAdapter(ActivityList.this);

        //Fab Initialization
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mDrawerToggle = new ActionBarDrawerToggle(ActivityList.this, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
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
        GenericData.DrawerOnClicks(ActivityList.this);


        Onclicks();
        OnResume();
    }

    public static void OnResume() {
        //Calling Adapter
        if (DB.getActivities().size() != 0) {
            filter.setVisibility(View.VISIBLE);
            filterlayout.setVisibility(View.VISIBLE);
            grid_list.setVisibility(View.VISIBLE);
            emptyactivitylayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }else {
            filter.setVisibility(View.GONE);
            filterlayout.setVisibility(View.GONE);
            grid_list.setVisibility(View.GONE);
            emptyactivitylayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void Onclicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ActivityList.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_activities);
                dialog.show();
                create_task = (LinearLayout) dialog.findViewById(R.id.create_task);
                create_event = (LinearLayout) dialog.findViewById(R.id.create_event);
                create_call = (LinearLayout) dialog.findViewById(R.id.create_call);
                create_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ActivityList.this, AddTask.class));
                        dialog.cancel();
                    }
                });
                create_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ActivityList.this, AddEvent.class));
                        dialog.cancel();
                    }
                });
                create_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ActivityList.this, AddCall.class));
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
                    recyclerView.setLayoutManager(new GridLayoutManager(ActivityList.this, 3));
                    grid_list.setImageResource(R.drawable.ic_format_list_bulleted_white_24dp);
                    grid_to_listflag=false;
                } else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityList.this));
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
            GenericData.logout(ActivityList.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OnResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
