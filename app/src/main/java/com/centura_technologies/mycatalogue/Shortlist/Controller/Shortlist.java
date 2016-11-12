package com.centura_technologies.mycatalogue.Shortlist.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Order.Controller.Order;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.View.OrderProductsAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Model.ShortlistModel;
import com.centura_technologies.mycatalogue.Shortlist.View.CustomerShortlistViewAdapter;
import com.centura_technologies.mycatalogue.Shortlist.View.CustomersAdapter;
import com.centura_technologies.mycatalogue.Shortlist.View.ShortlistAdapter;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class Shortlist extends AppCompatActivity {
    Toolbar toolbar;
    static RecyclerView shortlistrecyclerview, customerslist;
    public static EditText customername, salespersonname;
    Button save, clear, bill;
    TextView totalproducts;
    static RelativeLayout emptyshortlist, footer;
    static LinearLayout shortlistlayout;
    static CardView shortlistnow;
    static CardView details;
    static FloatingActionButton fab;
    ArrayList<ShortlistModel> list;
    BillingProducts billprod;
    ShortlistModel model;
    DbHelper db;
    DateFormat df;
    Date dateobj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortlist);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        df = new SimpleDateFormat("dd/MM/yy");
        dateobj = new Date();
        StaticData.SelectedCustomers=new CustomerModel();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        emptyshortlist = (RelativeLayout) findViewById(R.id.empty_shortlist);
        footer = (RelativeLayout) findViewById(R.id.footer);
        shortlistnow = (CardView) findViewById(R.id.shortlist);
        customerslist = (RecyclerView) findViewById(R.id.customerslist);
        customerslist.setLayoutManager(new LinearLayoutManager(Shortlist.this));
        details = (CardView) findViewById(R.id.details);
        customername = (EditText) findViewById(R.id.customername);
        salespersonname = (EditText) findViewById(R.id.salespersonname);
        shortlistlayout = (LinearLayout) findViewById(R.id.shortlistlayout);
        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);
        bill = (Button) findViewById(R.id.bill);
        totalproducts = (TextView) findViewById(R.id.totalproducts);
        shortlistrecyclerview = (RecyclerView) findViewById(R.id.shortlistrecyclerview);
        shortlistrecyclerview.setLayoutManager(new LinearLayoutManager(Shortlist.this, LinearLayoutManager.VERTICAL, false));
        totalproducts.setText("Total Products - " + DB.getShortlistedlist().size() + "");
        OnClicks();
        InitializeAdapter(Shortlist.this);

    }

    public static void InitializeAdapter(final Context context) {
        if (StaticData.customershortlistedview) {
            footer.setVisibility(View.GONE);
            details.setVisibility(View.VISIBLE);
            emptyshortlist.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            StaticData.customershortlistedview = false;
            shortlistrecyclerview.setAdapter(new CustomerShortlistViewAdapter(context));
        } else {
            fab.setVisibility(View.GONE);
            if (DB.getShortlistedlist().size() != 0) {
                shortlistrecyclerview.setAdapter(new ShortlistAdapter(context));
            } else {
                shortlistrecyclerview.setVisibility(View.GONE);
                shortlistlayout.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                details.setVisibility(View.GONE);
                emptyshortlist.setVisibility(View.VISIBLE);
            }
        }
    }

    private void OnClicks() {
        customername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    StaticData.TempCustomers = new ArrayList<CustomerModel>();
                    customerslist.setAdapter(new CustomersAdapter(Shortlist.this));
                } else {
                    StaticData.TempCustomers = new ArrayList<CustomerModel>();
                    if (!StaticData.SelectedCustomers.getName().toLowerCase().matches(s.toString().toLowerCase()))
                        for (CustomerModel customer : StaticData.Customers) {
                            if (customer.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                StaticData.TempCustomers.add(customer);
                            }
                        }
                    customerslist.setAdapter(new CustomersAdapter(Shortlist.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save.getText().toString().matches("SHORTLIST NOW")) {
                    details.setVisibility(View.VISIBLE);
                    save.setText("SAVE");
                    clear.setText("CANCEL");
                } else {
                    if(StaticData.SelectedCustomers==new CustomerModel())
                    {
                        customername.setError("Please Select a customer!");
                    }
                    else {
                        save.setText("SHORTLIST NOW");
                        details.setVisibility(View.VISIBLE);
                        list = new ArrayList<ShortlistModel>();
                        list = DB.getShortlistModels();
                        if (DB.getShortlistedlist().size() != 0) {
                            model = new ShortlistModel();
                            model.setShortlistNumber(UUID.randomUUID().toString());
                            model.setShortlistedDate(System.currentTimeMillis() + "");
                            model.setShortlistedDate(df.format(dateobj));
                            model.setCustomer(StaticData.Customers.get(0));
                            model.setSalesman(StaticData.CurrentSalesMan);
                            model.setShortlistedproducts(DB.getShortlistedlist());
                            list.add(model);
                            DB.setShortlistModels(list);
                            db = new DbHelper(Shortlist.this);
                            db.saveShortlisted();
                            DB.shortlistedlist = new ArrayList<Products>();
                            Toast.makeText(Shortlist.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else
                            Toast.makeText(Shortlist.this, "No Shortlisted Products", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clear.getText().toString().matches("CLEAR")) {
                    DB.getShortlistedlist().removeAll(DB.getShortlistedlist());
                    DB.getBillprodlist().removeAll(DB.getBillprodlist());
                    InitializeAdapter(Shortlist.this);
                } else {
                    clear.setText("CLEAR");
                    save.setText("SAVE");
                    details.setVisibility(View.VISIBLE);
                }
            }
        });

        shortlistnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreadCrumb.Section = "All Products";
                StaticData.SelectedCategoryId = "-1";
                BreadCrumb.Category = "";
                if (DB.getInitialModel().getProducts().size() != -0) {
                    finish();
                } else Toast.makeText(Shortlist.this, "No Products", Toast.LENGTH_SHORT).show();

            }
        });

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<BillingProducts> model = new ArrayList<BillingProducts>();
                for (int i = 0; i < DB.getShortlistedlist().size(); i++) {
                    billprod = new BillingProducts();
                    billprod.setId(DB.getShortlistedlist().get(i).getId());
                    billprod.setTitle(DB.getShortlistedlist().get(i).getTitle());
                    billprod.setDescription(DB.getShortlistedlist().get(i).getDescription());
                    billprod.setSectionId(DB.getShortlistedlist().get(i).getSectionId());
                    billprod.setCategoryId(DB.getShortlistedlist().get(i).getCategoryId());
                    billprod.setSKU(DB.getShortlistedlist().get(i).getSKU());
                    billprod.setBarCode(DB.getShortlistedlist().get(i).getBarCode());
                    billprod.setImageUrl(DB.getShortlistedlist().get(i).getImageUrl());
                    billprod.setVideoUrl(DB.getShortlistedlist().get(i).getVideoUrl());
                    billprod.setPdfUrl(DB.getShortlistedlist().get(i).getPdfUrl());
                    billprod.setMRP(DB.getShortlistedlist().get(i).getMRP());
                    billprod.setQuantity(0);
                    billprod.setAmount(0.0);
                    billprod.setPrice(DB.getShortlistedlist().get(i).getSellingPrice());
                    billprod.setSellingPrice(DB.getShortlistedlist().get(i).getSellingPrice());
                    billprod.setTags(DB.getShortlistedlist().get(i).getTags());
                    billprod.setStatus(DB.getShortlistedlist().get(i).getStatus());
                    billprod.setWeight(DB.getShortlistedlist().get(i).getWeight());
                    billprod.setWishList(DB.getShortlistedlist().get(i).isWishList());
                    billprod.setSelectedVarient(DB.getShortlistedlist().get(i).getSelectedVarient());
                    billprod.setProductImages(DB.getShortlistedlist().get(i).getProductImages());
                    billprod.setAttributes(DB.getShortlistedlist().get(i).getAttributes());
                    billprod.setVariants(DB.getShortlistedlist().get(i).getVariants());
                    model.add(billprod);
                }
                DB.setBillprodlist(model);
                Order.shortlistedorders = false;
                StaticData.ShortlistedOrder = true;
                startActivity(new Intent(Shortlist.this, Order.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Shortlist.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
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
        if (item.getItemId() == R.id.slideshow) {
            if (DB.getShortlistedlist().size() != 0) {
                startActivity(new Intent(Shortlist.this, SlideShow.class));
            } else {
                Toast.makeText(Shortlist.this, "Please add Products for Shortlist", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DB.getBillprodlist().removeAll(DB.getBillprodlist());
        finish();
    }

}
