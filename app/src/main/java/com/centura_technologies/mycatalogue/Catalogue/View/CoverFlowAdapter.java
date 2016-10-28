package com.centura_technologies.mycatalogue.Catalogue.View;

/**
 * Created by Centura on 27-10-2016.
 */import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<CollectionModel> data;
    private Context mContext;

    public CoverFlowAdapter(Context context, ArrayList<CollectionModel> objects) {
        this.mContext = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_collection, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.collectiontitle.setVisibility(View.VISIBLE);
        viewHolder.collectiontitle.setText(data.get(position).getTitle());
        GenericData.setImage(data.get(position).getImageUrl(), viewHolder.collectionimage, mContext);
        convertView.setOnClickListener(onClickListener(position));
        return convertView;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BreadCrumb.Section = data.get(position).getTitle();
                BreadCrumb.Category = "";
                StaticData.SelectedCollectionProducts = new ArrayList<String>();
                StaticData.SelectedCollection = true;
                StaticData.SelectedCollectionProducts = data.get(position).getProductIds();
                mContext.startActivity(new Intent(mContext, Catalogue.class));
            }
        };
    }


    private static class ViewHolder {
        TextView collectiontitle;
        ImageView collectionimage;
        CardView collectionpane;

        public ViewHolder(View v) {
            collectiontitle = (TextView) v.findViewById(R.id.text);
            collectionimage = (ImageView) v.findViewById(R.id.image);
            collectionpane = (CardView) v.findViewById(R.id.collectionpane);
        }
    }
}