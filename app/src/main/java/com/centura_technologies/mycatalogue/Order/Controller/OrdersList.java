package com.centura_technologies.mycatalogue.Order.Controller;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Order.View.OrderListAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

public class OrdersList extends AppCompatActivity {
    static ArrayList<OrderModel> LocalOrders=new ArrayList<OrderModel>();
    EditText serachorder;
    static RecyclerView orderslist;
    TextView toolbar_title;
    static LinearLayout search;
    static RelativeLayout empty_shortlist;
    Button ordernow;
    static FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search=(LinearLayout)findViewById(R.id.search);
        empty_shortlist=(RelativeLayout)findViewById(R.id.empty_shortlist);
        ordernow=(Button)findViewById(R.id.ordernow);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        serachorder= (EditText) findViewById(R.id.serachorder);
        orderslist = (RecyclerView) findViewById(R.id.orderslist);
        orderslist.setLayoutManager(new GridLayoutManager(OrdersList.this, 3));
        InitializeAdapter(OrdersList.this);
        SearchLogic();
        OnClicks();
    }

    private void OnClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.vieworder=false;
                Sync.BillingProducts();
                StaticData.ShortlistedOrder=false;
                startActivity(new Intent(OrdersList.this, Order.class));
            }
        });

        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.vieworder=false;
                Sync.BillingProducts();
                StaticData.ShortlistedOrder=false;
                startActivity(new Intent(OrdersList.this, Order.class));
            }
        });
    }

    public static void InitializeAdapter(Context context) {
        LocalOrders = (ArrayList<OrderModel>) StaticData.orders.clone();
        if(LocalOrders.size()!=0){
            fab.setVisibility(View.VISIBLE);
            orderslist.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            empty_shortlist.setVisibility(View.VISIBLE);
            orderslist.setAdapter(new OrderListAdapter(context));
        }else {
            fab.setVisibility(View.GONE);
            orderslist.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            empty_shortlist.setVisibility(View.VISIBLE);
        }
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
                    orderslist.setAdapter(new OrderListAdapter(OrdersList.this));
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
                    orderslist.setAdapter(new OrderListAdapter(OrdersList.this));
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
        InitializeAdapter(OrdersList.this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
