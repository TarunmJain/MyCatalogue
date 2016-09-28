package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Catalogue.View.CollectionAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SectionCatalogueAdapter;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionCatalogue extends AppCompatActivity {
    private static final int HORIZONTAL = 1;
    static Toolbar toolbar;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    public static RecyclerView recyclerView;
    static LinearLayout collectionlay;
    static RecyclerView category_recyclerview,collections_recyclerview;
    public static ArrayList<CategoryTree> categories;
    public static ArrayList<Categories> category=new ArrayList<Categories>();
    public static ArrayList<CollectionModel> collectionmodel;
    public static boolean Section_to_Category=false;
    int h1, h2,screenhight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectioncatalogue);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Catalogue");
        setSupportActionBar(toolbar);
        Drawer = (DrawerLayout) findViewById(R.id.drawer);
        sharedPreferences = getSharedPreferences(GenericData.MyPref, SectionCatalogue.this.MODE_PRIVATE);
        collectionlay= (LinearLayout) findViewById(R.id.collectionlay);
        mDrawerToggle = new ActionBarDrawerToggle(SectionCatalogue.this, Drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

        recyclerView=(RecyclerView)findViewById(R.id.section_recyclerview);
        category_recyclerview=(RecyclerView)findViewById(R.id.category_recyclerview);
        collections_recyclerview=(RecyclerView)findViewById(R.id.collections_recyclerview);
        //UiManuplation();
        categories=new ArrayList<CategoryTree>();
        for(int i=0;i<DB.getTreelist().size();i++){
            categories.add(DB.getTreelist().get(i));
        }
        collectionmodel=new ArrayList<CollectionModel>();
        for(int k=0;k<DB.getInitialModel().getCollections().size();k++){
            collectionmodel.add(DB.getInitialModel().getCollections().get(k));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(SectionCatalogue.this,3));
        category_recyclerview.setLayoutManager(new GridLayoutManager(SectionCatalogue.this, 3));
        collections_recyclerview.setLayoutManager(new GridLayoutManager(SectionCatalogue.this,1,GridLayoutManager.HORIZONTAL,false));
        InitializationCollectionAdapter(SectionCatalogue.this);
        InitialzationSectionAdapter(SectionCatalogue.this);

        if(Section_to_Category){
            InitialzationSectionAdapter(SectionCatalogue.this);
        }else {
            InitialzationCategoryAdapter(SectionCatalogue.this,null);
        }

        GenericData.DrawerOnClicks(SectionCatalogue.this);
    }
    public static void InitializationCollectionAdapter(Context context){
        collections_recyclerview.setAdapter(new CollectionAdapter(context,collectionmodel));
    }

    public static void InitialzationCategoryAdapter(final Context context,CategoryTree categoryTree){
        toolbar.setTitle(categoryTree.getTitle());
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitialzationSectionAdapter(context);
            }
        });
        category_recyclerview.setVisibility(View.VISIBLE);
        collectionlay.setVisibility(View.GONE);
        Section_to_Category=false;
        category_recyclerview.setAdapter(new SectionCatalogueAdapter(context,categoryTree));
    }

    public static void InitialzationSectionAdapter(final Context context){
        toolbar.setTitle("Catalogue");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitialzationSectionAdapter(context);
            }
        });
        collectionlay.setVisibility(View.VISIBLE);
        category_recyclerview.setVisibility(View.GONE);
        Section_to_Category=true;
        int finallines=0;
        int viewHeight = GenericData.convertDpToPixels(310, context);
        if(SectionCatalogue.categories.size()<3)
            finallines=1;
        else {
            float lines=(float)(SectionCatalogue.categories.size())/3;
            finallines=(int)lines;
            if(lines>finallines)
                finallines+=1;
        }
        viewHeight = viewHeight * finallines;
        recyclerView.getLayoutParams().height = viewHeight;
        recyclerView.setAdapter(new SectionCatalogueAdapter(context,null));
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
        return super.onOptionsItemSelected(item);
    }
    private void UiManuplation() {
        h1 = 0;
        h2 = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenhight = metrics.heightPixels;
        recyclerView.getLayoutParams().height = screenhight-200;
        category_recyclerview.getLayoutParams().height = screenhight-200;
    }
}
