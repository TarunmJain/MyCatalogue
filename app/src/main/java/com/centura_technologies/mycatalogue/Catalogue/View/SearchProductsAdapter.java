package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 14-09-2016.
 */
public class SearchProductsAdapter extends RecyclerView.Adapter<SearchProductsAdapter.ViewHolder> {
    Context mContext;
    public static ArrayList<Products> data;
    SharedPreferences sharedPreferences;
    Button yes, no;
    RequestQueue queue;
    RecyclerView product_specification;
    Activity activity;
    ImageView product_image;
    TextView product_title, product_description;

    public SearchProductsAdapter(Context context) {
        this.mContext = context;
        this.data=new ArrayList<Products>();
        queue = Volley.newRequestQueue(mContext);
        sharedPreferences = mContext.getSharedPreferences(GenericData.MyPref, mContext.MODE_PRIVATE);
        this.mContext = context;
        activity = (Activity) mContext;
        product_image = (ImageView) activity.findViewById(R.id.product_image);
        product_title = (TextView) activity.findViewById(R.id.product_title);
        product_description = (TextView) activity.findViewById(R.id.product_description);
        product_specification = (RecyclerView) activity.findViewById(R.id.product_specification);
        product_specification.setLayoutManager(new LinearLayoutManager(mContext));
        if (data.size() != 0) {
            GenericData.setImage(data.get(0).getImageUrl(), product_image, mContext);
            product_title.setText(data.get(0).getTitle());
            product_description.setText(GenericData.formatHtml(data.get(0).getDescription()));
            product_specification.setAdapter(new DescriptionAdapter(mContext, data.get(0).getAttributes()));
        }
    }
    @Override
    public SearchProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SearchProductsAdapter.ViewHolder holder, final int position) {

        GenericData.setImage(data.get(position).getImageUrl(), holder.image, mContext);
        if (StaticData.ProductsInList) {
            product_specification.setLayoutManager(new LinearLayoutManager(mContext));
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GenericData.setImage(data.get(position).getImageUrl(), product_image, mContext);
                    product_title.setText(data.get(position).getTitle());
                    product_description.setText(data.get(position).getDescription());
                    product_specification.setAdapter(new DescriptionAdapter(mContext, data.get(position).getAttributes()));
                    product_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StaticData.productposition = position;
                            StaticData.SelectedProductsId = data.get(position).getId();
                            //EventBus.getDefault().postSticky(products);
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
                    StaticData.SelectedProductsId = data.get(position).getId();
                    // EventBus.getDefault().postSticky(products);
                    ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }
}
