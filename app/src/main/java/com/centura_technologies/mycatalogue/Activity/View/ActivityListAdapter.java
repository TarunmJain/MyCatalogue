package com.centura_technologies.mycatalogue.Activity.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Activity.Controller.ActivityList;
import com.centura_technologies.mycatalogue.Activity.Controller.AddCall;
import com.centura_technologies.mycatalogue.Activity.Controller.AddEvent;
import com.centura_technologies.mycatalogue.Activity.Controller.AddTask;
import com.centura_technologies.mycatalogue.Activity.Model.ActivitiesModel;
import com.centura_technologies.mycatalogue.Leads.Controller.LeadsList;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 16-08-2016.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ActivitiesModel> data;

    public ActivityListAdapter(Context context) {
        this.mContext = context;
        this.data = DB.getActivities();
    }

    @Override
    public ActivityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (LeadsList.grid_to_listflag) {
            View gridv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitylist, parent, false);
            ViewHolder gridvh = new ViewHolder(gridv);
            return gridvh;
        } else {
            View listv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitylist, parent, false);
            ViewHolder listvh = new ViewHolder(listv);
            return listvh;
        }
    }

    @Override
    public void onBindViewHolder(ActivityListAdapter.ViewHolder holder, int position) {
        holder.ownedby.setText("Owned By : " + data.get(position).getOwnedBy());
        holder.subject.setText("Subject : " + data.get(position).getSubject());
        holder.activitytype.setText("Activity Type : " + data.get(position).getActivityType());
        holder.status.setText("Status : " + data.get(position).getStatus());

        Onclicks(holder, position);
    }

    private void Onclicks(ViewHolder holder, final int position) {
        holder.activitypane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getActivityType().matches("AddTask"))
                {
                    StaticData.edittask = true;
                    DB.setActivitymodel(data.get(position));
                    mContext.startActivity(new Intent(mContext, AddTask.class));
                }
                if(data.get(position).getActivityType().matches("AddEvent"))
                {
                    StaticData.editevent = true;
                    DB.setActivitymodel(data.get(position));
                    mContext.startActivity(new Intent(mContext, AddEvent.class));
                }
                if(data.get(position).getActivityType().matches("AddCall"))
                {
                    StaticData.editcall = true;
                    DB.setActivitymodel(data.get(position));
                    mContext.startActivity(new Intent(mContext, AddCall.class));
                }
            }
        });

        holder.deleteactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(position);
                notifyDataSetChanged();
                ActivityList.OnResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ownedby, subject, activitytype, status;
        ImageView deleteactivity;
        RelativeLayout activitypane;

        public ViewHolder(View v) {
            super(v);
            ownedby = (TextView) v.findViewById(R.id.ownedby);
            subject = (TextView) v.findViewById(R.id.subject);
            activitytype = (TextView) v.findViewById(R.id.activitytype);
            status = (TextView) v.findViewById(R.id.status);
            deleteactivity = (ImageView) v.findViewById(R.id.deleteactivity);
            activitypane = (RelativeLayout) v.findViewById(R.id.activitypane);
        }
    }
}
