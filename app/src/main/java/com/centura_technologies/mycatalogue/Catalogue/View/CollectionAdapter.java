package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.MyRecyclerView;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.CollectionModel;
import com.centura_technologies.mycatalogue.Login.Controller.Login;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CollectionModel> data;
    private int lastPosition = -1;
    int ANIMATION_DURATION = 500;

    public CollectionAdapter(Context context, ArrayList<CollectionModel> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
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

    @Override
    public void onBindViewHolder(final CollectionAdapter.ViewHolder holder, int position) {

        if (position == 0) {
            holder.collectiontitle.setText("All Products");
            GenericData.setImage(data.get((position) % 4).getImageUrl(), holder.collectionimage, mContext);
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
                    BreadCrumb.Category = data.get(finalPosition).getTitle();
                    StaticData.SelectedCollectionProducts = new ArrayList<String>();
                    StaticData.SelectedCollection = true;
                    StaticData.SelectedCollectionProducts = data.get(finalPosition).getProductIds();
                    mContext.startActivity(new Intent(mContext, Catalogue.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectiontitle;
        ImageView collectionimage;
        CardView collectionpane;

        public ViewHolder(View v) {
            super(v);
            collectiontitle = (TextView) v.findViewById(R.id.text);
            collectionimage = (ImageView) v.findViewById(R.id.image);
            collectionpane = (CardView) v.findViewById(R.id.collectionpane);
        }
    }
}
