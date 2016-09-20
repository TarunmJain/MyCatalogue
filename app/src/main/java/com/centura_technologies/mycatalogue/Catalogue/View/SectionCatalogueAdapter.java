package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionCatalogueAdapter extends RecyclerView.Adapter<SectionCatalogueAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Sections> data;

    public SectionCatalogueAdapter(Context context, ArrayList<Sections> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public SectionCatalogueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectioncatalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SectionCatalogueAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
        holder.text.setText(data.get(position).getTitle());
        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.SelectedSectionId = data.get(position).getId();
                StaticData.position=position;
                StaticData.SelectedSection=true;
                if(DB.getInitialModel().getProducts().size()!=0) {
                    mContext.startActivity(new Intent(mContext, Catalogue.class));
                }else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, StaticData.SelectedSectionId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text;
        RelativeLayout relativelayout;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.text);
            relativelayout = (RelativeLayout) v.findViewById(R.id.relativelayout);
        }
    }
}
