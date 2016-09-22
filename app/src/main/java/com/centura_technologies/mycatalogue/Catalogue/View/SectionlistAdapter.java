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
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Anims;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionlistAdapter extends RecyclerView.Adapter<SectionlistAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CategoryTree> data;
    CategoryTree currentTree;
    ArrayList<Categories> model;

    public SectionlistAdapter(Context context,CategoryTree tree) {
        this.mContext = context;
        currentTree=new CategoryTree();
        currentTree=tree;
        if(Catalogue.Section_to_Category){
            this.data = Catalogue.categories;
        }else {
            this.model=Catalogue.category;
        }
    }

    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectioncatalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SectionlistAdapter.ViewHolder holder, int position) {
        holder.backlay.setVisibility(View.GONE);
        if(Catalogue.Section_to_Category){

            GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
            holder.text.setText(data.get(position).getTitle());
            final int finalPosition1 = position;
            holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Catalogue.category=data.get(finalPosition1).getCategories();
                    Catalogue.InitialzationCategoryAdapter(mContext,data.get(finalPosition1));
                }
            });
        }else {
            if(position==0)
            {
                holder.backlay.setVisibility(View.VISIBLE);
                GenericData.setImage(currentTree.getImageUrl(), holder.categoryImage, mContext);
                holder.text.setText(currentTree.getTitle());
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Catalogue.InitialzationSectionAdapter(mContext);
                    }
                });
            }
            else {
                position-=1;
                GenericData.setImage(model.get(position).getImageUrl(), holder.categoryImage, mContext);
                holder.text.setText(model.get(position).getTitle());
                final int finalPosition = position;
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StaticData.SelectedCategoryId = model.get(finalPosition).getId();
                        StaticData.position= finalPosition;
                        if(DB.getInitialModel().getProducts().size()!=0) {
                            Catalogue.productslist();
                            Catalogue.InitializeAdapter(mContext);

                        }else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        if(Catalogue.Section_to_Category){
            return data.size();
        }else {
            return model.size()+1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text,backlay;
        CardView relativelayout;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.text);
            backlay= (TextView) v.findViewById(R.id.backlay);
            relativelayout = (CardView) v.findViewById(R.id.relativelayout);
        }
    }
}
