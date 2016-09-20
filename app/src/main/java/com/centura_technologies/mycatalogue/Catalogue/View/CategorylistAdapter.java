package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class CategorylistAdapter  extends BaseAdapter {

    ArrayList<Categories> categorylist;
    Context mContext;
    LayoutInflater li;

    public CategorylistAdapter(Context context,ArrayList<Categories> model) {
        this.categorylist =model;
        mContext = context;
        /*for (int i = 0; i < DB.getInitialModel().getCategories().size(); i++) {
            if (DB.getInitialModel().getCategories().get(i).getSectionId().matches(StaticData.SelectedSectionId)) {
                categorylist.add(DB.getInitialModel().getCategories().get(i));
            }
        }*/
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categorylist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View vi = view;
        ViewHolder holder;
        if (vi == null) {
            vi = li.inflate(R.layout.item_categorylist, null);
            holder = new ViewHolder();
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.pane = (CardView) vi.findViewById(R.id.SubCatagoryPane);
        holder.subCatagory = (TextView) vi.findViewById(R.id.textView1);
        holder.subCatagory.setText(categorylist.get(position).getTitle());
        holder.imageView1=(ImageView)vi.findViewById(R.id.imageView1);
        GenericData.setImage(categorylist.get(position).getImageUrl(),holder.imageView1,mContext);
        holder.subCatagory.setSelected(true);
        holder.pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.SelectedCategoryId=categorylist.get(position).getId();
                StaticData.SelectedSection=false;
                Catalogue.productslist();
                Catalogue.InitializeAdapter(mContext);
                Sync.syncFilters(mContext, Catalogue.products);

            }
        });
        return vi;
    }

    static class ViewHolder {
        CardView pane;
        TextView subCatagory;
        ImageView imageView1;
    }
}
