package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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

import com.balysv.materialripple.MaterialRippleLayout;
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
    int categoriesView = -1;
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
    public void onBindViewHolder(final SectionlistAdapter.ViewHolder holder, int position) {
        holder.SubCatagorieslist.setVisibility(View.GONE);
        holder.SubCatagorieslist.setLayoutManager(new LinearLayoutManager(mContext));
        if (position == 0) {
            //holder.categoryImage.setImageResource(R.drawable.common);
            holder.text.setText("All Products");
            holder.layview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section = "All Products";
                    StaticData.SelectedCategoryId = "-1";
                    BreadCrumb.Category = "";
                    Catalogue.toolbar.setTitle("All Products");
                    if (DB.getInitialModel().getProducts().size() != 0) {
                        Catalogue.productslist();
                        Catalogue.InitializeAdapter(mContext);
                    } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                    //Catalogue.drawer.closeDrawer(Catalogue.leftdrawer);
                    categoriesView = -1;
                    notifyDataSetChanged();
                }
            });
        } else {
            position -= 1;
            //GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
            holder.text.setText(data.get(position).getTitle());
            final int finalPosition = position;
            if (categoriesView == finalPosition) {
                if (data.get(finalPosition).getCategories().size() == 1) {
                    BreadCrumb.Category = data.get(finalPosition).getCategories().get(0).getTitle();
                    Catalogue.toolbar.setTitle(BreadCrumb.Category);
                    StaticData.SelectedCategoryId = data.get(finalPosition).getCategories().get(0).getId();
                    StaticData.position = 0;
                    if (DB.getInitialModel().getProducts().size() != 0) {
                        Catalogue.productslist();
                        Catalogue.InitializeAdapter(mContext);
                    } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                } else {
                    holder.SubCatagorieslist.setVisibility(View.VISIBLE);
                    holder.SubCatagorieslist.setAdapter(new CategorylistAdapter(mContext, data.get(finalPosition)));
                    int viewHeight = GenericData.convertDpToPixels(37, mContext);
                    viewHeight = viewHeight * (data.get(finalPosition).getCategories().size());
                    holder.SubCatagorieslist.getLayoutParams().height = viewHeight;
                }
            } else
                holder.SubCatagorieslist.setVisibility(View.GONE);
            holder.layview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreadCrumb.Section = data.get(finalPosition).getTitle();
                    Catalogue.category = data.get(finalPosition).getCategories();
                    if (finalPosition == categoriesView) {
                        categoriesView = -1;
                        notifyDataSetChanged();
                    } else if (data.get(finalPosition).getCategories().size() > 0) {
                        categoriesView = finalPosition;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Collections.sort(data, new Comparator<CategoryTree>() {
            public int compare(CategoryTree v1, CategoryTree v2) {
                if (v1.getTitle().toLowerCase() == v2.getTitle().toLowerCase())
                    return 0;
                return v1.getTitle().toLowerCase().compareTo(v2.getTitle().toLowerCase());
            }
        });
        return data.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        RecyclerView SubCatagorieslist;
        MaterialRippleLayout layview;
        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text);
            layview = (MaterialRippleLayout) v.findViewById(R.id.layview);
            SubCatagorieslist = (RecyclerView) v.findViewById(R.id.SubCatagorieslist);
        }
    }
}
