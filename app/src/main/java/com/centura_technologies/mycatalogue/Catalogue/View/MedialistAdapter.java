package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.centura_technologies.mycatalogue.Catalogue.Model.AttchmentClass;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Apis.Urls;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Centura User1 on 31-08-2016.
 */
public class MedialistAdapter extends RecyclerView.Adapter<MedialistAdapter.ViewHolder> {

    Context mContext;
    AttachmentGroup currentTree;
    DbHelper dbHelper;

    public MedialistAdapter(Context context, AttachmentGroup model) {
        this.currentTree = model;
        mContext = context;
        dbHelper = new DbHelper(mContext);
    }


    @Override
    public MedialistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectioncatalogue, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MedialistAdapter.ViewHolder holder, final int position) {
        holder.text.setText(currentTree.getAttchments().get(position).getAttachmentTitle());
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_IMAGE)
                    CatalogueDetails.LoadImage(mContext, currentTree.getAttchments().get(position).AttachmentUrl);

                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_Panorama)
                    CatalogueDetails.LoadPanorama(mContext, dbHelper.returnImage(Urls.parentIP + currentTree.getAttchments().get(position).AttachmentUrl));

                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_PDF)
                    CatalogueDetails.LoadPDF(mContext, dbHelper.returnImage(Urls.parentIP + currentTree.getAttchments().get(position).AttachmentUrl));

                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_PPT) {}

                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_VEDIO)
                    CatalogueDetails.LoadVedio(mContext, dbHelper.returnImage(Urls.parentIP + currentTree.getAttchments().get(position).AttachmentUrl));

                if (currentTree.getAttchments().get(position).MediaType == AttchmentClass.TYPE_WEB)
                    CatalogueDetails.LoadHTML(dbHelper.returnImage(Urls.parentIP + currentTree.getAttchments().get(position).AttachmentUrl));
            }
        });
    }

    @Override
    public int getItemCount() {
        Collections.sort(currentTree.getAttchments(), new Comparator<AttchmentClass>() {
            public int compare(AttchmentClass v1, AttchmentClass v2) {
                if (v1.getType().toLowerCase() == v2.getType().toLowerCase())
                    return 0;
                return v1.getAttachmentTitle().toLowerCase().compareTo(v2.getAttachmentTitle().toLowerCase());
            }
        });
        return currentTree.getAttchments().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        MaterialRippleLayout lay;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text);
            lay = (MaterialRippleLayout) v.findViewById(R.id.lay);
        }
    }
}
