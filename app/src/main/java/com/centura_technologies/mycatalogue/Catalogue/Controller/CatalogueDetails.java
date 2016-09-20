package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.VarientModel;
import com.centura_technologies.mycatalogue.Catalogue.View.DescriptionAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.DrawerItemsAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.IndividualProdImageAdapter;
import com.centura_technologies.mycatalogue.Catalogue.View.VarientsAdapter;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 24-08-2016.
 */
public class CatalogueDetails extends SwipeActivity implements VarientsAdapter.ClickListner, DrawerLayout.DrawerListener {
    Toolbar toolbar;
    ImageView hamburger, logff;
    TextView Title;
    static RecyclerView productdetaillist, drawer_items_recycler, individual_product_images;
    public static ArrayList<Products> allproducts;
    static ImageView openimage, next, previous, shortlist, media, arrow;
    static TextView title, description, amount, mediatext, variencetext;
    static LinearLayout varients;
    LinearLayout images,videos,pdfs,ppts;
    static Context context;
    static Products productModel;
    static Dialog dialog;
    float Draweropen = 0;
    DrawerLayout drawer;
    public static ArrayList<String> image;
    ScrollView scrollView;
    public static String videourl="";
    public static String pdfurl="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloguedetails);
        Title = (TextView) findViewById(R.id.AppbarTittle);
        Title.setText("Product Details");

