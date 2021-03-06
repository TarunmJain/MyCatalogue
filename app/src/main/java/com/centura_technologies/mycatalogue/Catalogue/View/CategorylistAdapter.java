package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.ViewHolder> {

    Context mContext;
    CategoryTree currentTree;

    public CategorylistAdapter(Context context, CategoryTree model) {
        this.currentTree = model;
        mContext = context;

    }


    @Override
    public CategorylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectioncatalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategorylistAdapter.ViewHolder holder, int position) {
        {
            holder.text.setText(currentTree.getCategories().get(position).getTitle());
            final int finalPosition = position;
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Category=currentTree.getCategories().get(finalPosition).getTitle();
                    Catalogue.toolbar.setTitle(BreadCrumb.Category);
                    StaticData.SelectedCategoryId = currentTree.getCategories().get(finalPosition).getId();
                    StaticData.position = finalPosition;
                    if (DB.getInitialModel().getProducts().size() != 0) {
                        Catalogue.productslist();
                        Catalogue.InitializeAdapter(mContext);
                    } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                    //Catalogue.drawer.closeDrawer(Catalogue.leftdrawer);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        Collections.sort(currentTree.getCategories(), new Comparator<Categories>() {
            public int compare(Categories v1, Categories v2) {
                if (v1.getTitle().toLowerCase() == v2.getTitle().toLowerCase())
                    return 0;
                return v1.getTitle().toLowerCase().compareTo(v2.getTitle().toLowerCase());
            }
        });
        return currentTree.getCategories().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        MaterialRippleLayout lay;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text);
            lay= (MaterialRippleLayout) v.findViewById(R.id.lay);
        }
    }
}
