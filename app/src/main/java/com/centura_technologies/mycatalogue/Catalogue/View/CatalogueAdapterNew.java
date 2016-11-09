package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

import static com.centura_technologies.mycatalogue.Support.DBHelper.StaticData.context;

/**
 * Created by Centura User1 on 07-11-2016.
 */

public class CatalogueAdapterNew extends BaseAdapter {
    Context mContext;
    RecyclerView product_specification;
    Activity activity;
    ImageView product_image;
    TextView product_title, product_description;
    ArrayList<Products> products;

    public CatalogueAdapterNew(Context context, ArrayList<Products> model) {
        products = new ArrayList<Products>();
        products = model;
        this.mContext = context;
        activity = (Activity) mContext;
        product_image = (ImageView) activity.findViewById(R.id.product_image);
        product_title = (TextView) activity.findViewById(R.id.product_title);
        product_description = (TextView) activity.findViewById(R.id.product_description);
        product_specification = (RecyclerView) activity.findViewById(R.id.product_specification);
        product_specification.setLayoutManager(new LinearLayoutManager(mContext));
        if (products.size() != 0) {
            GenericData.setImage(products.get(0).getImageUrl(), product_image, mContext);
            product_title.setText(products.get(0).getTitle());
            product_description.setText(GenericData.formatHtml(products.get(0).getDescription()));
            product_specification.setAdapter(new DescriptionAdapter(mContext, products.get(0).getAttributes()));
        }
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(mContext);
        } else {
            gridView = (View) convertView;
        }
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.item_catalogue, null);
            TextView title = (TextView) gridView.findViewById(R.id.title);
            ImageView image = (ImageView) gridView.findViewById(R.id.image);
            final ImageView wishlist = (ImageView) gridView.findViewById(R.id.shortlist);
            GenericData.setImage(products.get(position).getImageUrl(), image, mContext);
            title.setText(products.get(position).getTitle());
            if (StaticData.ProductsInList) {
                product_specification.setLayoutManager(new LinearLayoutManager(mContext));
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GenericData.setImage(products.get(position).getImageUrl(), product_image, mContext);
                        product_title.setText(products.get(position).getTitle());
                        product_description.setText(products.get(position).getDescription());
                        product_specification.setAdapter(new DescriptionAdapter(mContext, products.get(position).getAttributes()));
                        product_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StaticData.productposition = position;
                                StaticData.SelectedProductsId = products.get(position).getId();
                                //EventBus.getDefault().postSticky(products);
                                ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
                            }
                        });
                    }
                });
            } else {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StaticData.productposition = position;
                        StaticData.SelectedProductsId = products.get(position).getId();
                        // EventBus.getDefault().postSticky(products);
                        ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
                    }
                });
            }
            for (Products model : DB.getShortlistedlist()) {
                if (model.getId().matches(products.get(position).getId())) {
                    wishlist.setImageResource(R.drawable.ic_cartnew);
                    break;
                }else {
                    wishlist.setImageResource(R.drawable.ic_cartoutline);
                }
            }

            image.setLongClickable(true);
            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean found = false;
                    for (Products model : DB.getShortlistedlist()) {
                        if (model.getId().matches(products.get(position).getId())) {
                            wishlist.setImageResource(R.drawable.ic_cart_outline_grey600_24dp);
                            DB.getShortlistedlist().remove(model);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        wishlist.setImageResource(R.drawable.ic_cartnew);
                        StaticData.Shortlisted = true;
                        DB.shortlistedlist.add(products.get(position));
                    }
                    return true;
                }
            });

            wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        return gridView;
    }
}
