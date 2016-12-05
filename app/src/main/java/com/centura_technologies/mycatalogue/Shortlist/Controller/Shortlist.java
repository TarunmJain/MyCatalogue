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
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
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
    BillingProducts billingProducts;
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
        StaticData.SelectedCustomers = new CustomerModel();
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
        totalproducts.setText("Total Products - " + DB.getShortlistproductmodel().size() + "");
        //int i=DB.getShortlistproductmodel().size();
        OnClicks();
        InitializeAdapter(Shortlist.this);

    }

    public static void InitializeAdapter(final Context context) {
        if (StaticData.customershortlistedview) {
            footer.setVisibility(View.GONE);
            details.setVisibility(View.VISIBLE);
            emptyshortlist.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            customername.setEnabled(false);
            StaticData.customershortlistedview = false;
            shortlistrecyclerview.setAdapter(new CustomerShortlistViewAdapter(context));
        } else {
            fab.setVisibility(View.GONE);
            if (DB.getShortlistproductmodel().size() != 0) {
                shortlistrecyclerview.setVisibility(View.VISIBLE);
                shortlistlayout.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);
                details.setVisibility(View.VISIBLE);
                customerslist.setVisibility(View.GONE);
                emptyshortlist.setVisibility(View.GONE);
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
                    customerslist.setVisibility(View.VISIBLE);
                    StaticData.TempCustomers = new ArrayList<CustomerModel>();
                    customerslist.setAdapter(new CustomersAdapter(Shortlist.this));
                } else {
                    customerslist.setVisibility(View.VISIBLE);
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
                if (StaticData.SelectedCustomers == new CustomerModel()) {
                    customername.setError("Please Select a customer!");
                } else {
                    list = new ArrayList<ShortlistModel>();
                    list = DB.getShortlistModels();
                    if (DB.getShortlistproductmodel().size() != 0) {
                        model = new ShortlistModel();
                        model.setShortlistNumber(UUID.randomUUID().toString());
                        model.setShortlistedDate(System.currentTimeMillis() + "");
                        model.setShortlistedDate(df.format(dateobj));
                        model.setCustomer(StaticData.Customers.get(0));
                        model.setSalesman(StaticData.CurrentSalesMan);
                        model.setShortlistedproducts(DB.getShortlistproductmodel());
                        list.add(model);
                        DB.setShortlistModels(list);
                        db = new DbHelper(Shortlist.this);
                        db.saveShortlisted();
                        DB.setShortlistproductmodel(new ArrayList<ShortlistProductModel>());
                        clear.performClick();
                        DB.getShortlistedlist().removeAll(DB.getShortlistedlist());
                        Toast.makeText(Shortlist.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(Shortlist.this, "No Shortlisted Products", Toast.LENGTH_SHORT).show();
                }
                /*if (save.getText().toString().matches("SHORTLIST NOW")) {
                    save.setText("SAVE");
                    clear.setText("CANCEL");
                } else {

                }*/
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.getShortlistproductmodel().removeAll(DB.getShortlistproductmodel());
                DB.getBillprodlist().removeAll(DB.getBillprodlist());
                InitializeAdapter(Shortlist.this);
                ShortlistAdapter.clearBill();
               /* if (clear.getText().toString().matches("CLEAR")) {

                } else {
                    clear.setText("CLEAR");
                    save.setText("SAVE");
                }*/
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
                for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
                    billingProducts = new BillingProducts();
                    billingProducts.setId(DB.getInitialModel().getProducts().get(j).getId());
                    billingProducts.setTitle(DB.getInitialModel().getProducts().get(j).getTitle());
                    billingProducts.setDescription(DB.getInitialModel().getProducts().get(j).getDescription());
                    billingProducts.setSectionId(DB.getInitialModel().getProducts().get(j).getSectionId());
                    billingProducts.setCategoryId(DB.getInitialModel().getProducts().get(j).getCategoryId());
                    billingProducts.setSKU(DB.getInitialModel().getProducts().get(j).getSKU());
                    billingProducts.setBarCode(DB.getInitialModel().getProducts().get(j).getBarCode());
                    billingProducts.setImageUrl(DB.getInitialModel().getProducts().get(j).getImageUrl());
                    billingProducts.setVideoUrl(DB.getInitialModel().getProducts().get(j).getVideoUrl());
                    billingProducts.setPdfUrl(DB.getInitialModel().getProducts().get(j).getPdfUrl());
                    billingProducts.setMRP(DB.getInitialModel().getProducts().get(j).getMRP());
                    billingProducts.setAmount(0.0);
                    billingProducts.setQuantity(0);
                    billingProducts.setPrice(DB.getInitialModel().getProducts().get(j).getSellingPrice());
                    billingProducts.setSellingPrice(DB.getInitialModel().getProducts().get(j).getSellingPrice());
                    billingProducts.setTags(DB.getInitialModel().getProducts().get(j).getTags());
                    billingProducts.setStatus(DB.getInitialModel().getProducts().get(j).getStatus());
                    billingProducts.setWeight(DB.getInitialModel().getProducts().get(j).getWeight());
                    billingProducts.setWishList(DB.getInitialModel().getProducts().get(j).isWishList());
                    billingProducts.setSelectedVarient(DB.getInitialModel().getProducts().get(j).getSelectedVarient());
                    billingProducts.setProductImages(DB.getInitialModel().getProducts().get(j).getProductImages());
                    billingProducts.setAttributes(DB.getInitialModel().getProducts().get(j).getAttributes());
                    billingProducts.setVariants(DB.getInitialModel().getProducts().get(j).getVariants());
                    for (ShortlistProductModel ShorlistedProduct: DB.getShortlistproductmodel()) {
                     if(ShorlistedProduct.getId().matches(DB.getInitialModel().getProducts().get(j).getId())){
                         billingProducts.setAmount(DB.getInitialModel().getProducts().get(j).getSellingPrice()*ShorlistedProduct.getQuantity());
                         billingProducts.setQuantity(ShorlistedProduct.getQuantity());
                         break;
                     }
                    }
                    model.add(billingProducts);
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
                ArrayList<BillingProducts> model = new ArrayList<BillingProducts>();
                DB.setBillprodlist(new ArrayList<BillingProducts>());
                for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
                    billingProducts = new BillingProducts();
                    billingProducts.setId(DB.getInitialModel().getProducts().get(j).getId());
                    billingProducts.setTitle(DB.getInitialModel().getProducts().get(j).getTitle());
                    billingProducts.setDescription(DB.getInitialModel().getProducts().get(j).getDescription());
                    billingProducts.setSectionId(DB.getInitialModel().getProducts().get(j).getSectionId());
                    billingProducts.setCategoryId(DB.getInitialModel().getProducts().get(j).getCategoryId());
                    billingProducts.setSKU(DB.getInitialModel().getProducts().get(j).getSKU());
                    billingProducts.setBarCode(DB.getInitialModel().getProducts().get(j).getBarCode());
                    billingProducts.setImageUrl(DB.getInitialModel().getProducts().get(j).getImageUrl());
                    billingProducts.setVideoUrl(DB.getInitialModel().getProducts().get(j).getVideoUrl());
                    billingProducts.setPdfUrl(DB.getInitialModel().getProducts().get(j).getPdfUrl());
                    billingProducts.setMRP(DB.getInitialModel().getProducts().get(j).getMRP());
                    billingProducts.setAmount(0.0);
                    billingProducts.setQuantity(0);
                    billingProducts.setPrice(DB.getInitialModel().getProducts().get(j).getSellingPrice());
                    billingProducts.setSellingPrice(DB.getInitialModel().getProducts().get(j).getSellingPrice());
                    billingProducts.setTags(DB.getInitialModel().getProducts().get(j).getTags());
                    billingProducts.setStatus(DB.getInitialModel().getProducts().get(j).getStatus());
                    billingProducts.setWeight(DB.getInitialModel().getProducts().get(j).getWeight());
                    billingProducts.setWishList(DB.getInitialModel().getProducts().get(j).isWishList());
                    billingProducts.setSelectedVarient(DB.getInitialModel().getProducts().get(j).getSelectedVarient());
                    billingProducts.setProductImages(DB.getInitialModel().getProducts().get(j).getProductImages());
                    billingProducts.setAttributes(DB.getInitialModel().getProducts().get(j).getAttributes());
                    billingProducts.setVariants(DB.getInitialModel().getProducts().get(j).getVariants());
                    for (ShortlistProductModel ShorlistedProduct: DB.getShortlistModels().get(StaticData.customershortlistpos).getShortlistedproducts()) {
                        if(ShorlistedProduct.getId().matches(billingProducts.getId())){
                            billingProducts.setAmount(DB.getInitialModel().getProducts().get(j).getSellingPrice());
                            billingProducts.setQuantity(1);
                            break;
                        }
                    }
                    model.add(billingProducts);
                }
                DB.setBillprodlist(model);
                Order.shortlistedorders = false;
                StaticData.ShortlistedOrder = true;
                startActivity(new Intent(Shortlist.this, Order.class));
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
            if (DB.getShortlistproductmodel().size() != 0) {
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
