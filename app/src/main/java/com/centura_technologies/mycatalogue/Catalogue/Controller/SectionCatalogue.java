package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.View.CollectionAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.CollectionnewAdapter;
import com.centura_technologies.mycatalogue.Order.Controller.OrdersList;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Shortlist.Controller.CustomerShortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Sync.Controller.SyncClass;
import com.centura_technologies.mycatalogue.test.CoverFlowAdapternew;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionCatalogue extends AppCompatActivity {
    static Toolbar toolbar;
    //DrawerLayout Drawer;
    //ActionBarDrawerToggle mDrawerToggle;
    static FeatureCoverFlow coverFlow;
    private static TextSwitcher mTitle;
    SharedPreferences sharedPreferences;
    public static ArrayList<CollectionModel> collectionmodel;
    //TextView catalogtext,shortlisttext,ordertext,settingstext,logouttext;

    public void animate(View view, final int position) {
        view.setVisibility(View.VISIBLE);
        view.animate().cancel();
        view.setTranslationY(100);
        view.setAlpha(0);
        view.animate().alpha(1.0f).translationY(0).setDuration(1000);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectioncatalogue);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Collections");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(SectionCatalogue.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);
        /*animate(catalogtext, 1);
        animate(shortlisttext,1);
        animate(ordertext,1);
        animate(settingstext,1);
        animate(logouttext,1);*/
        sharedPreferences = getSharedPreferences(GenericData.MyPref, SectionCatalogue.this.MODE_PRIVATE);
        InitializationCollectionAdapter(SectionCatalogue.this);
        StaticData.DrawerTextDisable = "Catalogue";
    }

    public static void InitializationCollectionAdapter(final Context context) {
        collectionmodel = new ArrayList<CollectionModel>();
        for (int k = 0; k < DB.getInitialModel().getCollections().size(); k++) {
            collectionmodel.add(DB.getInitialModel().getCollections().get(k));
        }
        if (collectionmodel.size() != 0) {
            coverFlow.setAdapter(new CoverFlowAdapternew(context, collectionmodel));
        }
        coverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BreadCrumb.Section = collectionmodel.get(position).getTitle();
                BreadCrumb.Category = "";
                StaticData.SelectedCollectionProducts = new ArrayList<String>();
                StaticData.SelectedCollection = true;
                StaticData.SelectedCollectionProducts = collectionmodel.get(position).getProductIds();
                ((Activity) context).startActivity(new Intent(context, Catalogue.class));

            }
        });

        coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(collectionmodel.get(position).getTitle().toUpperCase());
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            GenericData.logout(SectionCatalogue.this);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitializationCollectionAdapter(SectionCatalogue.this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

}
