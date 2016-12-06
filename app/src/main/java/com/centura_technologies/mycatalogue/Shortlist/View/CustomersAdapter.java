package com.centura_technologies.mycatalogue.Shortlist.View;

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
import com.centura_technologies.mycatalogue.Catalogue.Model.CustomerModel;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {
    Context mContext;

    public CustomersAdapter(final Context context) {
        this.mContext = context;
    }

    @Override
    public CustomersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customerlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomersAdapter.ViewHolder holder, final int position) {
        holder.text.setText(StaticData.TempCustomers.get(position).getName());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.SelectedCustomers=new CustomerModel();
                StaticData.SelectedCustomers = StaticData.TempCustomers.get(position);
                Shortlist.customername.setText(StaticData.SelectedCustomers.getName());
                Shortlist.customerdisplay.setText(StaticData.SelectedCustomers.getName());
                Shortlist.searchcustomerslayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StaticData.TempCustomers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
