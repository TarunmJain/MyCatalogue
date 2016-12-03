package com.centura_technologies.mycatalogue.Shortlist.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 22-10-2016.
 */
public class CustomerShortlistViewAdapter extends RecyclerView.Adapter<CustomerShortlistViewAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ShortlistProductModel> model;
    public CustomerShortlistViewAdapter(final Context context){
        this.mContext=context;
        this.model= new ArrayList<ShortlistProductModel>();
        this.model= DB.getShortlistModels().get(StaticData.customershortlistpos).getShortlistedproducts();
    }

    @Override
    public CustomerShortlistViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shortlistview,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomerShortlistViewAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(model.get(position).getImageUrl(), holder.image, mContext);
        holder.text.setText(model.get(position).getTitle());
        holder.unit.setText(model.get(position).getWeight()+"");
        holder.price.setText(model.get(position).getPrice()+"");
        holder.amount.setText(model.get(position).getAmount()+"");
        holder.qty.setText(model.get(position).getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,unit,price,amount,qty;
        ImageView image;
        LinearLayout pane;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.title);
            unit=(TextView)itemView.findViewById(R.id.unit);
            price=(TextView)itemView.findViewById(R.id.price);
            amount=(TextView)itemView.findViewById(R.id.amount);
            qty=(TextView)itemView.findViewById(R.id.qty);
            image=(ImageView)itemView.findViewById(R.id.image);
            pane=(LinearLayout)itemView.findViewById(R.id.pane);
        }
    }
}
