package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 27-10-2016.
 */
public class CollectionnewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CollectionModel> data = new ArrayList<>();
    private int lastPosition = -1;

    public CollectionnewAdapter(Context context) {
        mContext = context;
    }
    public void setData(ArrayList<CollectionModel> mdata) {
        data = mdata;
    }
    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_collection, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.collectiontitle = (TextView) rowView.findViewById(R.id.text);
            viewHolder.collectionimage = (ImageView) rowView.findViewById(R.id.image);
            viewHolder.collectionpane = (CardView) rowView.findViewById(R.id.collectionpane);
            rowView.setTag(viewHolder);
        }

       ViewHolder holder = (ViewHolder) rowView.getTag();

        /*holder.itemView.setVisibility(View.GONE);
        if (position < 6)
            setFadeAnimation(holder.itemView, position);
        else
            holder.itemView.setVisibility(View.VISIBLE);*/
        if (position == 0) {
            holder.collectiontitle.setText("All Products");
            //GenericData.setImage(data.get(position).getImageUrl(), holder.collectionimage, mContext);
            holder.collectionpane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section = "All Products";
                    StaticData.SelectedCategoryId = "-1";
                    BreadCrumb.Category = "";
                    if (DB.getInitialModel().getProducts().size() != -0) {
                        Intent i = new Intent(new Intent(mContext, Catalogue.class));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            position = position - 1;
            holder.collectiontitle.setVisibility(View.VISIBLE);
            holder.collectiontitle.setText(data.get(position).getTitle());
            GenericData.setImage(data.get(position).getImageUrl(), holder.collectionimage, mContext);
            final int finalPosition = position;
            holder.collectionpane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section = data.get(finalPosition).getTitle();
                    BreadCrumb.Category = "";
                    StaticData.SelectedCollectionProducts = new ArrayList<String>();
                    StaticData.SelectedCollection = true;
                    StaticData.SelectedCollectionProducts = data.get(finalPosition).getProductIds();
                    mContext.startActivity(new Intent(mContext, Catalogue.class));
                }
            });
        }

        return rowView;
    }
    static class ViewHolder {
        TextView collectiontitle;
        ImageView collectionimage;
        CardView collectionpane;
    }

    private void animate(View view, final int position) {
        if (position > lastPosition) {
            view.setVisibility(View.VISIBLE);
            view.animate().cancel();
            view.setTranslationY(100);
            view.setAlpha(0);
            view.animate().alpha(1.0f).translationY(0).setDuration(200).setStartDelay(position * 100);
            lastPosition = position;
        }
    }

    private void setFadeAnimation(View view, int position) {
        /*if(position >lastPosition) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(ANIMATION_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }*/
        animate(view, position);

    }
}