        hamburger = (ImageView) findViewById(R.id.hamburger);
        logff = (ImageView) findViewById(R.id.logoff);
        logff.setVisibility(View.GONE);
        context = CatalogueDetails.this;
        allproducts = new ArrayList<Products>();
        // allproducts = EventBus.getDefault().removeStickyEvent(ArrayList.class);
        if (StaticData.ClickedProduct) {
            for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
                if (DB.getInitialModel().getProducts().get(i).getSectionId().matches(StaticData.SelectedSectionId)) {
                    allproducts.add(DB.getInitialModel().getProducts().get(i));
                }
            }
        } else {
            /*for (int i = 0; i < DB.getInitialModel().getProducts().size(); i++) {
                if (DB.getInitialModel().getProducts().get(i).getCategoryId().matches(StaticData.SelectedCategoryId)) {
                    allproducts.add(DB.getInitialModel().getProducts().get(i));
                }
            }*/
            allproducts=Catalogue.products;
        }

        productdetaillist = (RecyclerView) findViewById(R.id.productdetaillist);
        individual_product_images = (RecyclerView) findViewById(R.id.individual_product_images);
        drawer_items_recycler = (RecyclerView) findViewById(R.id.drawer_items_recycler1);
        openimage = (ImageView) findViewById(R.id.openimage);
        variencetext = (TextView) findViewById(R.id.variencetext);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        amount = (TextView) findViewById(R.id.amount);
        varients = (LinearLayout) findViewById(R.id.varients);
        arrow = (ImageView) findViewById(R.id.arrow);
        shortlist = (ImageView) findViewById(R.id.shortlist);
        mediatext = (TextView) findViewById(R.id.mediatext);
        media = (ImageView) findViewById(R.id.media);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        if (allproducts != null)
            if (allproducts.size() > 0)
                RenderProduct(allproducts.get(StaticData.productposition));
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerListener(this);

        if (allproducts.size() == 1) {
            next.setVisibility(View.GONE);
            previous.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
        }
        setOnClicks();

    }

    @Override
    protected void previous() {
        if (Draweropen == 0) {
            if (StaticData.productposition > 0)
                RenderProduct(allproducts.get(--StaticData.productposition));
        } else
            drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void next() {
        if (Draweropen == 0) {
            if (StaticData.productposition < allproducts.size() - 1)
                RenderProduct(allproducts.get(++StaticData.productposition));
        } else
            drawer.closeDrawer(Gravity.LEFT);
    }

    private void setOnClicks() {

        openimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().postSticky(productModel.getOtherImages());
                StaticData.SelectedProductImage = true;
                startActivity(new Intent(CatalogueDetails.this, ImageViewer.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticData.productposition < allproducts.size() - 1) {
                    scrollView.pageScroll(View.FOCUS_UP);
                    RenderProduct(allproducts.get(++StaticData.productposition));
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticData.productposition > 0) {
                    scrollView.pageScroll(View.FOCUS_UP);
                    RenderProduct(allproducts.get(--StaticData.productposition));
                }
            }
        });

        mediatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CatalogueDetails.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.media);
                images=(LinearLayout)dialog.findViewById(R.id.image);
                videos=(LinearLayout)dialog.findViewById(R.id.video);
                pdfs=(LinearLayout)dialog.findViewById(R.id.pdf);
                ppts=(LinearLayout)dialog.findViewById(R.id.ppt);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
               /* lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;*/
                lp.width = GenericData.convertDpToPixels(500, CatalogueDetails.this);
                lp.height = GenericData.convertDpToPixels(500, CatalogueDetails.this);
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(CatalogueDetails.this,ImageActivity.class));
                    }
                });
                videos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videourl=allproducts.get(StaticData.position).getVideoUrl();
                        startActivity(new Intent(CatalogueDetails.this,VideoActivity.class));
                    }
                });
                pdfs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pdfurl=allproducts.get(StaticData.position).getPdfUrl();
                        startActivity(new Intent(CatalogueDetails.this,PdfActivity.class));
                    }
                });
                ppts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void RenderProduct(final Products productdetail) {
        productModel = productdetail;
        image = new ArrayList<String>();
        for (int i = 0; i < productModel.getProductImages().size(); i++)
            image.add(productModel.getProductImages().get(i));
        if (productModel.getVariants().size() == 0) {
            varients.setVisibility(View.GONE);
        } else {
            varients.setVisibility(View.VISIBLE);
            variencetext.setText(productModel.getSelectedVarient().toString());
        }
        varients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productModel.getVariants().size() == 0) {
                    varients.setVisibility(View.GONE);
                } else if (productModel.getVariants().size() == 1) {
                    varients.setVisibility(View.VISIBLE);
                    VarientsDialog(productModel.getVariants());
                    variencetext.setText(productModel.getSelectedVarient());
                } else {
                    VarientsDialog(productModel.getVariants());
                    variencetext.setText(productModel.getSelectedVarient());
                }
            }
        });

        GenericData.setImage(productModel.getImageUrl(), openimage, context);
        title.setText(productModel.getTitle());
        description.setText(GenericData.formatHtml(productModel.getDescription()));
        amount.setText(productModel.getSellingPrice() + "");
        productdetaillist.setLayoutManager(new LinearLayoutManager(context));
        int viewHeight = GenericData.convertDpToPixels(50, context);
        viewHeight = viewHeight * (productModel.getAttributes().size());
        productdetaillist.getLayoutParams().height = viewHeight;
        productdetaillist.setAdapter(new DescriptionAdapter(context, productModel.getAttributes()));

        drawer_items_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        drawer_items_recycler.setAdapter(new DrawerItemsAdapter(context, allproducts));

        scrollchild();
        LinearLayoutManager layoutManager1 = new GridLayoutManager(context, 2);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        individual_product_images.setLayoutManager(layoutManager1);
        /*int viewHeight1 = GenericData.convertDpToPixels(50, context);
        viewHeight1 = viewHeight1 * (productModel.getProductImages().size());
        individual_product_images.getLayoutParams().height = viewHeight;*/
        individual_product_images.setAdapter(new IndividualProdImageAdapter(context, productModel.getProductImages(), openimage));
        productClicks(productdetail);

    }

    private static void productClicks(final Products productdetail) {
        for (Products model : StaticData.wishlistData) {
            if (model.getId().matches(productdetail.getId())) {
                if (StaticData.Shortlisted) {
                    shortlist.setImageResource(R.drawable.heart374);
                    break;
                }
            }
        }
        shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                for (Products model : StaticData.wishlistData) {
                    if (model.getId().matches(productdetail.getId())) {
                        shortlist.setImageResource(R.drawable.favorite7);
                        StaticData.wishlistData.remove(model);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    shortlist.setImageResource(R.drawable.heart374);
                    StaticData.wishlistData.add(productdetail);
                }
            }
        });
    }

    private static void scrollchild() {
        productdetaillist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private static void VarientsDialog(ArrayList<VarientModel> model) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_varients_dropdown);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.varientsrecycle);
        switch (model.size()) {
            case 1:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(50, context);
                break;
            case 2:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(100, context);
                break;
            case 3:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(150, context);
                break;
            case 4:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(200, context);
                break;
            case 5:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(150, context);
                break;
            default:
                recyclerView.getLayoutParams().height = GenericData.convertDpToPixels(300, context);
                break;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        VarientsAdapter adapter = new VarientsAdapter(context, model);
        adapter.setClickListner((VarientsAdapter.ClickListner) context);
        recyclerView.setAdapter(adapter);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void itemClicked(View v, int position) {
        dialog.cancel();
        productModel.setSelectedVarient(productModel.getVariants().get(position).getVariance());
        variencetext.setText(productModel.getSelectedVarient());
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        Draweropen = slideOffset;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
