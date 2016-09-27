package com.centura_technologies.mycatalogue.Order.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Order.Controller.Order;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 24-09-2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<BillingProducts> data;
    int qty;
    public OrderListAdapter(Context context,ArrayList<BillingProducts> model){
        this.mContext=context;
        this.data= model;
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getTitle());
        holder.unit.setText(data.get(position).getWeight() + "");
        holder.qty.setText(data.get(position).getQuantity()+"");
        holder.price.setText(data.get(position).getPrice() + "");
        double amount=data.get(position).getSellingPrice()*Double.parseDouble(data.get(position).getQuantity()+"");
        holder.amount.setText(amount+"");
        holder.orderlistlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity()+1);
                notifyDataSetChanged();
                Order.InitializeAdapter(mContext);
            }
        });
        holder.qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity()+1);
                notifyDataSetChanged();
                Order.InitializeAdapter(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,unit,qty,price,amount;
        LinearLayout orderlistlayout;
        public ViewHolder(View v) {
            super(v);
            name=(TextView)v.findViewById(R.id.name);
            unit=(TextView)v.findViewById(R.id.unit);
            qty=(TextView)v.findViewById(R.id.qty);
            price=(TextView)v.findViewById(R.id.price);
            amount=(TextView)v.findViewById(R.id.amount);
            orderlistlayout=(LinearLayout)v.findViewById(R.id.orderlistlayout);
        }
    }
}
