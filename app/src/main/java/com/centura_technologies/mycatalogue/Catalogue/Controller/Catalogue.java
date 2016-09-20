package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.View.CatalogueAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.FilterAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SearchAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SearchProductsAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SectionlistAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Centura User1 on 19-09-2016.
 */
public class Catalogue extends AppCompatActivity {
    Toolbar toolbar;
    ImageView filtericon, categoryicon, listicon;
    public static ImageView searchicon;
    RelativeLayout nocategory, quickview;
    static LinearLayout searchlayout, filterlayout, categorylayout, productlayout;
    public static EditText editsearch;
    Spinner spinner;
    FloatingActionButton fab;
    static RecyclerView recyclerview, recyclerview1, catagoriesrecyclerview, productsrecyclerview, filterrecyclerview;
    public static SearchProductsAdapter adapter;
    public static SearchAdapter adapter1;
    static LinearLayoutManager layoutManager1;
    ArrayList<String> suggestionsData = new ArrayList<String>();
    public static ArrayList<Products> products;
    public static ArrayList<CategoryTree> categories;
    List<String> sortby = new ArrayList<String>();
    static public String SearchString = "";
    static int SearchPageNumber = 0;
    static String item = "";
    public static boolean grid_to_listflag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Catalogue");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filtericon = (ImageView) findViewById(R.id.filtericon);
        categoryicon = (ImageView) findViewById(R.id.categoryicon);
        listicon = (ImageView) findViewById(R.id.listicon);
        searchicon = (ImageView) findViewById(R.id.searchicon);
        quickview = (RelativeLayout) findViewById(R.id.quickview);
        nocategory = (RelativeLayout) findViewById(R.id.nocategory);
        searchlayout = (LinearLayout) findViewById(R.id.searchlayout);
        filterlayout = (LinearLayout) findViewById(R.id.filterlayout);
        categorylayout = (LinearLayout) findViewById(R.id.categorylayout);
        productlayout = (LinearLayout) findViewById(R.id.productlayout);
        editsearch = (EditText) findViewById(R.id.editsearch);
        spinner = (Spinner) findViewById(R.id.spinner);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
        catagoriesrecyclerview = (RecyclerView) findViewById(R.id.catagoriesrecyclerview);
        productsrecyclerview = (RecyclerView) findViewById(R.id.productsrecyclerview);
        filterrecyclerview = (RecyclerView) findViewById(R.id.filterrecyclerview);


        recyclerview.setLayoutManager(new GridLayoutManager(Catalogue.this, 3));
        recyclerview1.setLayoutManager(new LinearLayoutManager(Catalogue.this));

        adapter = new SearchProductsAdapter(Catalogue.this);
        adapter1 = new SearchAdapter(Catalogue.this, suggestionsData);

        sortby.add("Price low-high");
        sortby.add("Price high-low");

        productslist();
        categorylist();
        SortSpinner();
        setsuggestiondata();
        searchset();
        OnClicks();

