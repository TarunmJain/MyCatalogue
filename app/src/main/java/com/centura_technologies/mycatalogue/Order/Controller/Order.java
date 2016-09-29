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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    static RecyclerView orderlist_recyclerview;
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
                    shortlistedorders = true;
                    for (BillingProducts temp : DB.getBillprodlist()) {
                        if (temp.getQuantity() > 0) {
                            shorlistedmodel.add(temp);
                        }
                    }
                    if (shorlistedmodel.size() != 0) {
                        checked.setImageResource(R.mipmap.checked);
                        InitializeAdapter(Order.this);
                    } else Toast.makeText(Order.this, "Please Select the Item", Toast.LENGTH_SHORT).show();
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
