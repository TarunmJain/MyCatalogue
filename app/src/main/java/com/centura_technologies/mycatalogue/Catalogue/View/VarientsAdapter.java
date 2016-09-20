package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.centura_technologies.mycatalogue.Catalogue.Model.VarientModel;
import com.centura_technologies.mycatalogue.R;

import java.util.ArrayList;

/**
 * Created by Centura on 28-04-2016.
 */
public class VarientsAdapter extends RecyclerView.Adapter<VarientsAdapter.ViewHolder> {
    Context context;
    ClickListner clickListner;
    ArrayList<VarientModel> model;
    public VarientsAdapter(Context context, ArrayList<VarientModel> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public VarientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.varient_item,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VarientsAdapter.ViewHolder holder, final int position) {
        holder.varienttext.setText(model.get(position).getVariance());
        holder.price.setText(model.get(position).getSellingPrice()+"");
        holder.varientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListner != null)
                    clickListner.itemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView varienttext,price;
        RelativeLayout varientLayout;
        public ClickListner clickListner;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            varienttext = (TextView) itemView.findViewById(R.id.varienttext);
            price = (TextView) itemView.findViewById(R.id.price);
            varientLayout= (RelativeLayout) itemView.findViewById(R.id.varientLayout);
        }

        @Override
        public void onClick(View v) {
            if(clickListner!=null)
                clickListner.itemClicked(v,getAdapterPosition());
        }
    }

    public void setClickListner(ClickListner clickListner){
        this.clickListner=clickListner;
    }
    public interface ClickListner{
        public void itemClicked(View v, int position);
    }

}
