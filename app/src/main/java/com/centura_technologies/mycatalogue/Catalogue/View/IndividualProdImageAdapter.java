package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.centura_technologies.mycatalogue.Catalogue.Controller.ImageViewer;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 27-04-2016.
 */
public class IndividualProdImageAdapter extends RecyclerView.Adapter<IndividualProdImageAdapter.ViewHolder> {
    Context context;
    public static ArrayList<String> otherImages;
    ImageView openimage;

    public IndividualProdImageAdapter(Context context, ArrayList<String> otherImages, ImageView openimage){
        this.context = context;
        this.otherImages = otherImages;
        this.openimage = openimage;
    }

    @Override
    public IndividualProdImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individualprod_images,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IndividualProdImageAdapter.ViewHolder holder, final int position) {

        if(position>2){
            holder.viewmore.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
            holder.viewmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EventBus.getDefault().postSticky(otherImages);
                    StaticData.SelectedProductImage=false;
                    context.startActivity(new Intent(context, ImageViewer.class));
                }
            });
        }else{
            GenericData.setImage(otherImages.get(position), holder.image,context);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GenericData.setImage(otherImages.get(position),openimage,context);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(otherImages.size()>3){
            return 4;
        }
        return otherImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,openimage;
        TextView viewmore;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.otherimage);
            viewmore=(TextView)itemView.findViewById(R.id.viewmore);
        }
    }
}
