package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttachmentGroup;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttchmentClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class AttchmentsAdapter extends RecyclerView.Adapter<AttchmentsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<AttachmentGroup> data;
    ArrayList<AttchmentClass> model;
    int GroupView = -1;

    public AttchmentsAdapter(Context context, ArrayList<AttachmentGroup> tree) {
        this.mContext = context;
        this.data = tree;
    }

    @Override
    public AttchmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attchmentview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AttchmentsAdapter.ViewHolder holder, final int position) {

        holder.SubCatagorieslist.setVisibility(View.GONE);
        holder.SubCatagorieslist.setLayoutManager(new LinearLayoutManager(mContext));

        if (position == 0) {
            holder.text.setText("Info");
            holder.icon.setImageResource(R.drawable.ic_infoicon);
        } else
        {
            holder.text.setText(data.get(position - 1).getGroupTitle());
            holder.icon.setImageResource(R.drawable.mr_ic_play_light);
        }

        final int finalPosition = position - 1;
        if (GroupView == position) {
            if (position == 0) {
                holder.SubCatagorieslist.setVisibility(View.GONE);
                CatalogueDetails.LoadInfo();
            } else {
                holder.SubCatagorieslist.setVisibility(View.VISIBLE);
                holder.SubCatagorieslist.setAdapter(new MedialistAdapter(mContext, data.get(finalPosition)));
                int viewHeight = GenericData.convertDpToPixels(37, mContext);
                viewHeight = viewHeight * (data.get(finalPosition).getAttchments().size());
                holder.SubCatagorieslist.getLayoutParams().height = viewHeight;
            }
        } else
            holder.SubCatagorieslist.setVisibility(View.GONE);

        holder.layview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    GroupView = position;
                    notifyDataSetChanged();
                } else if (position == GroupView) {
                    GroupView = -1;
                    notifyDataSetChanged();
                } else if (data.get(finalPosition).getAttchments().size() > 0) {
                    GroupView = position;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        Collections.sort(data, new Comparator<AttachmentGroup>() {
            public int compare(AttachmentGroup v1, AttachmentGroup v2) {
                if (v1.getGroupTitle().toLowerCase() == v2.getGroupTitle().toLowerCase())
                    return 0;
                return v1.getGroupTitle().toLowerCase().compareTo(v2.getGroupTitle().toLowerCase());
            }
        });
        return data.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView icon;
        RecyclerView SubCatagorieslist;
        MaterialRippleLayout layview;

        public ViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon);
            text = (TextView) v.findViewById(R.id.text);
            layview = (MaterialRippleLayout) v.findViewById(R.id.layview);
            SubCatagorieslist = (RecyclerView) v.findViewById(R.id.SubCatagorieslist);
        }
    }
}
