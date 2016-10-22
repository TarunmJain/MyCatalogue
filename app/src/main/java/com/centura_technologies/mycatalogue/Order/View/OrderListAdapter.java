package com.centura_technologies.mycatalogue.Order.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Order.Controller.OrdersList;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.Order.Model.OrderModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura on 17-10-2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    Context mContext;
    DbHelper dbHelper;

    public OrderListAdapter(Context context){
        mContext=context;
        dbHelper=new DbHelper(mContext);
    }


    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_grid, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.ViewHolder holder, int position) {
        holder.CustomerName.setText(StaticData.orders.get(position).customer.getName());
        holder.CustomerNumber.setText(StaticData.orders.get(position).customer.getPhone());
        holder.CustomerEmail.setText(StaticData.orders.get(position).customer.getEmail());
        holder.OrderNumber.setText(StaticData.orders.get(position).OrderNumber);
        holder.ProductCount.setText(StaticData.orders.get(position).billingProducts.size()+"");
        holder.GrandTotal.setText(StaticData.orders.get(position).Amount+"");
        holder.OrderDate.setText(StaticData.orders.get(position).OrderDate);
        OnClicks(holder,position);
    }

    private void OnClicks(ViewHolder holder, final int pos) {
        holder.orderdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.orders.remove(pos);
                dbHelper.saveOrders();
                OrdersList.InitializeAdapter(mContext);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return StaticData.orders.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView CustomerName,CustomerNumber,CustomerEmail,OrderNumber,ProductCount,GrandTotal,OrderDate;
        ImageView orderdelete;
        public ViewHolder(View itemView) {
            super(itemView);
            CustomerName = (TextView) itemView.findViewById(R.id.customername);
            CustomerNumber = (TextView) itemView.findViewById(R.id.customernumber);
            CustomerEmail = (TextView) itemView.findViewById(R.id.customeremail);
            OrderNumber = (TextView) itemView.findViewById(R.id.oredernumber);
            ProductCount = (TextView) itemView.findViewById(R.id.productcount);
            GrandTotal = (TextView) itemView.findViewById(R.id.grandtotal);
            OrderDate= (TextView) itemView.findViewById(R.id.orderdate);
            orderdelete=(ImageView)itemView.findViewById(R.id.orderdelete);
        }
    }
}
