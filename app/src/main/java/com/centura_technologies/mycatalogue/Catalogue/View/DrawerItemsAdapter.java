package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 27-04-2016.
 */
public class DrawerItemsAdapter extends RecyclerView.Adapter<DrawerItemsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Products> productsmodel;
    public DrawerItemsAdapter(Context context, ArrayList<Products> allproducts) {
        this.mContext = context;
        productsmodel = allproducts;
    }

    @Override
    public DrawerItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_drawer,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DrawerItemsAdapter.ViewHolder holder, final int position) {
        holder.drawer_item_title.setText(GenericData.Concatinate(productsmodel.get(position).getTitle(), 20));
        GenericData.setImage(productsmodel.get(position).getImageUrl(), holder.drawer_item_image, mContext);
        holder.drawer_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogueDetails.RenderProduct(productsmodel.get(position));
                StaticData.productposition=position;
            }
        });
    }




    @Override
    public int getItemCount() {
        return productsmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView drawer_item_title;
        ImageView drawer_item_image;
        public ViewHolder(View itemView) {
            super(itemView);
            drawer_item_title = (TextView) itemView.findViewById(R.id.drawer_item_title);
            drawer_item_image = (ImageView) itemView.findViewById(R.id.drawer_item_image);

        }
    }
}
