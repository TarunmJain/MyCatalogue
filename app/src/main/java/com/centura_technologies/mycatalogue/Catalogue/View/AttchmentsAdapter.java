package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.CatalogueDetails;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttachmentGroup;
import com.centura_technologies.mycatalogue.Catalogue.Model.AttchmentClass;
import com.centura_technologies.mycatalogue.Catalogue.Model.BreadCrumb;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Catalogue.Model.CategoryTree;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Apis.Urls;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class AttchmentsAdapter extends RecyclerView.Adapter<AttchmentsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<AttachmentGroup> data;
    ArrayList<AttchmentClass> model;
    int GroupView = -1;
    DbHelper dbHelper;

    public AttchmentsAdapter(Context context, ArrayList<AttachmentGroup> tree) {
        this.mContext = context;
        this.data = tree;
        dbHelper = new DbHelper(mContext);
    }

    @Override
    public AttchmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attchmentview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AttchmentsAdapter.ViewHolder holder, final int position) {

        holder.SubCatagorieslist.setVisibility(View.GONE);
        holder.SubCatagorieslist.setLayoutManager(new LinearLayoutManager(mContext));

        if (position == 0) {
            holder.text.setText("Info");
            holder.icon.setImageResource(R.drawable.ic_infoicon);
        } else if (data.get(position - 1).getType() == AttchmentClass.GROUP) {
            holder.text.setText(data.get(position - 1).getGroupTitle());
            holder.icon.setImageResource(R.drawable.mr_ic_play_light);
        } else {
            holder.text.setText(data.get(position - 1).getIndividualAttachment().AttachmentTitle);
            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_IMAGE)
                holder.icon.setImageResource(R.drawable.image);

            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_Panorama)
                holder.icon.setImageResource(R.drawable.panorama);

            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_PDF)
                holder.icon.setImageResource(R.drawable.pdficon);

            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_PPT)
                holder.icon.setImageResource(R.drawable.ppticon);

            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_VEDIO)
                holder.icon.setImageResource(R.drawable.videoicon);

            if (data.get(position - 1).getIndividualAttachment().MediaType == AttchmentClass.TYPE_WEB)
                holder.icon.setImageResource(R.drawable.html);

            //holder.icon.setImageResource(R.drawable.thumb_image);
        }
        final int finalPosition = position - 1;
        if (GroupView == position) {
            //common code
            if (position == 0) {
                holder.SubCatagorieslist.setVisibility(View.GONE);
                CatalogueDetails.LoadInfo();
            } else if (data.get(finalPosition).getType() == AttchmentClass.GROUP) {
                holder.SubCatagorieslist.setVisibility(View.VISIBLE);
                holder.SubCatagorieslist.setAdapter(new MedialistAdapter(mContext, data.get(finalPosition)));
                int viewHeight = GenericData.convertDpToPixels(37, mContext);
                viewHeight = viewHeight * (data.get(finalPosition).getAttchments().size());
                holder.SubCatagorieslist.getLayoutParams().height = viewHeight;
            } else {
                //attchment is clicked change background color
            }
        } else
            holder.SubCatagorieslist.setVisibility(View.GONE);
        if (position == 0)
            holder.layview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupView = position;
                    notifyDataSetChanged();

                }
            });
        else if (data.get(finalPosition).getType() == AttchmentClass.GROUP)
            holder.layview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == GroupView) {
                        GroupView = -1;
                        notifyDataSetChanged();
                    } else if (data.get(finalPosition).getAttchments().size() > 0) {
                        GroupView = position;
                        notifyDataSetChanged();
                    }
                }
            });

        else
            holder.layview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != GroupView) {
                        GroupView = position;
                        String type = AttchmentClass.getMimeType(data.get(finalPosition).getIndividualAttachment().getAttachmentUrl());
                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_IMAGE)
                            CatalogueDetails.LoadImage(mContext, data.get(finalPosition).getIndividualAttachment().AttachmentUrl);

                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_Panorama)
                            CatalogueDetails.LoadPanorama(mContext, dbHelper.returnImage(Urls.parentIP + data.get(finalPosition).getIndividualAttachment().AttachmentUrl));

                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_PDF)
                            CatalogueDetails.LoadPDF(mContext, dbHelper.returnImage(Urls.parentIP + data.get(finalPosition).getIndividualAttachment().AttachmentUrl));

                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_PPT) {
                        }

                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_VEDIO)
                            CatalogueDetails.LoadVedio(mContext, dbHelper.returnImage(Urls.parentIP + data.get(finalPosition).getIndividualAttachment().AttachmentUrl));

                        if (data.get(finalPosition).getIndividualAttachment().MediaType == AttchmentClass.TYPE_WEB)
                            CatalogueDetails.LoadHTML(dbHelper.returnImage(Urls.parentIP + data.get(finalPosition).getIndividualAttachment().AttachmentUrl));

                        notifyDataSetChanged();
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        Collections.sort(data, new Comparator<AttachmentGroup>() {
            public int compare(AttachmentGroup v1, AttachmentGroup v2) {
                if (v1.getGroupTitle().toLowerCase() == v2.getGroupTitle().toLowerCase())
                    return 0;
                return v1.getGroupTitle().toLowerCase().compareTo(v2.getGroupTitle().toLowerCase());
            }
        });
        return data.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView icon;
        RecyclerView SubCatagorieslist;
        MaterialRippleLayout layview;

        public ViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon);
            text = (TextView) v.findViewById(R.id.text);
            layview = (MaterialRippleLayout) v.findViewById(R.id.layview);
            SubCatagorieslist = (RecyclerView) v.findViewById(R.id.SubCatagorieslist);
        }
    }
}
