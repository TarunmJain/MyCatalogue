package com.centura_technologies.mycatalogue.Shortlist.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Products> model;
    RelativeLayout emptyshortlist;
    RecyclerView recyclerView;
    Button shortlistnow;
    public ShortlistAdapter(final Context context){
        this.mContext=context;
        this.model= new ArrayList<Products>();
        this.model= StaticData.wishlistData;
        final Activity a=(Activity)context;
        recyclerView=(RecyclerView)a.findViewById(R.id.shortlistrecyclerview);
        emptyshortlist=(RelativeLayout)a.findViewById(R.id.empty_shortlist);
        shortlistnow=(Button)a.findViewById(R.id.shortlist);
        if(StaticData.wishlistData.size()==0){
            recyclerView.setVisibility(View.GONE);
            emptyshortlist.setVisibility(View.VISIBLE);
        }
        shortlistnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.startActivity(new Intent(mContext,Catalogue.class));
                a.finish();
            }
        });
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.wishlistData.remove(position);
                if (StaticData.wishlistData.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyshortlist.setVisibility(View.VISIBLE);
                }
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
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image,delete;
        RelativeLayout pane;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.title);
            image=(ImageView)itemView.findViewById(R.id.image);
            delete= (ImageView) itemView.findViewById(R.id.delete);
            pane=(RelativeLayout)itemView.findViewById(R.id.pane);
        }
    }
}
