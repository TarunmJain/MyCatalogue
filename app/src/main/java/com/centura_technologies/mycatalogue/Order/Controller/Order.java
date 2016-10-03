package com.centura_technologies.mycatalogue.Order.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Dashboard.Controller.Dashboard;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.View.OrderListAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Centura User1 on 24-09-2016.
 */
public class Order extends AppCompatActivity {
    Toolbar toolbar;
    public static RecyclerView orderlist_recyclerview;
    public static ArrayList<BillingProducts> shorlistedmodel;
    RelativeLayout billdatelayout;
    EditText billno, custname, salespersonname;
    TextView billdate;
    Spinner spinner;
    ImageView checked;
    LinearLayout shortlistedorder;
    Button clear, save;
    int mYear, mMonth, mDay;
    static final int DATE_DIALOG_ID = 0;
    public static boolean shortlistedorders = false;
    public static boolean selectedcategories = false;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> categories;
    ArrayList<String> categoryids;
    public static String item = "-1";
    static BillingProducts billingProducts;
    public static ArrayList<BillingProducts> billingProductsArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        billdatelayout = (RelativeLayout) findViewById(R.id.billdatelayout);
        billno = (EditText) findViewById(R.id.billno);
        billdate = (TextView) findViewById(R.id.billdate);
        custname = (EditText) findViewById(R.id.custname);
        salespersonname = (EditText) findViewById(R.id.salespersonname);
        checked = (ImageView) findViewById(R.id.checked);
        orderlist_recyclerview = (RecyclerView) findViewById(R.id.orderlist_recyclerview);
        spinner = (Spinner) findViewById(R.id.spinner);
        shortlistedorder = (LinearLayout) findViewById(R.id.shortlistedorder);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        orderlist_recyclerview.setLayoutManager(new LinearLayoutManager(Order.this, LinearLayoutManager.VERTICAL, false));
        categories = new ArrayList<String>();
        categoryids = new ArrayList<String>();
        categories.add("All Products");
        categoryids.add("-1");
        for (int i = 0; i < DB.getInitialModel().getCategories().size(); i++) {
            categoryids.add(DB.getInitialModel().getCategories().get(i).getId());
            categories.add(DB.getInitialModel().getCategories().get(i).getTitle());
        }
        InitializeAdapter(Order.this);
        onClicks();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DATE);
        updateDisplay();

    }

    private void onClicks() {
        billdatelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        shortlistedorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shorlistedmodel = new ArrayList<BillingProducts>();
                if (shortlistedorders) {
                    shortlistedorders = false;
                    checked.setImageResource(R.mipmap.checking);
                    InitializeAdapter(Order.this);
                } else {
                    for (BillingProducts temp : DB.getBillprodlist()) {
                        if (temp.getQuantity() > 0) {
                            shorlistedmodel.add(temp);
                        }
                    }
                    if (shorlistedmodel.size() != 0) {
                        checked.setImageResource(R.mipmap.checked);
                        shortlistedorders = true;
                    } else {
                        shortlistedorders = false;
                        Toast.makeText(Order.this, "Please Select the Item", Toast.LENGTH_SHORT).show();
                    }
                    InitializeAdapter(Order.this);
                }
                /*if (StaticData.shortlistedorders) {
                    checked.setImageResource(R.mipmap.checked);
                    StaticData.shortlistedorders = false;
                }else{
                    checked.setImageResource(R.mipmap.checking);
                    StaticData.shortlistedorders = true;
                }
                Dashboard.BillingProducts();
                InitializeAdapter(Order.this);*/
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(BillingProducts prod:DB.getBillprodlist()){
                    if(prod.getQuantity()>0){
                        prod.setQuantity(0);
                    }
                }
                InitializeAdapter(Order.this);
            }
        });

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //item = parent.getItemAtPosition(position).toString();
                item = categoryids.get(position);
                selectedcategories = true;
                InitializeAdapter(Order.this);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateDisplay() {
        this.billdate.setText(
                new StringBuilder()
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public static void InitializeAdapter(Context context) {
        if (selectedcategories) {

            billingProductsArrayList = new ArrayList<BillingProducts>();
            if (item.matches("-1"))
                billingProductsArrayList = DB.getBillprodlist();
            else
                for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
                    if (DB.getInitialModel().getProducts().get(j).getCategoryId().matches(item)) {
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
                        billingProductsArrayList.add(billingProducts);
                    }
                }
        }
        int viewHeight = GenericData.convertDpToPixels(72, context);
        viewHeight = viewHeight * ((DB.getBillprodlist().size()));
        orderlist_recyclerview.getLayoutParams().height = viewHeight;
        orderlist_recyclerview.setAdapter(new OrderListAdapter(context));
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
