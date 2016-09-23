package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class SectionCatalogueAdapter extends RecyclerView.Adapter<SectionCatalogueAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CategoryTree> data;
    CategoryTree currentTree;
    ArrayList<Categories> model;

    public SectionCatalogueAdapter(Context context, CategoryTree tree) {
        this.mContext = context;
        currentTree = new CategoryTree();
        currentTree = tree;
        if (SectionCatalogue.Section_to_Category) {
            this.data = SectionCatalogue.categories;
        } else {
            this.model = SectionCatalogue.category;
        }
    }

    @Override
    public SectionCatalogueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectioncatalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SectionCatalogueAdapter.ViewHolder holder, int position) {
        holder.backlay.setVisibility(View.GONE);
        if (SectionCatalogue.Section_to_Category) {
            if (position == data.size()) {
                holder.backlay.setVisibility(View.VISIBLE);
                holder.backlay.setText("All Categories");
                holder.categoryImage.setImageResource(R.drawable.common);
                holder.text.setVisibility(View.GONE);
                final int finalPosition1 = position;
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SectionCatalogue.category = DB.getInitialModel().getCategories();
                        SectionCatalogue.InitialzationCategoryAdapter(mContext, null);
                    }
                });
            } else {
                GenericData.setImage(data.get(position).getImageUrl(), holder.categoryImage, mContext);
                holder.text.setText(data.get(position).getTitle());
                holder.text.setVisibility(View.VISIBLE);
                final int finalPosition1 = position;
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SectionCatalogue.category = data.get(finalPosition1).getCategories();
                        SectionCatalogue.InitialzationCategoryAdapter(mContext, data.get(finalPosition1));
                    }
                });
            }

        } else {
            if (position == 0) {
                holder.backlay.setVisibility(View.VISIBLE);
                holder.backlay.setText("BACK");
                holder.text.setVisibility(View.VISIBLE);
                if (currentTree == null) {
                    holder.categoryImage.setImageResource(R.drawable.common);
                    holder.text.setText("All Categories");
                } else {
                    GenericData.setImage(currentTree.getImageUrl(), holder.categoryImage, mContext);
                    holder.text.setText(currentTree.getTitle());
                }
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SectionCatalogue.InitialzationSectionAdapter(mContext);
                    }
                });
            }
            else if (position == model.size()+1) {
                holder.backlay.setVisibility(View.VISIBLE);
                holder.backlay.setText("All Products");
                holder.text.setText("");
                holder.text.setVisibility(View.GONE);
                holder.categoryImage.setImageResource(R.drawable.common);
                final int finalPosition = position;
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StaticData.SelectedCategoryId = "-1";
                        if (DB.getInitialModel().getProducts().size() != 0) {
                            mContext.startActivity(new Intent(mContext, Catalogue.class));
                        } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                position -= 1;
                GenericData.setImage(model.get(position).getImageUrl(), holder.categoryImage, mContext);
                holder.text.setText(model.get(position).getTitle());
                holder.text.setVisibility(View.VISIBLE);
                final int finalPosition = position;
                holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StaticData.SelectedCategoryId = model.get(finalPosition).getId();
                        StaticData.position = finalPosition;
                        if (DB.getInitialModel().getProducts().size() != 0) {
                            mContext.startActivity(new Intent(mContext, Catalogue.class));
                        } else Toast.makeText(mContext, "No Products", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }

    @Override
    public int getItemCount() {
        if (SectionCatalogue.Section_to_Category) {
            return data.size() + 1;
        } else {
            return model.size() + 2;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView text, backlay;
        CardView relativelayout;

        public ViewHolder(View v) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.text);
            backlay = (TextView) v.findViewById(R.id.backlay);
            relativelayout = (CardView) v.findViewById(R.id.relativelayout);
        }
    }
}
