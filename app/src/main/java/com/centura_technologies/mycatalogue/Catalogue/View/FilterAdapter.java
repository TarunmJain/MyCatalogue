package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 29-04-2016.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    Context context;
    ArrayList<FilterItem> model;

    public FilterAdapter(Context context, ArrayList<FilterItem> data) {
        this.context = context;
        this.model = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(model.get(position).getTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.recyclerView.getVisibility() == View.VISIBLE)
                    holder.recyclerView.setVisibility(View.GONE);
                else
                    holder.recyclerView.setVisibility(View.VISIBLE);
            }
        });
        int viewHeight = GenericData.convertDpToPixels(60, context);
        viewHeight = viewHeight * ((model.get(position).getValue().size()));
        holder.recyclerView.getLayoutParams().height = viewHeight;
        holder.recyclerView.setAdapter(new FilterItemAdapter(context, model.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        if (model != null)
            return model.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.value);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
