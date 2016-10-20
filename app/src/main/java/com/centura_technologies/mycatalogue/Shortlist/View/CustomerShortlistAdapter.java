package com.centura_technologies.mycatalogue.Shortlist.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 20-10-2016.
 */
public class CustomerShortlistAdapter extends RecyclerView.Adapter<CustomerShortlistAdapter.ViewHolder> {
    Context mContext;
    public CustomerShortlistAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public CustomerShortlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customershortlist,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomerShortlistAdapter.ViewHolder holder, int position) {
        holder.CustomerName.setText(DB.getShortlistModels().get(position).customer.getName());
        holder.CustomerNumber.setText(DB.getShortlistModels().get(position).customer.getPhone());
        holder.CustomerEmail.setText(DB.getShortlistModels().get(position).customer.getEmail());
        holder.shortlistNumber.setText(DB.getShortlistModels().get(position).ShortlistNumber);
        holder.ProductCount.setText(DB.getShortlistModels().get(position).getShortlistedproducts().size()+"");
        holder.shortlistDate.setText(DB.getShortlistModels().get(position).ShortlistedDate);
    }

    @Override
    public int getItemCount() {
        return DB.getShortlistModels().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CustomerName,CustomerNumber,CustomerEmail,shortlistNumber,ProductCount,shortlistDate;
        public ViewHolder(View itemView) {
            super(itemView);
            CustomerName = (TextView) itemView.findViewById(R.id.customername);
            CustomerNumber = (TextView) itemView.findViewById(R.id.customernumber);
            CustomerEmail = (TextView) itemView.findViewById(R.id.customeremail);
            shortlistNumber = (TextView) itemView.findViewById(R.id.shortlistnumber);
            ProductCount = (TextView) itemView.findViewById(R.id.productcount);
            shortlistDate= (TextView) itemView.findViewById(R.id.shortlistdate);
        }
    }
}
