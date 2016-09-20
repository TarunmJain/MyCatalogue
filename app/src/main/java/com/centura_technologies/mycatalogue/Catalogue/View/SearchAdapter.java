package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.R;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 14-09-2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context mContext;
    public static ArrayList<String> data=new ArrayList<String>();
    public SearchAdapter(Context context,ArrayList<String> model){
        this.mContext=context;
    }
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchproduct,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {
        holder.tittle.setText(data.get(position));
        holder.tittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalogue.search.setText(data.get(position));
                Catalogue.searchicon.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittle;
        public ViewHolder(View itemView) {
            super(itemView);
            tittle = (TextView) itemView.findViewById(R.id.text_main_seen);
        }
    }
}
