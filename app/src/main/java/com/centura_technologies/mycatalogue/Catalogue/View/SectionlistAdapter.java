package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Anims;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionlistAdapter extends RecyclerView.Adapter<SectionlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CategoryTree> data;
    CategoryTree currentTree;
    ArrayList<Categories> model;

    public SectionlistAdapter(Context context, CategoryTree tree) {
        this.mContext = context;
        currentTree = new CategoryTree();
        currentTree = tree;
        this.data = Catalogue.categories;
    }

    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectionview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SectionlistAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
        holder.text.setText(data.get(position).getTitle());
        final int finalPosition1 = position;
        holder.layview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreadCrumb.Section=data.get(position).getTitle();
                Catalogue.category = data.get(finalPosition1).getCategories();
                Catalogue.InitialzationCategoryAdapter(mContext, data.get(finalPosition1));
            }
        });
    }

    @Override
    public int getItemCount() {
        Collections.sort(data, new Comparator<CategoryTree>() {
            public int compare(CategoryTree v1, CategoryTree v2) {
                if (v1.getTitle() == v2.getTitle())
                    return 0;
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text;
        LinearLayout layview;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.text);
            layview = (LinearLayout) v.findViewById(R.id.layview);
        }
    }
}
