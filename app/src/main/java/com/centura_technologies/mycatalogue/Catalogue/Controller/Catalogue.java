package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.View.CatalogueAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.CategorylistAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.FilterAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SearchAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SearchProductsAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SectionCatalogueAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.SectionlistAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.TempFilterAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Centura User1 on 19-09-2016.
 */
public class Catalogue extends AppCompatActivity {
    Toolbar toolbar;
    static RecyclerView cat_filterlist;
    ImageView listicon;
    public static DrawerLayout drawer;
    public static ImageView searchicon;
    RelativeLayout nocategory;
    RelativeLayout quickview;
    static RelativeLayout fabpane;
    static LinearLayout searchlayout, filterlayout, categorylayout, productlayout;
    public static LinearLayout leftdrawer, rightdrawer;
    public static EditText editsearch;
    Spinner spinner;
    //FloatingActionButton fab;
    TextView apply, clear;
    static RecyclerView recyclerview, recyclerview1, sectionrecycler, categoryrecycler, productsrecyclerview;
    public static SearchProductsAdapter adapter;
    public static SearchAdapter adapter1;
    static LinearLayoutManager layoutManager1;
    ArrayList<String> suggestionsData = new ArrayList<String>();
    public static ArrayList<Products> products;
    public static ArrayList<CategoryTree> categories;
    Products filterprod;
    ArrayList<Products> categoryproducts = new ArrayList<Products>();
    public static ArrayList<Categories> category = new ArrayList<Categories>();
    List<String> sortby = new ArrayList<String>();
    List<String> filterlist;
    RelativeLayout.LayoutParams params;
    RelativeLayout specificationpane;
    static public String SearchString = "";
    static int SearchPageNumber = 0;
    static String item = "";
    TextView filtericon, categoryicon;
    public static TextView catagorybreadcrumb, sectionbreadcrumb, slashbreadcrumb;
    public static TextView nocategorytext;
    public static boolean grid_to_listflag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        slashbreadcrumb = (TextView) findViewById(R.id.slashbreadcrumb);
        catagorybreadcrumb = (TextView) findViewById(R.id.categorybreadcrumb);
        sectionbreadcrumb = (TextView) findViewById(R.id.sectionbreadcrumb);
        toolbar.setTitle("");
        sectionbreadcrumb.setText(BreadCrumb.Section);
        catagorybreadcrumb.setText(BreadCrumb.Category);
        if (BreadCrumb.Category.matches(""))
            slashbreadcrumb.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        leftdrawer = (LinearLayout) findViewById(R.id.leftdrawer);
        rightdrawer = (LinearLayout) findViewById(R.id.rightdrawer);
        filtericon = (TextView) findViewById(R.id.filtericon);
        nocategorytext = (TextView) findViewById(R.id.nocategorytext);
        categoryicon = (TextView) findViewById(R.id.categoryicon);
        listicon = (ImageView) findViewById(R.id.listicon);
        cat_filterlist = (RecyclerView) findViewById(R.id.cat_filterlist);
        searchicon = (ImageView) findViewById(R.id.searchicon);
        quickview = (RelativeLayout) findViewById(R.id.quickview);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        nocategory = (RelativeLayout) findViewById(R.id.nocategory);
        searchlayout = (LinearLayout) findViewById(R.id.searchlayout);
        filterlayout = (LinearLayout) findViewById(R.id.filterlayout);
        categorylayout = (LinearLayout) findViewById(R.id.categorylayout);
        productlayout = (LinearLayout) findViewById(R.id.productlayout);
        fabpane = (RelativeLayout) findViewById(R.id.fabpane);
        specificationpane = (RelativeLayout) findViewById(R.id.specificationpane);
        params = (RelativeLayout.LayoutParams) (specificationpane).getLayoutParams();
        editsearch = (EditText) findViewById(R.id.editsearch);
        spinner = (Spinner) findViewById(R.id.spinner);
        //fab = (FloatingActionButton) findViewById(R.id.fab);
        apply = (TextView) findViewById(R.id.applyfilter);
        clear = (TextView) findViewById(R.id.cancelfiltertest);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
        sectionrecycler = (RecyclerView) findViewById(R.id.sectionrecycler);
        categoryrecycler = (RecyclerView) findViewById(R.id.categoryrecycler);
        productsrecyclerview = (RecyclerView) findViewById(R.id.productsrecyclerview);

