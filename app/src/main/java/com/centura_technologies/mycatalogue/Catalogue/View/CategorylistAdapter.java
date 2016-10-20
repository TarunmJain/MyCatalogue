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

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
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
    public void onBindViewHolder(CategorylistAdapter.ViewHolder holder, final int position) {
        GenericData.setImage(currentTree.getCategories().get(position).getImageUrl(), holder.categoryImage, mContext);
        holder.text.setText(currentTree.getCategories().get(position).getTitle());
        final int finalPosition = position;
        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreadCrumb.Category=currentTree.getCategories().get(position).getTitle();
                Catalogue.sectionbreadcrumb.setText(BreadCrumb.Section);
                Catalogue.catagorybreadcrumb.setText(BreadCrumb.Category);
                Catalogue.slashbreadcrumb.setVisibility(View.VISIBLE);
                StaticData.SelectedCategoryId = currentTree.getCategories().get(finalPosition).getId();
                StaticData.position = finalPosition;
                if (DB.getInitialModel().getProducts().size() != 0) {
                    Catalogue.productslist();
                    Catalogue.InitializeAdapter(mContext);
                } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                Catalogue.drawer.closeDrawer(Catalogue.leftdrawer);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (currentTree.getCategories().size() == 0){
            Catalogue.nocategorytext.setVisibility(View.VISIBLE);
            Catalogue.nocategorytext.setText("There are no Categories Under the "+currentTree.getTitle()+" Section ");
        }
        else
            Catalogue.nocategorytext.setVisibility(View.GONE);
        return currentTree.getCategories().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.catimage);
            text = (TextView) v.findViewById(R.id.text);

        }
    }
}
