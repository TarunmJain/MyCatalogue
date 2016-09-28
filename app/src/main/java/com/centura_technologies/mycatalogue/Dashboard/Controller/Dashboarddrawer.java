package com.centura_technologies.mycatalogue.Dashboard.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Activity.Controller.ActivityList;
import com.centura_technologies.mycatalogue.Leads.Controller.LeadsList;
import com.centura_technologies.mycatalogue.Login.Controller.Login;
import com.centura_technologies.mycatalogue.R;

/**
 * Created by Centura User1 on 19-08-2016.
 */
public class Dashboarddrawer {

    static LinearLayout dashboard, leads, activity, catalogues, products, shortlist, order, billing, customer, routeplan, expenses, sync, aboutus, logout;
    static TextView dashboardtext;
    static DrawerLayout Drawer;


    public static void show(final Context mContext, ViewGroup view) {
        dashboard = (LinearLayout) view.findViewById(R.id.dashboard);
        catalogues = (LinearLayout) view.findViewById(R.id.catalogues);
        products = (LinearLayout) view.findViewById(R.id.products);
        shortlist = (LinearLayout) view.findViewById(R.id.shortlist);
        order = (LinearLayout) view.findViewById(R.id.Order);
        sync = (LinearLayout) view.findViewById(R.id.sync);
        aboutus = (LinearLayout) view.findViewById(R.id.aboutus);
        logout = (LinearLayout) view.findViewById(R.id.logout);
        dashboardtext = (TextView) view.findViewById(R.id.dashboardtext);
        Drawer = (DrawerLayout) view.findViewById(R.id.drawer);

        DrawerOnClicks(mContext);
    }

    private static void DrawerOnClicks(final Context context) {
        dashboardtext.setTextColor(context.getResources().getColor(R.color.viewcolor));
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });

        catalogues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Login.class));
                ((Activity)context).finish();
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
    }
}
