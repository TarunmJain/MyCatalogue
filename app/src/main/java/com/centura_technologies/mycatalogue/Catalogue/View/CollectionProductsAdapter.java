package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Collection;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 21-11-2016.
 */

public class CollectionProductsAdapter extends RecyclerView.Adapter<CollectionProductsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CollectionModel> data;

    public CollectionProductsAdapter(Context context, ArrayList<CollectionModel> model) {
        this.mContext = context;
        this.data = model;
        if (data.size() > 0) {
            StaticData.SelectedCollectionProducts = new ArrayList<String>();
            StaticData.SelectedCollection = true;
            StaticData.SelectedCollectionProducts = data.get(0).getProductIds();
            Collection.collectionproducts_recyclerview.setAdapter(new CollectionProductViewAdapter(mContext, StaticData.SelectedCollectionProducts));
        }
    }

    @Override
    public CollectionProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectionproducts, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionProductsAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(data.get(position).getImageUrl(), holder.image, mContext);
        holder.title.setText(data.get(position).getTitle());
        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.SelectedCollectionProducts = new ArrayList<String>();
                StaticData.SelectedCollection = true;
                StaticData.SelectedCollectionProducts = data.get(position).getProductIds();
                Collection.collectionproducts_recyclerview.setAdapter(new CollectionProductViewAdapter(mContext, StaticData.SelectedCollectionProducts));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout pane;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            image = (ImageView) v.findViewById(R.id.image);
            pane = (RelativeLayout) v.findViewById(R.id.pane);
        }
    }
}
