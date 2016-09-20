package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.AttributeClass;
import com.centura_technologies.mycatalogue.R;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 24-08-2016.
 */
public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.ViewHolder> {
    Context context;
    ArrayList<AttributeClass> description;
    public DescriptionAdapter(Context context, ArrayList<AttributeClass> description) {
        this.context = context;
        this.description = description;
    }

    @Override
    public DescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_desc,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DescriptionAdapter.ViewHolder holder, int position) {
        holder.title.setText(description.get(position).getAttributeName());
        holder.data.setText(description.get(position).getAttributeValue());
    }

    @Override
    public int getItemCount() {
        return description.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,data;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            data = (TextView) itemView.findViewById(R.id.data);
        }
    }
}

