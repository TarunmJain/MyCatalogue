package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    ArrayList<CategoryTree> categorylist;
    LinearLayoutManager layoutManager;

    public SectionlistAdapter(Context context, ArrayList<CategoryTree> model, LinearLayoutManager layoutManager1) {
        this.mContext = context;
        this.data = model;
        openedtab = -1;
        layoutManager = layoutManager1;
        /*categorylist = new ArrayList<Categories>();
        for (int i = 0; i < DB.getInitialModel().getCategories().size(); i++) {
            if (DB.getInitialModel().getCategories().get(i).getSectionId().matches(StaticData.SelectedSectionId)) {
                categorylist.add(DB.getInitialModel().getCategories().get(i));
            }
        }*/
    }

    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectionlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SectionlistAdapter.ViewHolder holder, final int position) {
        holder.catagories1.setText(data.get(position).getTitle());
        holder.catagories1.setTextColor(Color.parseColor("#FFFFFF"));
        // holder.catagories1.setTypeface(null, Typeface.NORMAL);
        // for Normal Text
        holder.subCatagoriesView.setAdapter(new CategorylistAdapter(mContext,data.get(position).getCategories()));
        int viewHeight = GenericData.convertDpToPixels(48, mContext);
        viewHeight = viewHeight * categorylist.size();
        holder.subCatagoriesView.getLayoutParams().height = viewHeight;
        holder.subCatagoriesView.setVisibility(View.GONE);
        holder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);
        Anims.setFadeout(mContext, holder.subCatagoriesView);

        if (openedtab == position) {
            // holder.catagories1.setTypeface(null, Typeface.BOLD);
            holder.catagories1.setTextColor(Color.parseColor("#FFFFFF"));
            holder.subCatagoriesView.setVisibility(View.VISIBLE);
            Anims.setfadein(mContext, holder.subCatagoriesView);
            holder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
        }

        /*if (StaticData.SelectedSection) {
            holder.subCatagoriesView.setAdapter(new CategorylistAdapter(mContext));
            int viewHeight1 = GenericData.convertDpToPixels(48, mContext);
            viewHeight1 = viewHeight1 * categorylist.size();
            holder.subCatagoriesView.getLayoutParams().height = viewHeight;
            holder.subCatagoriesView.setVisibility(View.VISIBLE);
        }*/

/*
        //remove if no subcategory
        if(catagories.get(position).getSubCategories().size()==0)
            holder.pane.setVisibility(View.GONE);
        else
            holder.pane.setVisibility(View.VISIBLE);*/

        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categorylist.size() == 0) {
                    //callApi(catagories.get(position).getId(), Urls.CatagoryProducts, "CategoryId");
                } else if (categorylist.size() == 1) {
                    // callApi(catagories.get(position).getSubCategories().get(0).getId(), Urls.SubCatagoryProducts, "SubCategoryId");
                } else {
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
        ImageView arrow;
        ListView subCatagoriesView;
        LinearLayoutManager layoutManager;
        RelativeLayout pane;

        public ViewHolder(View v) {
            super(v);
            layoutManager = new LinearLayoutManager(mContext);
            pane = (RelativeLayout) v.findViewById(R.id.catagoryPane);
            catagories1 = (TextView) v.findViewById(R.id.catagoriestext);
            arrow = (ImageView) v.findViewById(R.id.arrow);
            subCatagoriesView = (ListView) v.findViewById(R.id.subcatagoriesView);
        }
    }
}
