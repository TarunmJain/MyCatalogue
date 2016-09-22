package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.Catalogue.Model.Valuepair;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura on 20-09-2016.
 */
public class TempFilterItemAdapter extends RecyclerView.Adapter<TempFilterItemAdapter.ViewHolder> {
    Context context;
    ArrayList<Valuepair> model;

    public TempFilterItemAdapter(Context context, ArrayList<Valuepair> payment) {
        this.context = context;
        this.model = payment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_item_filterdata, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cat_filterdata.setText(model.get(position).ValueName);
        if (model.get(position).Selected) {
            holder.image.setImageResource(R.drawable.checkcircle);
        } else
            holder.image.setImageResource(R.drawable.check_circle);
        holder.cat_filterdata.setTextColor(Color.parseColor("#737373"));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.get(position).Selected)
                    model.get(position).Selected = false;
                else
                    model.get(position).Selected = true;
                notifyItemChanged(position);
                callFilterApi();
            }
        });
    }

    private void callFilterApi() {
        StaticData.filter="";
        for (FilterItem item : StaticData.filtermodel.getItem()) {
            for (Valuepair pair : item.getValue()) {
                if (pair.Selected) {
                    StaticData.filter += "," + pair.ValueName;
                }
            }
        }
        Toast.makeText(context, StaticData.filter, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cat_filterdata;
        ImageView image;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            cat_filterdata = (TextView) itemView.findViewById(R.id.cat_filterdata);
            image = (ImageView) itemView.findViewById(R.id.cat_filter_checked);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cat_filterdatalay);
        }
    }
}
