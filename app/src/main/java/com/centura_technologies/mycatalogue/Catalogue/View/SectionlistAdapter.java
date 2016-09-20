package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Anims;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionlistAdapter extends RecyclerView.Adapter<SectionlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CategoryTree> data;
    int openedtab;
    LinearLayoutManager layoutManager;

    public SectionlistAdapter(Context context, ArrayList<CategoryTree> model, LinearLayoutManager layoutManager1) {
        this.mContext = context;
        this.data = model;
        openedtab = -1;
        layoutManager = layoutManager1;
    }

    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectionlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SectionlistAdapter.ViewHolder holder, final int position) {
        String catagorystring = data.get(position).getTitle();
        holder.catagories1.setText(catagorystring);
        GenericData.setImage(data.get(position).getImageUrl(), holder.icon,mContext);
        holder.gridView.setAdapter(new CategorylistAdapter(mContext, data.get(position).getCategories()));
        int viewHeight = GenericData.convertDpToPixels(460, mContext);
        viewHeight = viewHeight * ((data.get(position).getCategories().size() + 1) / 3);
        holder.gridView.getLayoutParams().height = viewHeight;
        holder.gridView.setVisibility(View.VISIBLE);
        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getCategories().size() == 0) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                    if (openedtab != position) {
                        openedtab = position;
                        notifyDataSetChanged();
                    } else {
                        openedtab = -1;
                        notifyDataSetChanged();
                    }
                    //callApi(catagories.get(position).getId(), Urls.CatagoryProducts, "CategoryId");
                } else if (data.get(position).getCategories().size() == 1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                    if (openedtab != position) {
                        openedtab = position;
                        notifyDataSetChanged();
                    } else {
                        openedtab = -1;
                        notifyDataSetChanged();
                    }
                    //callApi(catagories.get(position).getSubCategories().get(0).getId(), Urls.SubCatagoryProducts, "SubCategoryId");
                }else {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                    if (openedtab != position) {
                        openedtab = position;
                        notifyDataSetChanged();
                    } else {
                        openedtab = -1;
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catagories1;
        ImageView icon;
        GridView gridView;
        LinearLayoutManager layoutManager;
        CardView pane;

        public ViewHolder(View v) {
            super(v);
            layoutManager = new LinearLayoutManager(mContext);
            pane = (CardView) itemView.findViewById(R.id.catagoryPane);
            catagories1 = (TextView) itemView.findViewById(R.id.catagoriestext);
            icon = (ImageView) itemView.findViewById(R.id.catagoriesimage);
            gridView = (GridView) itemView.findViewById(R.id.gridview);
        }
    }
}