        StaticData.ProductsInGrid = true;
        StaticData.ProductsInList = false;
        productsrecyclerview.setLayoutManager(new GridLayoutManager(Catalogue.this, 3));
        InitializeAdapter(Catalogue.this);
    }

    public static void productslist() {
        products = new ArrayList<Products>();
        if (StaticData.SelectedSection) {
            StaticData.SelectedCategoryId = DB.getInitialModel().getCategories().get(0).getId();
        }
        for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
            if (DB.getInitialModel().getProducts().get(i).getCategoryId().matches(StaticData.SelectedCategoryId))
                products.add(DB.getInitialModel().getProducts().get(i));
        }
    }

    public static void categorylist(){
        categories=new ArrayList<CategoryTree>();
        for(int i=0;i<DB.getTreelist().size();i++){
            categories.add(DB.getTreelist().get(i));
        }
    }

    private void OnClicks() {
        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.GONE);
                filterlayout.setVisibility(View.VISIBLE);
                categorylayout.setVisibility(View.GONE);
                productlayout.setVisibility(View.GONE);
            }
        });

        categoryicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.GONE);
                filterlayout.setVisibility(View.GONE);
                categorylayout.setVisibility(View.VISIBLE);
                productlayout.setVisibility(View.GONE);
                layoutManager1 = new LinearLayoutManager(Catalogue.this, LinearLayoutManager.VERTICAL, false);
                catagoriesrecyclerview.setLayoutManager(layoutManager1);
                InitializeCategoryAdapter(Catalogue.this);
            }
        });

        listicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.GONE);
                filterlayout.setVisibility(View.GONE);
                categorylayout.setVisibility(View.GONE);
                productlayout.setVisibility(View.VISIBLE);
                if (grid_to_listflag) {
                    quickview.setVisibility(View.GONE);
                    StaticData.ProductsInGrid = true;
                    StaticData.ProductsInList = false;
                    productsrecyclerview.setLayoutManager(new GridLayoutManager(Catalogue.this, 3));
                    listicon.setImageResource(R.drawable.ic_format_list_bulleted_white_24dp);
                    //product_recyclerview.setAdapter(new CatalogueAdapter(Catalogue.this, products));
                    productsrecyclerview.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                    grid_to_listflag = false;
                } else {
                    quickview.setVisibility(View.VISIBLE);
                    StaticData.ProductsInGrid = false;
                    StaticData.ProductsInList = true;
                    productsrecyclerview.setLayoutManager(new LinearLayoutManager(Catalogue.this, LinearLayoutManager.VERTICAL, false));
                    listicon.setImageResource(R.drawable.ic_view_grid_white_24dp);
                    // product_recyclerview.setAdapter(new CatalogueAdapter(Catalogue.this, products));
                    productsrecyclerview.getLayoutParams().width = GenericData.convertDpToPixels(300, Catalogue.this);
                    grid_to_listflag = true;
                }
                InitializeAdapter(Catalogue.this);
            }
        });

        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.VISIBLE);
                filterlayout.setVisibility(View.GONE);
                categorylayout.setVisibility(View.GONE);
                productlayout.setVisibility(View.GONE);
                SearchApi(Catalogue.this);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Catalogue.this, Shortlist.class));
                finish();
            }
        });
    }

    private void SortSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Catalogue.this, R.layout.spinner_item, sortby);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                InitializeAdapter(Catalogue.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Drawable spinnerDrawable = spinner.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setBackground(spinnerDrawable);
        } else {
            spinner.setBackgroundDrawable(spinnerDrawable);
        }


    }

    public void searchset() {
        editsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchlayout.setVisibility(View.VISIBLE);
                    filterlayout.setVisibility(View.GONE);
                    categorylayout.setVisibility(View.GONE);
                    productlayout.setVisibility(View.GONE);
                    searchicon.performClick();
                    return true;
                }
                return false;
            }
        });

        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchProductsAdapter.data = new ArrayList<Products>();
                SearchAdapter.data = new ArrayList<String>();
                if (s.length() > 0) {
                    for (String temp : suggestionsData) {
                        if (temp.toLowerCase().contains(s.toString().toLowerCase()))
                            SearchAdapter.data.add(temp);
                        nocategory.setVisibility(View.GONE);
                        searchlayout.setVisibility(View.VISIBLE);
                        adapter1.notifyDataSetChanged();
                    }
                    recyclerview.setAdapter(adapter1);
                } else {
                    SearchAdapter.data = new ArrayList<String>();
                    adapter1.notifyDataSetChanged();
                    SearchProductsAdapter.data = new ArrayList<Products>();
                    adapter.notifyDataSetChanged();
                    recyclerview.setVisibility(View.GONE);
                    nocategory.setVisibility(View.VISIBLE);
                    searchlayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setsuggestiondata() {
        suggestionsData = new ArrayList<String>();
        for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
            suggestionsData.add(DB.getInitialModel().getProducts().get(i).getTitle());
        }
        searchlayout.setVisibility(View.VISIBLE);
        recyclerview1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    private void SearchApi(final Context context) {
        SearchString = editsearch.getText().toString();
        SearchPageNumber = 0;
        editsearch.setText("");
        SearchProductsAdapter.data = new ArrayList<Products>();
        Products model = new Products();
        for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
            if (DB.getInitialModel().getProducts().get(i).getTitle().toLowerCase().contains(SearchString.toLowerCase()))
                SearchProductsAdapter.data.add(DB.getInitialModel().getProducts().get(i));
        }
        products = SearchProductsAdapter.data;
        adapter.notifyDataSetChanged();
        if (SearchProductsAdapter.data.size() == 0) {
            recyclerview.setVisibility(View.GONE);
            nocategory.setVisibility(View.VISIBLE);
        } else {
            nocategory.setVisibility(View.GONE);
            searchlayout.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.VISIBLE);
            recyclerview.setAdapter(adapter);
        }
    }

    public static void InitializeAdapter(Context context) {
        searchlayout.setVisibility(View.GONE);
        filterlayout.setVisibility(View.GONE);
        categorylayout.setVisibility(View.GONE);
        productlayout.setVisibility(View.VISIBLE);
        if (item.matches("Price low-high")) {
            ArrayList<Products> sortedproducts = new ArrayList<Products>();
            sortedproducts = new ArrayList<Products>();
            Collections.sort(products, new Comparator<Products>() {
                public int compare(Products p1, Products p2) {
                    if (p1.getSellingPrice() == p2.getSellingPrice())
                        return 0;
                    return p1.getSellingPrice() < p2.getSellingPrice() ? -1 : 1;
                }
            });
        } else {
            ArrayList<Products> sortedproducts = new ArrayList<Products>();
            sortedproducts = new ArrayList<Products>();
            Collections.sort(products, new Comparator<Products>() {
                public int compare(Products p1, Products p2) {
                    if (p1.getSellingPrice() == p2.getSellingPrice())
                        return 0;
                    return p1.getSellingPrice() > p2.getSellingPrice() ? -1 : 1;
                }
            });
        }
        productsrecyclerview.setAdapter(new CatalogueAdapter(context, products));
        if (StaticData.filtermodel.getItem() != null)
            filterrecyclerview.setAdapter(new FilterAdapter(context, StaticData.filtermodel.getItem()));
    }

    public static void InitializeCategoryAdapter(Context context){
            if(catagoriesrecyclerview!=null)
                catagoriesrecyclerview.setAdapter(new SectionlistAdapter(context, categories, layoutManager1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1 = menu.findItem(R.id.slideshow);
        register1.setVisible(false);
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
