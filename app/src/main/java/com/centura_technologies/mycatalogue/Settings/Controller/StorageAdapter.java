package com.centura_technologies.mycatalogue.Settings.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.configuration.FolderInfo;

/**
 * Created by Centura User1 on 25-08-2016.
 */
public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    Context mContext;
    TextView storagepath;
    Activity a;

    public StorageAdapter(final Context context) {
        this.mContext = context;
        a=(Activity)context;
        storagepath=(TextView)a.findViewById(R.id.storagepath);
    }

    @Override
    public StorageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storege_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final StorageAdapter.ViewHolder holder, final int position) {
        holder.text.setText(ConfigData.StorageList.get(position).getDisplayName());
        holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.radio.setImageResource(R.drawable.roundedhallow);
        if (ConfigData.selectedStoregePosition == position) {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.radio.setImageResource(R.drawable.roundedsolid);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigData.selectedStoregePosition = position;

                if (ConfigData.StorageList.get(position).getDisplayName().contains("Internal")){
                    ConfigData.selectedStoregePath = ConfigData.StorageList.get(position).path;
                    storagepath.setText("Internal Storage");
                }
                else
                {
                    String[] parts = (ConfigData.StorageList.get(position).path).split("/");
                    String pathtemp = parts[parts.length - 1];
                    ConfigData.selectedStoregePath = "/storage/"+pathtemp;
                    storagepath.setText(pathtemp);
                }
                holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.radio.setImageResource(R.drawable.roundedsolid);
                /*((Activity) mContext).startActivity(new Intent(mContext, FolderInfo.class));
                ((Activity) mContext).finish();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return ConfigData.StorageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView radio;
        TextView text;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            radio = (ImageView) itemView.findViewById(R.id.radio);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
