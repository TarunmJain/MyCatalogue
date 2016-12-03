package com.centura_technologies.mycatalogue.Shortlist.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ShortlistProductModel> model;
    public static Double total_amount = 0.0;
    public static TextView grandtotal;
    TextView qtydecrement, qtyincrement;
    Button apply, cancel;
    EditText qtytext;
    //int qty=0;
    Activity a;
    public ShortlistAdapter(final Context context){
        this.mContext=context;
        a = (Activity) mContext;
        this.model= new ArrayList<ShortlistProductModel>();
        this.model= DB.getShortlistproductmodel();
        grandtotal = (TextView) a.findViewById(R.id.grandtotal);
        grandtotal.setText("Rs " + total_amount + "");
    }

    public static void clearBill() {
        total_amount = 0.0;
        grandtotal.setText("Rs " + total_amount + "");
    }

    @Override
    public ShortlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shortlist,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ShortlistAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(model.get(position).getImageUrl(), holder.image, mContext);
        holder.text.setText(model.get(position).getTitle());
        holder.unit.setText(model.get(position).getWeight()+"");
        holder.price.setText(model.get(position).getPrice()+"");
        holder.qty.setText(model.get(position).getQuantity()+"");
        model.get(position).setAmount(model.get(position).getSellingPrice()*Double.parseDouble(model.get(position).getQuantity()+""));
        holder.amount.setText(model.get(position).getAmount()+"");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getShortlistproductmodel().remove(position);
                Shortlist.InitializeAdapter(mContext);
                notifyDataSetChanged();

            }
        });
        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.productposition = position;
                StaticData.SelectedSectionId=model.get(position).getSectionId();
                ((Activity)mContext).startActivity(new Intent(mContext, CatalogueDetails.class));
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
                qtytext.setText(model.get(position).getQuantity()+"");
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
                model.get(position).setQuantity(model.get(position).getQuantity() + 1);
                increment(position);
                notifyDataSetChanged();
                //data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                //notifyDataSetChanged();
            }
        });

        holder.minusincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.get(position).getQuantity() != 0) {
                    model.get(position).setQuantity(model.get(position).getQuantity() - 1);
                    decrement(position);
                    notifyDataSetChanged();
                } else
                    Toast.makeText(mContext, "Min Count is 0", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void increment(int position) {
        total_amount += model.get(position).getPrice();
        grandtotal.setText("Rs " + total_amount + "");
    }

    private void decrement(int position) {
        total_amount -= model.get(position).getPrice();
        grandtotal.setText("Rs " + total_amount + "");
    }

    private void increment(int position, int qty) {
        int actualqty = qty;
        if (model.get(position).getQuantity() > qty) {
            qty = qty - model.get(position).getQuantity();
            total_amount += model.get(position).getPrice() * qty;
        } else {
            qty = model.get(position).getQuantity() - qty;
            total_amount -= model.get(position).getPrice() * qty;
        }
        model.get(position).setQuantity(actualqty);
        grandtotal.setText("Rs " + total_amount + "");
    }


    @Override
    public int getItemCount() {
       /* Collections.sort(model, new Comparator<ShortlistProductModel>() {
            public int compare(ShortlistProductModel v1, ShortlistProductModel v2) {
                if (v1.getTitle() == v2.getTitle())
                    return 0;
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });*/
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,unit,price,amount,qty;
        ImageView image,delete;
        TextView plusincrement, minusincrement;
        LinearLayout pane;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.title);
            unit=(TextView)itemView.findViewById(R.id.unit);
            price=(TextView)itemView.findViewById(R.id.price);
            amount=(TextView)itemView.findViewById(R.id.amount);
            qty=(TextView)itemView.findViewById(R.id.qty);
            plusincrement = (TextView) itemView.findViewById(R.id.plusincrement);
            minusincrement = (TextView) itemView.findViewById(R.id.minusincrement);
            image=(ImageView)itemView.findViewById(R.id.image);
            delete=(ImageView)itemView.findViewById(R.id.delete);
            pane=(LinearLayout)itemView.findViewById(R.id.pane);
        }
    }
}
