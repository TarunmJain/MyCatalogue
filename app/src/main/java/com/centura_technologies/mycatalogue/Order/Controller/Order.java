package com.centura_technologies.mycatalogue.Order.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.Order.View.OrderProductSearchAdapter;
import com.centura_technologies.mycatalogue.Order.View.OrderProductViewAdapter;
import com.centura_technologies.mycatalogue.Order.View.OrderProductsAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Centura User1 on 24-09-2016.
 */
public class Order extends AppCompatActivity {
    Toolbar toolbar;
    public static RecyclerView orderlist_recyclerview;
    public static ArrayList<BillingProducts> shorlistedmodel;
    RelativeLayout billdatelayout;
    static RelativeLayout filterpane;
    EditText billno, custname, salespersonname;
    TextView billdate, toolbar_title;
    Spinner spinner;
    static CardView billdetailheader;
    ImageView checked;
    static LinearLayout shortlistedorder;
    public static LinearLayout category;
    Button clearBill, placeorder;
    TextView Cancel;
    public static EditText serachorderlist;
    static RelativeLayout editfooter, viewfooter;
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
    static Double latitude, longitude;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        billdatelayout = (RelativeLayout) findViewById(R.id.billdatelayout);
        billno = (EditText) findViewById(R.id.billno);
        billno.setText(UUID.randomUUID().toString());
        billdate = (TextView) findViewById(R.id.billdate);
        serachorderlist = (EditText) findViewById(R.id.serachorderlist);
        custname = (EditText) findViewById(R.id.custname);
        salespersonname = (EditText) findViewById(R.id.salespersonname);
        checked = (ImageView) findViewById(R.id.checked);
        billdetailheader = (CardView) findViewById(R.id.billdetailheader);
        orderlist_recyclerview = (RecyclerView) findViewById(R.id.orderlist_recyclerview);
        spinner = (Spinner) findViewById(R.id.spinner);
        shortlistedorder = (LinearLayout) findViewById(R.id.shortlistedorder);
        category=(LinearLayout)findViewById(R.id.category);
        clearBill = (Button) findViewById(R.id.clear);
        filterpane = (RelativeLayout) findViewById(R.id.filterpane);
        placeorder = (Button) findViewById(R.id.placeorder);
        editfooter = (RelativeLayout) findViewById(R.id.editfooter);
        viewfooter = (RelativeLayout) findViewById(R.id.viewfooter);
        //Cancel = (TextView) findViewById(R.id.cancel);
        orderlist_recyclerview.setLayoutManager(new LinearLayoutManager(Order.this, LinearLayoutManager.VERTICAL, false));
        categories = new ArrayList<String>();
        categoryids = new ArrayList<String>();
        categories.add("All Products");
        categoryids.add("-1");
        for (int i = 0; i < DB.getInitialModel().getCategories().size(); i++) {
            categoryids.add(DB.getInitialModel().getCategories().get(i).getId());
            categories.add(DB.getInitialModel().getCategories().get(i).getTitle());
        }
        if(StaticData.ShortlistedOrder){
            Order.category.setVisibility(View.GONE);
            StaticData.ShortlistedOrder=false;
        }else {
            Order.category.setVisibility(View.VISIBLE);
            StaticData.ShortlistedOrder=true;
        }
        InitializeAdapter(Order.this);
        onClicks();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DATE);
        updateDisplay();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void onClicks() {
        serachorderlist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s != null)
                    if (!s.toString().toLowerCase().matches(""))
                        orderlist_recyclerview.setAdapter(new OrderProductSearchAdapter(Order.this));
                    else
                        InitializeAdapter(Order.this);
                else
                    InitializeAdapter(Order.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        billdatelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        shortlistedorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortlistedorders) {
                    shortlistedorders = false;
                    checked.setImageResource(R.drawable.uncheck);
                    InitializeAdapter(Order.this);
                } else {
                    checked.setImageResource(R.drawable.checkcircle);
                    shortlistedorders = true;
                    InitializeAdapter(Order.this);
                }
            }
        });

        clearBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearBill.getText().toString().matches("CANCEL")) {
                    clearBill.setText("CLEAR BILL");
                    placeorder.setText("SAVE BILL");
                    shortlistedorders = false;
                    billdetailheader.setVisibility(View.GONE);
                    filterpane.setVisibility(View.VISIBLE);
                } else {
                    shortlistedorders = true;
                    shortlistedorder.performClick();
                    for (BillingProducts prod : DB.getBillprodlist()) {
                        if (prod.getQuantity() > 0) {
                            prod.setQuantity(0);
                        }
                    }
                    InitializeAdapter(Order.this);
                    OrderProductsAdapter.clearBill();
                }
            }
        });

       /* Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billdetailheader.setVisibility(View.GONE);
                filterpane.setVisibility(View.VISIBLE);
                placeorder.setText("SAVE BILL");
            }
        });*/

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placeorder.getText().toString().matches("SAVE BILL")) {
                    shortlistedorders = false;
                    shortlistedorder.performClick();
                    billdetailheader.setVisibility(View.VISIBLE);
                    filterpane.setVisibility(View.GONE);
                    salespersonname.setText(StaticData.CurrentSalesMan.Name);
                    placeorder.setText("PLACE ORDER");
                    clearBill.setText("CANCEL");
                } else {
                    placeorder.setText("SAVE BILL");
                    clearBill.setText("CLEAR BILL");
                    setLocation(Order.this);
                    shorlistedmodel = new ArrayList<BillingProducts>();
                    for (BillingProducts temp : DB.getBillprodlist()) {
                        if (temp.getQuantity() > 0) {
                            shorlistedmodel.add(temp);
                        }
                    }
                    if (shorlistedmodel.size() > 0) {
                        OrderModel temporder = new OrderModel();
                        temporder.billingProducts = (ArrayList<BillingProducts>) shorlistedmodel.clone();
                        temporder.Amount = OrderProductsAdapter.total_amount;
                        temporder.customer = StaticData.Customers.get(0);
                        temporder.OrderDate = billdate.getText().toString();
                        temporder.OrderNumber = billno.getText().toString();
                        temporder.salesman = StaticData.CurrentSalesMan;
                        StaticData.orders.add(temporder);
                        DbHelper dbHelper = new DbHelper(Order.this);
                        dbHelper.saveOrders();
                        clearBill.performClick();
                        DB.getShortlistedlist().removeAll(DB.getShortlistedlist());
                        Shortlist.InitializeAdapter(Order.this);
                        finish();
                    } else {
                        Toast.makeText(Order.this, "No Products Selected", Toast.LENGTH_SHORT).show();
                    }

                }
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

    private static void setLocation(final Context context) {
        //info.setText("Searching for Location!");
        final LocationManager locationManager;
        LocationListener locationListener;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(this);
                //info.setText("Location Recorder!");
                //checkversion(context);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ((Activity) context).startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
        if (StaticData.vieworder) {
            billdetailheader.setVisibility(View.VISIBLE);
            editfooter.setVisibility(View.GONE);
            viewfooter.setVisibility(View.VISIBLE);
            filterpane.setVisibility(View.GONE);
            serachorderlist.setVisibility(View.GONE);
            orderlist_recyclerview.setAdapter(new OrderProductViewAdapter(context));
        } else {
            editfooter.setVisibility(View.VISIBLE);
            viewfooter.setVisibility(View.GONE);
            serachorderlist.setVisibility(View.VISIBLE);
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
            orderlist_recyclerview.setAdapter(new OrderProductsAdapter(context));
        }
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
