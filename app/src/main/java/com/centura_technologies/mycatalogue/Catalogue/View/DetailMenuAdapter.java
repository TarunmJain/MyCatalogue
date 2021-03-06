package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.DescriptionMenuClass;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Apis.Urls;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura on 24-10-2016.
 */
public class DetailMenuAdapter extends RecyclerView.Adapter<DetailMenuAdapter.ViewHolder> {

    Context context;
    ArrayList<DescriptionMenuClass> descriptionMenuClasses = new ArrayList<DescriptionMenuClass>();


    public DetailMenuAdapter(Context mContext, ArrayList<DescriptionMenuClass> data) {
        context = mContext;
        descriptionMenuClasses = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desc_menu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final int finalPosition = position-1;
        if (position == 0)
        {
            holder.srcimage.setImageResource(R.drawable.ic_infoicon);
            holder.attachmenttext.setText("Info");
        }
        else {
            if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_IMAGE) {
                {
                    holder.attachmenttext.setText(descriptionMenuClasses.get(finalPosition).Title);
                    GenericData.setImage(descriptionMenuClasses.get(finalPosition).URL,holder.srcimage,context);
                }
            }

            if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_PDF) {
                holder.srcimage.setImageResource(R.drawable.ic_pdficon);
                holder.attachmenttext.setText(descriptionMenuClasses.get(finalPosition).Title);
            }

            if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_Panorama) {
                holder.srcimage.setImageResource(R.drawable.ic_ppticon);
                holder.attachmenttext.setText(descriptionMenuClasses.get(finalPosition).Title);
            }

            if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_VEDIO) {
                holder.srcimage.setImageResource(R.drawable.ic_vedioicon);
                holder.attachmenttext.setText(descriptionMenuClasses.get(finalPosition).Title);
            }

            if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_WEB) {
                holder.srcimage.setImageResource(R.drawable.ic_webicon);
                holder.attachmenttext.setText(descriptionMenuClasses.get(finalPosition).Title);
            }
        }

        holder.srcimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalPosition ==-1)
                {
                    CatalogueDetails.LoadInfo();
                }
                else {
                    DbHelper dbHelper = new DbHelper(context);
                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_IMAGE) {
                        {
                            CatalogueDetails.LoadImage(context,descriptionMenuClasses.get(finalPosition).URL);
                        }
                    }

                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_Panorama) {
                        {
                            CatalogueDetails.LoadPanorama(context,dbHelper.returnImage(Urls.parentIP + descriptionMenuClasses.get(finalPosition).URL));
                        }
                    }

                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_PDF) {
                        {
                            CatalogueDetails.LoadPDF(context,dbHelper.returnImage(Urls.parentIP + descriptionMenuClasses.get(finalPosition).URL));
                        }
                    }

                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_PPT) {

                    }

                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_VEDIO) {
                        {
                            CatalogueDetails.LoadVedio(context,dbHelper.returnImage(Urls.parentIP + descriptionMenuClasses.get(finalPosition).URL));
                        }
                    }

                    if (descriptionMenuClasses.get(finalPosition).MediaType == DescriptionMenuClass.TYPE_WEB) {
                        {
                            CatalogueDetails.LoadHTML(dbHelper.returnImage(Urls.parentIP + descriptionMenuClasses.get(finalPosition).URL));
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return descriptionMenuClasses.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView srcimage;
        TextView attachmenttext;
        public ViewHolder(View itemView) {
            super(itemView);
            srcimage = (ImageView) itemView.findViewById(R.id.srcimage);
            attachmenttext= (TextView) itemView.findViewById(R.id.attachmenttext);
        }
    }
}
