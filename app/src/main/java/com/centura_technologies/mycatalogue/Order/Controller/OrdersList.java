package com.centura_technologies.mycatalogue.Order.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.centura_technologies.mycatalogue.Order.View.OrderListAdapter;
import com.centura_technologies.mycatalogue.R;

public class OrdersList extends AppCompatActivity {

    RecyclerView OrdersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        OrdersList = (RecyclerView) findViewById(R.id.orderslist);
        OrdersList.setLayoutManager(new GridLayoutManager(OrdersList.this,3));
        OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(OrdersList.this,Order.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
    }
}
