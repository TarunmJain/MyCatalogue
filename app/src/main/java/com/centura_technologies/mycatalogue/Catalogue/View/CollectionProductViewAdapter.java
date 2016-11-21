package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 21-11-2016.
 */

public class CollectionProductViewAdapter extends RecyclerView.Adapter<CollectionProductViewAdapter.ViewHolder> {
    Context mContext;
    ArrayList<String> prodlist;
    ArrayList<Products> model;
    public CollectionProductViewAdapter(Context context, ArrayList<String> list){
        this.mContext=context;
        this.prodlist=list;
        model=new ArrayList<Products>();
        if (StaticData.SelectedCollection) {
            for (int j = 0; j < DB.getInitialModel().getProducts().size(); j++) {
                for (int k = 0; k < prodlist.size(); k++) {
                    if (DB.getInitialModel().getProducts().get(j).getId().matches(prodlist.get(k))) {
                        model.add(DB.getInitialModel().getProducts().get(j));
                        StaticData.SelectedCollection = false;
                    }
                }
            }
        }
    }
    @Override
    public CollectionProductViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectionproductview,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionProductViewAdapter.ViewHolder holder, int position) {
        position=position%2;
        if(position==0){

        }
        GenericData.setImage(model.get(position).getImageUrl(),holder.imageView,mContext);
        holder.prodtitle.setText(model.get(position).getTitle());
        holder.proddescription.setText(GenericData.formatHtml(model.get(position).getDescription()));
        holder.prodprice.setText(model.get(position).getSellingPrice()+"");
        final int finalPosition = position;
        holder.prodpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.productposition = finalPosition;
                StaticData.SelectedProductsId = model.get(finalPosition).getId();
                //EventBus.getDefault().postSticky(products);
                ((Activity) mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prodtitle,proddescription,prodprice;
        ImageView imageView;
        LinearLayout prodpane;
        public ViewHolder(View v) {
            super(v);
            prodtitle=(TextView)v.findViewById(R.id.prodtitle);
            proddescription=(TextView)v.findViewById(R.id.proddescription);
            prodprice=(TextView)v.findViewById(R.id.prodprice);
            imageView=(ImageView) v.findViewById(R.id.imageView);
            prodpane=(LinearLayout)v.findViewById(R.id.prodpane);
        }
    }
}
