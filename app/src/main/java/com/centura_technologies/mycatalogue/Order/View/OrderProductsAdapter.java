package com.centura_technologies.mycatalogue.Order.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 24-09-2016.
 */
public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.ViewHolder> {
    private static final int DEFAULT_KEYS_DIALER = 0;
    Context mContext;
    ArrayList<BillingProducts> data;
    TextView qtydecrement, qtyincrement;
    Button apply, cancel;
    EditText qtytext;
    public static TextView grandtotal;
    Activity a;
    public static Double total_amount = 0.0;
    int viewHeight;
    int selected_position = 0;
    Double Quantity=0.0;
    private int TYPE_NUMBER_FLAG_DECIMAL=0;

    public OrderProductsAdapter(Context context) {
        this.mContext = context;
        a = (Activity) mContext;
        this.data = DB.getBillprodlist();
        grandtotal = (TextView) a.findViewById(R.id.grandtotal);
        grandtotal.setText("Rs " + total_amount + "");
       /* if (Order.shortlistedorders)
            this.data = Order.shorlistedmodel;
        else if (Order.selectedcategories) {
            this.data = Order.billingProductsArrayList;
            Order.selectedcategories = false;
        } else
            this.data = DB.getBillprodlist();*/
    }

    public static void clearBill() {
        total_amount = 0.0;
        grandtotal.setText("Rs " + total_amount + "");
    }

    @Override
    public OrderProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrderProductsAdapter.ViewHolder holder, final int position) {
        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        if (Order.shortlistedorders) {
            if (data.get(position).getQuantity() > 0) {
                holder.orderlistlayout.setVisibility(View.VISIBLE);
                //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                GenericData.setImage(data.get(position).getImageUrl(),holder.productimage,mContext);
                holder.name.setText(data.get(position).getTitle());
                holder.unit.setText(data.get(position).getWeight() + "");
                holder.qty.setText(data.get(position).getQuantity() + "");
                holder.price.setText(data.get(position).getPrice() + "");
                data.get(position).setAmount(data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + ""));
                holder.amount.setText(data.get(position).getAmount() + "");
                onClicks(holder, position);
            } else holder.orderlistlayout.setVisibility(View.GONE);
        } else {
            if (Order.item.matches("-1")) {
                //all products
                holder.orderlistlayout.setVisibility(View.VISIBLE);
                //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                if (data.get(position).getQuantity() > 0) {
                    holder.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    holder.name.setSelected(true);
                    holder.name.setSingleLine(true);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.selectedcolor));
                } else{
                    holder.name.setEllipsize(TextUtils.TruncateAt.END);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                GenericData.setImage(data.get(position).getImageUrl(),holder.productimage,mContext);
                holder.name.setText(data.get(position).getTitle());
                holder.unit.setText(data.get(position).getWeight() + "");
                holder.qty.setText(data.get(position).getQuantity() + "");
                holder.price.setText(data.get(position).getPrice() + "");
                data.get(position).setAmount(data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + ""));
                holder.amount.setText(data.get(position).getAmount() + "");
                onClicks(holder, position);
            } else {
                if (data.get(position).getCategoryId().matches(Order.item)) {
                    holder.orderlistlayout.setVisibility(View.VISIBLE);
                    //Order.orderlist_recyclerview.getLayoutParams().height += viewHeight;
                    if (data.get(position).getQuantity() > 0) {
                        holder.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        holder.name.setSelected(true);
                        holder.name.setSingleLine(true);
                        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.selectedcolor));
                    } else{
                        holder.name.setEllipsize(TextUtils.TruncateAt.END);
                        holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                    GenericData.setImage(data.get(position).getImageUrl(),holder.productimage,mContext);
                    holder.name.setText(data.get(position).getTitle());
                    holder.unit.setText(data.get(position).getWeight() + "");
                    holder.qty.setText(data.get(position).getQuantity() + "");
                    holder.price.setText(data.get(position).getPrice() + "");
                    data.get(position).setAmount(data.get(position).getSellingPrice() * Double.parseDouble(data.get(position).getQuantity() + ""));
                    holder.amount.setText(data.get(position).getAmount() + "");
                    onClicks(holder, position);
                } else holder.orderlistlayout.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating old as well as new positions
                notifyItemChanged(selected_position);
                selected_position = position;
                notifyItemChanged(selected_position);

                // Do your another stuff for your onClick
            }
        });
    }

    private void increment(int position) {
        total_amount += data.get(position).getPrice();
        grandtotal.setText("Rs " + total_amount + "");
    }

    private void decrement(int position) {
        total_amount -= data.get(position).getPrice();
        grandtotal.setText("Rs " + total_amount + "");
    }

    private void onClicks(final ViewHolder holder, final int position) {
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getQuantity() > 0) {
                    increment(position, 0);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    notifyDataSetChanged();
                } else {
                    data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                    increment(position);
                    notifyDataSetChanged();
                }
            }
        });

        holder.unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getQuantity() > 0) {
                    increment(position, 0);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    notifyDataSetChanged();
                } else {
                    data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                    increment(position);
                    notifyDataSetChanged();
                }
            }
        });

        holder.qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_quantity);
                qtydecrement = (TextView) dialog.findViewById(R.id.qtydecrement);
                qtyincrement = (TextView) dialog.findViewById(R.id.qtyincrement);
                qtytext = (EditText) dialog.findViewById(R.id.qtytext);
                apply = (Button) dialog.findViewById(R.id.apply);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                qtytext.setText(data.get(position).getQuantity() + "");
                dialog.show();
                qtyincrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qtytext.setText((Integer.parseInt(qtytext.getText().toString()) + 1) + "");
                    }
                });
                qtydecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((Integer.parseInt(qtytext.getText().toString())) != 0) {
                            qtytext.setText((Integer.parseInt(qtytext.getText().toString()) - 1) + "");
                        } else
                            Toast.makeText(mContext, "Min Count is 0", Toast.LENGTH_SHORT).show();
                    }
                });
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //data.get(position).setQuantity(Integer.parseInt(qtytext.getText().toString()));
                        increment(position, Integer.parseInt(qtytext.getText().toString()));
                        dialog.cancel();
                        notifyDataSetChanged();
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

        holder.plusincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                increment(position);
                notifyDataSetChanged();
            }
        });

        holder.minusincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getQuantity() != 0) {
                    data.get(position).setQuantity(data.get(position).getQuantity() - 1);
                    decrement(position);
                    notifyDataSetChanged();
                } else
                    Toast.makeText(mContext, "Min Count is 0", Toast.LENGTH_SHORT).show();
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getQuantity() > 0) {
                    increment(position, 0);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    notifyDataSetChanged();
                } else {
                    data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                    increment(position);
                    notifyDataSetChanged();
                }
            }
        });

        holder.amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getQuantity() > 0) {
                    increment(position, 0);
                    holder.orderlistlayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    notifyDataSetChanged();
                } else {
                    data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                    increment(position);
                    notifyDataSetChanged();
                }
            }
        });
    }


    private void increment(int position, int qty) {
        int actualqty = qty;
        if (data.get(position).getQuantity() > qty) {
            qty = qty - data.get(position).getQuantity();
            total_amount += data.get(position).getPrice() * qty;
        } else {
            qty = data.get(position).getQuantity() - qty;
            total_amount -= data.get(position).getPrice() * qty;
        }
        data.get(position).setQuantity(actualqty);
        grandtotal.setText("Rs " + total_amount + "");
    }

    @Override
    public int getItemCount() {
        Collections.sort(data, new Comparator<BillingProducts>() {
            public int compare(BillingProducts v1, BillingProducts v2) {
                if (v1.getTitle() == v2.getTitle())
                    return 0;
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, unit, qty, price, amount;
        TextView plusincrement, minusincrement;
        LinearLayout orderlistlayout;
        ImageView productimage;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            unit = (TextView) v.findViewById(R.id.unit);
            qty = (TextView) v.findViewById(R.id.qty);
            price = (TextView) v.findViewById(R.id.price);
            amount = (TextView) v.findViewById(R.id.amount);
            plusincrement = (TextView) v.findViewById(R.id.plusincrement);
            minusincrement = (TextView) v.findViewById(R.id.minusincrement);
            orderlistlayout = (LinearLayout) v.findViewById(R.id.orderlistlayout);
            productimage=(ImageView)v.findViewById(R.id.productimage);
        }
    }
}
