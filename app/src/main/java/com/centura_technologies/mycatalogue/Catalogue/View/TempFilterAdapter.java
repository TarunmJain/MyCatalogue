package com.centura_technologies.mycatalogue.Catalogue.View;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Model.FilterItem;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;

/**
 * Created by Centura on 20-09-2016.
 */
public class TempFilterAdapter extends RecyclerView.Adapter<TempFilterAdapter.ViewHolder> {
    Context context;
    ArrayList<FilterItem> model;

    public TempFilterAdapter(Context context, ArrayList<FilterItem> data) {
        this.context = context;
        this.model = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_item_filter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(position%2==0)
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.black05));
        holder.textView.setText(model.get(position).getTitle());
        int finallines=0;
        int viewHeight = GenericData.convertDpToPixels(55, context);
        if(model.get(position).getValue().size()<4)
            finallines=1;
        else {
            float lines=(float)(model.get(position).getValue().size())/3;
            finallines=(int)lines;
            if(lines>finallines)
                finallines+=1;
        }
        viewHeight = viewHeight * finallines;
        holder.recyclerView.getLayoutParams().height = viewHeight;
        holder.recyclerView.setAdapter(new TempFilterItemAdapter(context, model.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        if (model != null)
            return model.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        LinearLayout backgroundlay;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cat_filtertitle);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.cat_filterdata);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            backgroundlay= (LinearLayout) itemView.findViewById(R.id.backgroundlay);
        }
    }
}
