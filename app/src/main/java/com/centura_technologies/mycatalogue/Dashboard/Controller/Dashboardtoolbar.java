package com.centura_technologies.mycatalogue.Dashboard.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;

/**
 * Created by Centura User1 on 19-08-2016.
 */
public class Dashboardtoolbar {
    private static AppCompatActivity activity;

    public static void set(final Context mContext, final String tittle, Boolean Includedrawer) {
        activity = (AppCompatActivity) mContext;
        TextView Tittle;
        final ImageView logout;
        Toolbar toolbar;
        final DrawerLayout Drawer;
        final RelativeLayout options;
        ActionBarDrawerToggle mDrawerToggle;
        final SharedPreferences sharedPreferences;
        sharedPreferences = activity.getSharedPreferences(GenericData.MyPref, activity.MODE_PRIVATE);
        toolbar = (Toolbar) activity.findViewById(R.id.tool_bar);
        Drawer = (DrawerLayout) activity.findViewById(R.id.drawer);
        options = (RelativeLayout) activity.findViewById(R.id.optionlayout);
        activity.setSupportActionBar(toolbar);
        Tittle = (TextView) activity.findViewById(R.id.AppbarTittle);
        Tittle.setText(tittle);

            if (Includedrawer) {
                Drawer.openDrawer(Gravity.LEFT);
                mDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                                DrawerLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(500, 0, 0, 0);
                        options.setLayoutParams(params);
                        Drawer.setScrimColor(activity.getResources().getColor(android.R.color.transparent));
                        // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                        // open I am not going to put anything here)
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                                DrawerLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 0, 0);
                        options.setLayoutParams(params);
                        // Code here will execute once drawer is closed
                    }
                }; // Drawer Toggle Object Made

                Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
                mDrawerToggle.syncState();
            } else {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        logout = (ImageView) activity.findViewById(R.id.logoff);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setTitle("Really Exit " + sharedPreferences.getString(GenericData.Sp_StoreCode, "") + "?")
                        .setMessage("Are you sure you want to exit " + sharedPreferences.getString(GenericData.Sp_StoreCode, "") + "?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                activity.moveTaskToBack(true);
                                activity.onBackPressed();
                            }
                        }).create().show();
            }
        });
    }
}
