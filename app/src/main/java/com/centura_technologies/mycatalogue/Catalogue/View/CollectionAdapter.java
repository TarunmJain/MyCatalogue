package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CollectionModel> data;
    public CollectionAdapter(Context context,ArrayList<CollectionModel> model){
        this.mContext=context;
        this.data=model;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
        holder.backlay.setVisibility(View.GONE);
        if (position == 0) {
            holder.backlay.setText("All Products");
            holder.backlay.setVisibility(View.VISIBLE);
            holder.collectiontitle.setVisibility(View.GONE);
            GenericData.setImage(data.get((position) % 4).getImageUrl(), holder.collectionimage, mContext);
            holder.collectionpane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section="All Products";
                    StaticData.SelectedCategoryId = "-1";
                    BreadCrumb.Category="";
                    if (DB.getInitialModel().getProducts().size() !=- 0) {
                        Intent i=new Intent(new Intent(mContext, Catalogue.class));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                }
            });
        }
else {
            position=position-1;
            holder.collectiontitle.setVisibility(View.VISIBLE);
            holder.collectiontitle.setText(data.get(position).getTitle());
            GenericData.setImage(data.get(position).getImageUrl(), holder.collectionimage, mContext);
            final int finalPosition = position;
            holder.collectionpane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section=data.get(finalPosition).getTitle();
                    BreadCrumb.Category="";
                    StaticData.SelectedCollectionProducts=new ArrayList<String>();
                    StaticData.SelectedCollection=true;
                    StaticData.SelectedCollectionProducts=data.get(finalPosition).getProductIds();
                    mContext.startActivity(new Intent(mContext, Catalogue.class));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectiontitle,backlay;
        ImageView collectionimage;
        CardView collectionpane;
        public ViewHolder(View v) {
            super(v);
            collectiontitle=(TextView)v.findViewById(R.id.text);
            collectionimage=(ImageView)v.findViewById(R.id.image);
            backlay= (TextView) v.findViewById(R.id.backlay);
            collectionpane=(CardView)v.findViewById(R.id.collectionpane);
        }
    }
}
