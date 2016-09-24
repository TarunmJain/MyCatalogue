package com.centura_technologies.mycatalogue.Sync.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SyncListAdapter extends RecyclerView.Adapter<SyncListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Sections> data;

    public SyncListAdapter(Context context) {
        this.mContext = context;
        this.data = DB.getInitialModel().getSections();
    }

    @Override
    public SyncListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_synclist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SyncListAdapter.ViewHolder holder, int position) {
            holder.textdown.setVisibility(View.GONE);
        if (position == 0) {
            holder.categoryImage.setImageResource(R.drawable.common);
            holder.text.setText("Collections");
            holder.textdown.setText("Collections");
            final int finalPosition1 = position;
            holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.text.getVisibility() == View.VISIBLE)
                    {
                        holder.text.setVisibility(View.GONE);
                        holder.textdown.setVisibility(View.VISIBLE);
                    }

                    else
                    {
                        holder.text.setVisibility(View.VISIBLE);
                        holder.textdown.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            position -= 1;
            GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
            holder.text.setText(data.get(position).getTitle());
            holder.textdown.setText(data.get(position).getTitle());
            final int finalPosition1 = position;
            holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.text.getVisibility() == View.VISIBLE)
                    {
                        holder.text.setVisibility(View.GONE);
                        holder.textdown.setVisibility(View.VISIBLE);
                    }

                    else
                    {
                        holder.text.setVisibility(View.VISIBLE);
                        holder.textdown.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text,textdown;
        CardView relativelayout;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.backlay);
            textdown = (TextView) v.findViewById(R.id.text);
            relativelayout = (CardView) v.findViewById(R.id.relativelayout);
        }
    }
}
