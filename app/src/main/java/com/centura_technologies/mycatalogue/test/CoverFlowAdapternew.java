package com.centura_technologies.mycatalogue.test;

/**
 * Created by Centura on 27-10-2016.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class CoverFlowAdapternew extends BaseAdapter {

    private ArrayList<CollectionModel> data;
    private Context activity;

    public CoverFlowAdapternew(Context context, ArrayList<CollectionModel> objects) {
        this.activity = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public CollectionModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            GenericData.setImage(data.get(position).getImageUrl(), viewHolder.gameImage, activity);
            viewHolder.gameName.setText("All Products");
        } else {
            GenericData.setImage(data.get(position-1).getImageUrl(), viewHolder.gameImage, activity);
            viewHolder.gameName.setText(data.get(position-1).getTitle());
        }
        convertView.setOnClickListener(onClickListener(position));
        return convertView;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(position==0)
                {
                    BreadCrumb.Section = "All Products";
                    StaticData.SelectedCategoryId = "-1";
                    BreadCrumb.Category = "";
                    if (DB.getInitialModel().getProducts().size() != -0) {
                        Intent i = new Intent(activity, Catalogue.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity)activity).startActivity(i);
                    } else Toast.makeText(activity, "No Products", Toast.LENGTH_SHORT).show();
                }else {
                    BreadCrumb.Section = data.get(position-1).getTitle();
                    BreadCrumb.Category = "";
                    StaticData.SelectedCollectionProducts = new ArrayList<String>();
                    StaticData.SelectedCollection = true;
                    StaticData.SelectedCollectionProducts = data.get(position-1).getProductIds();
                    ((Activity)activity).startActivity(new Intent(activity, Catalogue.class));
                }
            }
        };
    }


    private static class ViewHolder {
        private TextView gameName;
        private ImageView gameImage;

        public ViewHolder(View v) {
            gameImage = (ImageView) v.findViewById(R.id.image);
            gameName = (TextView) v.findViewById(R.id.name);
        }
    }
}