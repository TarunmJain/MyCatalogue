package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Centura User1 on 23-08-2016.
 */
public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
    Context mContext;
    RecyclerView product_specification;
    Activity activity;
    ImageView product_image;
    TextView product_title, product_description;
    ArrayList<Products> products;
    //ArrayList<Products> shortlist;

    public CatalogueAdapter(Context context, ArrayList<Products> model) {
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
    public CatalogueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CatalogueAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(products.get(position).getImageUrl(), holder.image, mContext);
        if (StaticData.ProductsInList) {
            product_specification.setLayoutManager(new LinearLayoutManager(mContext));
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GenericData.setThumbImage(products.get(position).getImageUrl(), product_image, mContext);
                    product_title.setText(products.get(position).getTitle());
                    product_description.setText(products.get(position).getDescription());
                    product_specification.setAdapter(new DescriptionAdapter(mContext, products.get(position).getAttributes()));
                    product_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StaticData.productposition = position;
                            StaticData.SelectedProductsId = products.get(position).getId();
                            //EventBus.getNDefault().postSticky(products);
                            ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
                        }
                    });
                }
            });
        } else {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StaticData.productposition = position;
                    StaticData.SelectedProductsId = products.get(position).getId();
                    ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }
}
