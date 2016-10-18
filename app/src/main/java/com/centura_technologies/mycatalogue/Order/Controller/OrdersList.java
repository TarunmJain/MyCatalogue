package com.centura_technologies.mycatalogue.Order.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Order.View.OrderListAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

public class OrdersList extends AppCompatActivity {
    ArrayList<OrderModel> LocalOrders=new ArrayList<OrderModel>();
    EditText serachorder;
    RecyclerView OrdersList;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        serachorder= (EditText) findViewById(R.id.serachorder);
        OrdersList = (RecyclerView) findViewById(R.id.orderslist);
        OrdersList.setLayoutManager(new GridLayoutManager(OrdersList.this,3));
        LocalOrders = (ArrayList<OrderModel>) StaticData.orders.clone();
        OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrdersList.this, Order.class));
            }
        });
        SearchLogic();
    }

    private void SearchLogic() {

        serachorder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().matches(""))
                {
                    OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
                }
                else {
                    StaticData.orders=new ArrayList<OrderModel>();
                    for (OrderModel temporder:LocalOrders) {
                        Boolean matched=false;
                        if(temporder.OrderNumber.toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(temporder.customer.getName().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(temporder.customer.getPhone().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(matched)
                            StaticData.orders.add(temporder);
                    }
                    OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalOrders = (ArrayList<OrderModel>) StaticData.orders.clone();
        OrdersList.setAdapter(new OrderListAdapter(OrdersList.this));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