        sectionrecycler.setLayoutManager(new LinearLayoutManager(Catalogue.this));
        categoryrecycler.setLayoutManager(new GridLayoutManager(Catalogue.this, 3));
        cat_filterlist.setLayoutManager(new LinearLayoutManager(Catalogue.this));
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
        Sync.syncFilters(Catalogue.this, products);
        StaticData.ProductsInGrid = true;
        StaticData.ProductsInList = false;
        productsrecyclerview.setLayoutManager(new GridLayoutManager(Catalogue.this, 3));
        InitializeAdapter(Catalogue.this);
        InitialzationSectionAdapter(Catalogue.this);
        InitialzationCategoryAdapter(Catalogue.this, null);
    }

    public static void productslist() {
        products = new ArrayList<Products>();
        if (StaticData.SelectedSection) {
            StaticData.SelectedCategoryId = DB.getInitialModel().getCategories().get(0).getId();
        }
        if (StaticData.SelectedCollection) {
            for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
                for (int k = 0; k < StaticData.SelectedCollectionProducts.size(); k++) {
                    if (DB.getInitialModel().getProducts().get(j).getId().matches(StaticData.SelectedCollectionProducts.get(k))) {
                        products.add(DB.getInitialModel().getProducts().get(j));
                        StaticData.SelectedCollection = false;
                    }
                }
            }
        } else if (StaticData.SelectedCategoryId.matches("-1")) {
            for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
                products.add(DB.getInitialModel().getProducts().get(i));
            }
        } else {
            for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
                if (DB.getInitialModel().getProducts().get(i).getCategoryId().matches(StaticData.SelectedCategoryId))
                    products.add(DB.getInitialModel().getProducts().get(i));
            }
        }
    }


    public static void categorylist() {
        categories = new ArrayList<CategoryTree>();
        for (int i = 0; i < DB.getTreelist().size(); i++) {
            categories.add(DB.getTreelist().get(i));
        }
    }

    private void OnClicks() {



        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(rightdrawer);
                drawer.closeDrawer(leftdrawer);
                cat_filterlist.setAdapter(new TempFilterAdapter(Catalogue.this, StaticData.filtermodel.getItem()));
                searchlayout.setVisibility(View.GONE);
            }
        });

        categoryicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
                searchlayout.setVisibility(View.GONE);
                drawer.openDrawer(leftdrawer);
                drawer.closeDrawer(rightdrawer);
                InitialzationSectionAdapter(Catalogue.this);
                InitialzationCategoryAdapter(Catalogue.this, null);
            }
        });

        listicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.GONE);
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
                    setspecificationstoRight();
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
                productlayout.setVisibility(View.GONE);
                SearchApi(Catalogue.this);
            }
        });

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Catalogue.this, Shortlist.class));
                finish();
            }
        });*/

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productslist();
                categoryproducts = new ArrayList<Products>();
                categoryproducts = products;
                if (StaticData.filter != "") {
                    boolean matched = false;
                    filterprod = new Products();
                    products = new ArrayList<Products>();
                    StaticData.filter = StaticData.filter.substring(1, StaticData.filter.length());
                    filterlist = new ArrayList<String>(Arrays.asList(StaticData.filter.split(",")));
                    for (int i = 0; i < categoryproducts.size(); i++) {
                        matched = false;
                        for (int j = 0; j < categoryproducts.get(i).getAttributes().size(); j++) {
                            if (matched)
                                break;
                            for (int k = 0; k < filterlist.size(); k++) {
                                if (categoryproducts.get(i).getAttributes().get(j).getAttributeValue().matches(filterlist.get(k))) {
                                    filterprod = categoryproducts.get(i);
                                    products.add(filterprod);
                                    matched = true;
                                    break;
                                }
                            }
                        }
                    }
                    InitializeAdapter(Catalogue.this);
                    drawer.closeDrawer(rightdrawer);

                } else {
                    Toast.makeText(Catalogue.this, "Filter cannot be apply without selected", Toast.LENGTH_SHORT).show();
                    InitializeAdapter(Catalogue.this);
                    drawer.closeDrawer(rightdrawer);
                }

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.filter = "";
                for (int x = 0; x < StaticData.filtermodel.getItem().size(); x++) {
                    for (int y = 0; y < StaticData.filtermodel.getItem().get(x).getValue().size(); y++) {
                        StaticData.filtermodel.getItem().get(x).getValue().get(y).Selected = false;
                    }
                }
                cat_filterlist.setAdapter(new TempFilterAdapter(Catalogue.this, StaticData.filtermodel.getItem()));
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
                    recyclerview.setVisibility(View.VISIBLE);
                    nocategory.setVisibility(View.GONE);
                    searchlayout.setVisibility(View.GONE);
                    searchicon.performClick();
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

    public static void InitialzationCategoryAdapter(Context context, CategoryTree categoryTree) {
        if (categoryTree == null)
            categoryrecycler.setAdapter(new CategorylistAdapter(context, Catalogue.categories.get(0)));
        else
            categoryrecycler.setAdapter(new CategorylistAdapter(context, categoryTree));
    }

    public static void InitialzationSectionAdapter(Context context) {
        sectionrecycler.setAdapter(new SectionlistAdapter(context, null));
    }

    public static void InitializeAdapter(Context context) {
        searchlayout.setVisibility(View.GONE);
        productlayout.setVisibility(View.VISIBLE);
        if (item.matches("Price low-high")) {
            Collections.sort(products, new Comparator<Products>() {
                public int compare(Products p1, Products p2) {
                    if (p1.getSellingPrice() == p2.getSellingPrice())
                        return 0;
                    return p1.getSellingPrice() < p2.getSellingPrice() ? -1 : 1;
                }
            });
        } else {
            Collections.sort(products, new Comparator<Products>() {
                public int compare(Products p1, Products p2) {
                    if (p1.getSellingPrice() == p2.getSellingPrice())
                        return 0;
                    return p1.getSellingPrice() > p2.getSellingPrice() ? -1 : 1;
                }
            });
        }
        productsrecyclerview.setAdapter(new CatalogueAdapter(context, products));
        Sync.syncFilters(context, products);
        if (StaticData.filtermodel.getItem() != null)
            cat_filterlist.setAdapter(new TempFilterAdapter(context, StaticData.filtermodel.getItem()));
    }

   /* public static void InitializeCategoryAdapter(Context context) {
        if (catagoriesrecyclerview != null)
            catagoriesrecyclerview.setAdapter(new SectionlistAdapter(context, categories, layoutManager1));

    }*/

    void setspecificationsBelow() {
        params.addRule(RelativeLayout.BELOW, R.id.leftlayout);
        params.addRule(RelativeLayout.RIGHT_OF, 0);
        specificationpane.setLayoutParams(params);

    }

    void setspecificationstoRight() {
        params.addRule(RelativeLayout.RIGHT_OF, R.id.leftlayout);
        params.addRule(RelativeLayout.BELOW, 0);
        specificationpane.setLayoutParams(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setVisible(false);
        MenuItem slideshow = menu.findItem(R.id.slideshow);
        slideshow.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == R.id.shortlist) {
            startActivity(new Intent(Catalogue.this, Shortlist.class));
        }
        if (item.getItemId() == android.R.id.home) {
            if (drawer.isDrawerOpen(leftdrawer))//On Back Arrow pressed
                drawer.closeDrawer(leftdrawer);
            else if (drawer.isDrawerOpen(rightdrawer))
                drawer.closeDrawer(rightdrawer);
            else
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
        InitializeAdapter(Catalogue.this);
    }
}
