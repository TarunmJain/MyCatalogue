package com.centura_technologies.mycatalogue.Shortlist.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Order.View.OrderListAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Model.ShortlistModel;
import com.centura_technologies.mycatalogue.Shortlist.View.CustomerShortlistAdapter;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 20-10-2016.
 */
public class CustomerShortlist extends AppCompatActivity {
    static ArrayList<ShortlistModel> Localshortlist=new ArrayList<ShortlistModel>();
    Toolbar toolbar;
    static RecyclerView recyclerView;
    static FloatingActionButton fab;
    TextView toolbar_title;
    EditText serachorder;
    static LinearLayout search,shortlistlayout;
    static RelativeLayout empty_shortlist;
    Button shortlistnow;
    ArrayList<ShortlistModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customershortlist);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Customer Shortlist");
        search=(LinearLayout)findViewById(R.id.search);
        shortlistlayout=(LinearLayout)findViewById(R.id.shortlistlayout);
        empty_shortlist=(RelativeLayout)findViewById(R.id.empty_shortlist);
        shortlistnow=(Button)findViewById(R.id.shortlistnow);
        serachorder= (EditText) findViewById(R.id.serachorder);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerShortlist.this,LinearLayoutManager.VERTICAL,false));
        Localshortlist= (ArrayList<ShortlistModel>) DB.getShortlistModels().clone();
        InitializationAdapter(CustomerShortlist.this);

        SearchLogic();
        OnClicks();
    }

    public static void InitializationAdapter(Context context) {
        if(DB.getShortlistModels().size()!=0){
            fab.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            shortlistlayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            empty_shortlist.setVisibility(View.GONE);
            recyclerView.setAdapter(new CustomerShortlistAdapter(context));
        }else {
            fab.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            shortlistlayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
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
                    recyclerView.setAdapter(new CustomerShortlistAdapter(CustomerShortlist.this));
                }
                else {
                    DB.setShortlistModels(new ArrayList<ShortlistModel>());
                    list=new ArrayList<ShortlistModel>();
                    for (ShortlistModel tempshortlist:Localshortlist) {
                        Boolean matched=false;
                        if(tempshortlist.ShortlistNumber.toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(tempshortlist.customer.getName().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(tempshortlist.customer.getPhone().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(matched)
                            list.add(tempshortlist);
                            DB.setShortlistModels(list);
                    }
                    recyclerView.setAdapter(new CustomerShortlistAdapter(CustomerShortlist.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void OnClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerShortlist.this, Catalogue.class));
                finish();
            }
        });

        shortlistnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreadCrumb.Section = "All Products";
                StaticData.SelectedCategoryId = "-1";
                BreadCrumb.Category = "";
                if (DB.getInitialModel().getProducts().size() != -0) {
                    startActivity(new Intent(CustomerShortlist.this, Catalogue.class));
                    finish();
                } else Toast.makeText(CustomerShortlist.this, "No Products", Toast.LENGTH_SHORT).show();
            }
        });
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
