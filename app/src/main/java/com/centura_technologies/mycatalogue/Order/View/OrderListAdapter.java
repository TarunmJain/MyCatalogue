package com.centura_technologies.mycatalogue.Order.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Order.Controller.Order;
import com.centura_technologies.mycatalogue.Order.Model.BillingProducts;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 24-09-2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<BillingProducts> data;
    ImageView qtydecrement, qtyincrement;
    Button apply, cancel;
    EditText qtytext;
    int viewHeight;

    public OrderListAdapter(Context context) {
        this.mContext = context;
        this.data = DB.getBillprodlist();
       /* if (Order.shortlistedorders)
            this.data = Order.shorlistedmodel;
        else if (Order.selectedcategories) {
            this.data = Order.billingProductsArrayList;
            Order.selectedcategories = false;
        } else
            this.data = DB.getBillprodlist();*/
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrderListAdapter.ViewHolder holder, final int position) {
        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        if(Order.shortlistedorders)
        {
            if(data.get(position).getQuantity()>0)
            {
                holder.orderlistlayout.setVisibility(View.VISIBLE);
                //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                holder.name.setText(data.get(position).getTitle());
                holder.unit.setText(data.get(position).getWeight() + "");
                holder.qty.setText(data.get(position).getQuantity() + "");
                holder.price.setText(data.get(position).getPrice() + "");
                double amount = data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + "");
                holder.amount.setText(amount + "");
                onClicks(holder, position);
            }
            else holder.orderlistlayout.setVisibility(View.GONE);
        }
        else {
            if(Order.item.matches("-1"))
            {
                holder.orderlistlayout.setVisibility(View.VISIBLE);
                //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                if (data.get(position).getQuantity() > 0)
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.accentcolor));
                else
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.name.setText(data.get(position).getTitle());
                holder.unit.setText(data.get(position).getWeight() + "");
                holder.qty.setText(data.get(position).getQuantity() + "");
                holder.price.setText(data.get(position).getPrice() + "");
                double amount = data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + "");
                holder.amount.setText(amount + "");
                onClicks(holder, position);
            }
            else {
                if(data.get(position).getCategoryId().matches(Order.item))
                {
                    holder.orderlistlayout.setVisibility(View.VISIBLE);
                    //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                    if (data.get(position).getQuantity() > 0)
                        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.accentcolor));
                    else
                        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.name.setText(data.get(position).getTitle());
                    holder.unit.setText(data.get(position).getWeight() + "");
                    holder.qty.setText(data.get(position).getQuantity() + "");
                    holder.price.setText(data.get(position).getPrice() + "");
                    double amount = data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + "");
                    holder.amount.setText(amount + "");
                    onClicks(holder, position);
                }
                else holder.orderlistlayout.setVisibility(View.GONE);
            }
        }
    }

    private void onClicks(final ViewHolder holder, final int position) {
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                notifyDataSetChanged();
                Order.InitializeAdapter(mContext);
            }
        });

        holder.unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                notifyDataSetChanged();
                Order.InitializeAdapter(mContext);
            }
        });

        holder.qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_quantity);
                qtydecrement = (ImageView) dialog.findViewById(R.id.qtydecrement);
                qtyincrement = (ImageView) dialog.findViewById(R.id.qtyincrement);
                qtytext = (EditText) dialog.findViewById(R.id.qtytext);
                apply = (Button) dialog.findViewById(R.id.apply);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                qtytext.setText(data.get(position).getQuantity() + "");
                dialog.show();
                qtydecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.get(position).getQuantity() != 0) {
                            data.get(position).setQuantity(data.get(position).getQuantity() - 1);
                            qtytext.setText(data.get(position).getQuantity() + "");
                        } else
                            Toast.makeText(mContext, "Min Count is 0", Toast.LENGTH_SHORT).show();
                    }
                });
                qtyincrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                        qtytext.setText(data.get(position).getQuantity() + "");
                    }
                });
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.get(position).setQuantity(Integer.parseInt(qtytext.getText().toString()));
                        dialog.cancel();
                        notifyDataSetChanged();
                        Order.InitializeAdapter(mContext);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                notifyDataSetChanged();
                Order.InitializeAdapter(mContext);
            }
        });

        holder.amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity() + 1);
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
        TextView name, unit, qty, price, amount;
        LinearLayout orderlistlayout;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            unit = (TextView) v.findViewById(R.id.unit);
            qty = (TextView) v.findViewById(R.id.qty);
            price = (TextView) v.findViewById(R.id.price);
            amount = (TextView) v.findViewById(R.id.amount);
            orderlistlayout = (LinearLayout) v.findViewById(R.id.orderlistlayout);
        }
    }
}
