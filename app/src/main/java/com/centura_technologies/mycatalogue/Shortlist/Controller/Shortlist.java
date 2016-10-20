package com.centura_technologies.mycatalogue.Shortlist.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Model.ShortlistModel;
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
    static RecyclerView shortlistrecyclerview;
    EditText customername, salespersonname;
    Button save, clear;
    TextView totalproducts;
    static RelativeLayout emptyshortlist, footer;
    static Button shortlistnow;
    static CardView details;
    ArrayList<ShortlistModel> list;
    ShortlistModel model;
    DbHelper db;
    DateFormat df;
    Date dateobj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortlist);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Shortlist");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        df = new SimpleDateFormat("dd/MM/yy");
        dateobj = new Date();
        emptyshortlist = (RelativeLayout) findViewById(R.id.empty_shortlist);
        footer = (RelativeLayout) findViewById(R.id.footer);
        shortlistnow = (Button) findViewById(R.id.shortlist);
        details = (CardView) findViewById(R.id.details);
        customername = (EditText) findViewById(R.id.customername);
        salespersonname = (EditText) findViewById(R.id.salespersonname);
        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);
        totalproducts = (TextView) findViewById(R.id.totalproducts);
        shortlistrecyclerview = (RecyclerView) findViewById(R.id.shortlistrecyclerview);
        shortlistrecyclerview.setLayoutManager(new GridLayoutManager(Shortlist.this, 3));
        totalproducts.setText("Total Products - " + DB.getShortlistedlist().size() + "");
        OnClicks();
        InitializeAdapter(Shortlist.this);

    }

    public static void InitializeAdapter(final Context context) {
        if (DB.getShortlistedlist().size() != 0) {
            shortlistrecyclerview.setAdapter(new ShortlistAdapter(context));
        } else {
            shortlistrecyclerview.setVisibility(View.GONE);
            footer.setVisibility(View.GONE);
            details.setVisibility(View.GONE);
            emptyshortlist.setVisibility(View.VISIBLE);
            shortlistnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).startActivity(new Intent(context, Catalogue.class));
                    ((Activity) context).finish();
                }
            });
        }
    }

    private void OnClicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save.getText().toString().matches("SAVE")) {
                    details.setVisibility(View.VISIBLE);
                    save.setText("SHORTLIST");
                    clear.setText("CANCEL");
                } else {
                    save.setText("SAVE");
                    details.setVisibility(View.GONE);
                    list = new ArrayList<ShortlistModel>();
                    list=DB.getShortlistModels();
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
                        Toast.makeText(Shortlist.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(Shortlist.this, "No Shortlisted Products", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clear.getText().toString().matches("CLEAR")){
                    DB.getShortlistedlist().removeAll(DB.getShortlistedlist());
                    InitializeAdapter(Shortlist.this);
                }else {
                    clear.setText("CLEAR");
                    save.setText("SAVE");
                    details.setVisibility(View.GONE);
                }
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
