package com.centura_technologies.mycatalogue.Dashboard.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Dashboard.View.SlidingImage_Adapter;
import com.centura_technologies.mycatalogue.Order.Controller.Order;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Sync.Controller.Sync;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class Dashboard extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView pos, crm, catalogue, others;
    ImageView hamburger, logoff, bannerimage;
    RelativeLayout options, drawerlayout;
    RelativeLayout.LayoutParams params;
    LinearLayout dashboard, leads, activity, catalogues, products, shortlist, order, billing, customer, routeplan, expenses, sync, aboutus, logout, alloptions;
    TextView dashboardtext, catalogue_option;
    DrawerLayout Drawer;
    TextView Title;
    RelativeLayout bannerpane;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<String> images;
    ArrayList<CategoryTree> tree;
    CategoryTree model;
    String id;
    static BillingProducts billprod;
    static ArrayList<BillingProducts> billprodlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handlechange();
        setContentView(R.layout.activity_dashboard);
        Title = (TextView) findViewById(R.id.AppbarTittle);
        Title.setText("DashBoard");
        sharedPreferences = getSharedPreferences(GenericData.MyPref, Dashboard.this.MODE_PRIVATE);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);

        dashboardtext = (TextView) findViewById(R.id.dashboardtext);
        dashboard = (LinearLayout) findViewById(R.id.dashboard);
        /*leads = (LinearLayout) findViewById(R.id.leads);
        activity = (LinearLayout) findViewById(R.id.activity);*/
        catalogues = (LinearLayout) findViewById(R.id.catalogues);
        products = (LinearLayout) findViewById(R.id.products);
        shortlist = (LinearLayout) findViewById(R.id.shortlist);
        order = (LinearLayout) findViewById(R.id.Order);
       /* billing = (LinearLayout) findViewById(R.id.billing);
        customer = (LinearLayout) findViewById(R.id.customer);
        routeplan = (LinearLayout) findViewById(R.id.routeplan);
        expenses = (LinearLayout) findViewById(R.id.expenses);*/
        sync = (LinearLayout) findViewById(R.id.sync);
        aboutus = (LinearLayout) findViewById(R.id.aboutus);
        logout = (LinearLayout) findViewById(R.id.logout);

        alloptions = (LinearLayout) findViewById(R.id.alloptions);
        hamburger = (ImageView) findViewById(R.id.hamburger);
        logoff = (ImageView) findViewById(R.id.logoff);
        // bannerimage=(ImageView)findViewById(R.id.bannerimage);
        bannerpane = (RelativeLayout) findViewById(R.id.bannerpane);
        options = (RelativeLayout) findViewById(R.id.optionlayout);
        drawerlayout = (RelativeLayout) findViewById(R.id.drawerlayout);

        params = (RelativeLayout.LayoutParams) (options).getLayoutParams();

        pos = (TextView) findViewById(R.id.pos);
        crm = (TextView) findViewById(R.id.crm);
        catalogue = (TextView) findViewById(R.id.catalogue);
        others = (TextView) findViewById(R.id.others);

        DrawerOnClicks();
        BillingProducts();

        tree = new ArrayList<CategoryTree>();
        DB.setTreelist(tree);
        for (int i = 0; i < DB.getInitialModel().getSections().size(); i++) {
            id = DB.getInitialModel().getSections().get(i).getId();
            model = new CategoryTree();
            model.setId(DB.getInitialModel().getSections().get(i).getId());
            model.setTitle(DB.getInitialModel().getSections().get(i).getTitle());
            model.setImageUrl(DB.getInitialModel().getSections().get(i).getImageUrl());
            model.setPriority(DB.getInitialModel().getSections().get(i).getPriority());
            model.setSelected(DB.getInitialModel().getSections().get(i).isSelected());
            for (int j = 0; j < DB.getInitialModel().getCategories().size(); j++) {
                if (id.matches(DB.getInitialModel().getCategories().get(j).getSectionId())) {
                    model.categories.add(DB.getInitialModel().getCategories().get(j));
                }
            }
            DB.getTreelist().add(model);
        }

        if (StaticData.Options.matches("Catalogue")) {
            alloptions.setVisibility(View.GONE);
            bannerpane.setVisibility(View.VISIBLE);
        } else {
            alloptions.setVisibility(View.VISIBLE);
            bannerpane.setVisibility(View.GONE);
        }
        init();
        OnClicks();
    }

    public static void BillingProducts(){
        billprodlist=new ArrayList<BillingProducts>();
        if(StaticData.shortlistedorders){
            for(int j=0;j<DB.getShortlistedlist().size();j++){
                billprod=new BillingProducts();
                billprod.setId(DB.getShortlistedlist().get(j).getId());
                billprod.setTitle(DB.getShortlistedlist().get(j).getTitle());
                billprod.setDescription(DB.getShortlistedlist().get(j).getDescription());
                billprod.setSectionId(DB.getShortlistedlist().get(j).getSectionId());
                billprod.setCategoryId(DB.getShortlistedlist().get(j).getCategoryId());
                billprod.setSKU(DB.getShortlistedlist().get(j).getSKU());
                billprod.setBarCode(DB.getShortlistedlist().get(j).getBarCode());
                billprod.setImageUrl(DB.getShortlistedlist().get(j).getImageUrl());
                billprod.setVideoUrl(DB.getShortlistedlist().get(j).getVideoUrl());
                billprod.setPdfUrl(DB.getShortlistedlist().get(j).getPdfUrl());
                billprod.setMRP(DB.getShortlistedlist().get(j).getMRP());
                billprod.setAmount(0.0);
                billprod.setQuantity(0);
                billprod.setPrice(DB.getShortlistedlist().get(j).getSellingPrice());
                billprod.setSellingPrice(DB.getShortlistedlist().get(j).getSellingPrice());
                billprod.setTags(DB.getShortlistedlist().get(j).getTags());
                billprod.setStatus(DB.getShortlistedlist().get(j).getStatus());
                billprod.setWeight(DB.getShortlistedlist().get(j).getWeight());
                billprod.setWishList(DB.getShortlistedlist().get(j).isWishList());
                billprod.setSelectedVarient(DB.getShortlistedlist().get(j).getSelectedVarient());
                billprod.setProductImages(DB.getShortlistedlist().get(j).getProductImages());
                billprod.setAttributes(DB.getShortlistedlist().get(j).getAttributes());
                billprod.setVariants(DB.getShortlistedlist().get(j).getVariants());
                billprodlist.add(billprod);
            }
        }else {
            for(int i=0;i<DB.getInitialModel().getProducts().size();i++){
                billprod=new BillingProducts();
                billprod.setId(DB.getInitialModel().getProducts().get(i).getId());
                billprod.setTitle(DB.getInitialModel().getProducts().get(i).getTitle());
                billprod.setDescription(DB.getInitialModel().getProducts().get(i).getDescription());
                billprod.setSectionId(DB.getInitialModel().getProducts().get(i).getSectionId());
                billprod.setCategoryId(DB.getInitialModel().getProducts().get(i).getCategoryId());
                billprod.setSKU(DB.getInitialModel().getProducts().get(i).getSKU());
                billprod.setBarCode(DB.getInitialModel().getProducts().get(i).getBarCode());
                billprod.setImageUrl(DB.getInitialModel().getProducts().get(i).getImageUrl());
                billprod.setVideoUrl(DB.getInitialModel().getProducts().get(i).getVideoUrl());
                billprod.setPdfUrl(DB.getInitialModel().getProducts().get(i).getPdfUrl());
                billprod.setMRP(DB.getInitialModel().getProducts().get(i).getMRP());
                billprod.setAmount(0.0);
                billprod.setQuantity(0);
                billprod.setPrice(DB.getInitialModel().getProducts().get(i).getSellingPrice());
                billprod.setSellingPrice(DB.getInitialModel().getProducts().get(i).getSellingPrice());
                billprod.setTags(DB.getInitialModel().getProducts().get(i).getTags());
                billprod.setStatus(DB.getInitialModel().getProducts().get(i).getStatus());
                billprod.setWeight(DB.getInitialModel().getProducts().get(i).getWeight());
                billprod.setWishList(DB.getInitialModel().getProducts().get(i).isWishList());
                billprod.setSelectedVarient(DB.getInitialModel().getProducts().get(i).getSelectedVarient());
                billprod.setProductImages(DB.getInitialModel().getProducts().get(i).getProductImages());
                billprod.setAttributes(DB.getInitialModel().getProducts().get(i).getAttributes());
                billprod.setVariants(DB.getInitialModel().getProducts().get(i).getVariants());
                billprodlist.add(billprod);
            }
        }
        DB.setBillprodlist(billprodlist);
    }

    private void init() {
        images = new ArrayList<String>();
        for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++)
            images.add(DB.getInitialModel().getProducts().get(i).getImageUrl());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(Dashboard.this, images));
        final float density = getResources().getDisplayMetrics().density;
        NUM_PAGES = images.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 5000);

        // Pager listener over indicator
    }

    private void OnClicks() {
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        crm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Catalogue.class));
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerlayout.getVisibility() == View.VISIBLE) {
                    hamburger.setImageResource(R.drawable.ic_dehaze_white_24dp);
                    drawerlayout.setVisibility(View.GONE);
                    params.addRule(RelativeLayout.RIGHT_OF, R.id.drawerlayout);
                    params.addRule(RelativeLayout.BELOW, 0);
                    options.setLayoutParams(params);
                } else {
                    hamburger.setImageResource(R.drawable.ic_keyboard_backspace_white_24dp);
                    drawerlayout.setVisibility(View.VISIBLE);
                    params.addRule(RelativeLayout.BELOW, 0);
                    params.addRule(RelativeLayout.RIGHT_OF, R.id.drawerlayout);
                    options.setLayoutParams(params);
                }
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericData.logout(Dashboard.this);
            }
        });
        bannerpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, SectionCatalogue.class));
            }
        });
    }

    private void DrawerOnClicks() {
        dashboardtext.setTextColor(getResources().getColor(R.color.viewcolor));
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamburger.setImageResource(R.drawable.ic_dehaze_white_24dp);
                drawerlayout.setVisibility(View.GONE);
            }
        });
        /*leads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, LeadsList.class));
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, ActivityList.class));
            }
        });*/
        catalogues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Dashboard.this, Catalogue.class));
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, SectionCatalogue.class));
            }
        });
        shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Shortlist.class));
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Order.class));
            }
        });
       /* billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        routeplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });*/
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Sync.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericData.logout(Dashboard.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit " + sharedPreferences.getString(GenericData.Sp_StoreCode, "") + "?")
                .setMessage("Are you sure you want to exit " + sharedPreferences.getString(GenericData.Sp_StoreCode, "") + "?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                        Dashboard.super.onBackPressed();
                    }
                }).create().show();
    }
}
