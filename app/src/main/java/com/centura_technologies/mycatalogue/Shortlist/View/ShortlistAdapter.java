package com.centura_technologies.mycatalogue.Shortlist.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Products> model;
    public ShortlistAdapter(final Context context){
        this.mContext=context;
        this.model= new ArrayList<Products>();
        this.model= DB.getShortlistedlist();
    }

    @Override
    public ShortlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shortlist,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShortlistAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(model.get(position).getImageUrl(), holder.image, mContext);
        holder.text.setText(model.get(position).getTitle());
        for(int i=0;i<DB.getInitialModel().getSections().size();i++){
            if(DB.getInitialModel().getSections().get(i).getId().matches(model.get(position).getSectionId())){
                holder.section.setText(DB.getInitialModel().getSections().get(i).getTitle());
            }
        }
        for(int j=0;j<DB.getInitialModel().getCategories().size();j++){
            if(DB.getInitialModel().getCategories().get(j).getId().matches(model.get(position).getCategoryId())){
                holder.category.setText(DB.getInitialModel().getCategories().get(j).getTitle());
            }
        }
        holder.price.setText(model.get(position).getSellingPrice()+"");
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getShortlistedlist().remove(position);
                Shortlist.InitializeAdapter(mContext);
                notifyDataSetChanged();

            }
        });
        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.productposition = position;
                StaticData.SelectedProductsId=model.get(position).getId();
                ((Activity)mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        Collections.sort(model, new Comparator<Products>() {
            public int compare(Products v1, Products v2) {
                if (v1.getTitle() == v2.getTitle())
                    return 0;
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,section,category,price;
        ImageView image;
        LinearLayout pane;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.title);
            section=(TextView)itemView.findViewById(R.id.section);
            category=(TextView)itemView.findViewById(R.id.category);
            price=(TextView)itemView.findViewById(R.id.price);
            image=(ImageView)itemView.findViewById(R.id.image);
            pane=(LinearLayout)itemView.findViewById(R.id.pane);
        }
    }
}
