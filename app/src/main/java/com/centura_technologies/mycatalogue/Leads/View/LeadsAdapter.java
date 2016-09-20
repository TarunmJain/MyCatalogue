package com.centura_technologies.mycatalogue.Leads.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Leads.Controller.AddLeads;
import com.centura_technologies.mycatalogue.Leads.Controller.LeadsList;
import com.centura_technologies.mycatalogue.Leads.Model.LeadsModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 09-08-2016.
 */
public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<LeadsModel> data;

    public LeadsAdapter(Context context) {
        this.mContext = context;
        data = DB.getLeads();
    }

    @Override
    public LeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(LeadsList.grid_to_listflag){
            View gridv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leads, parent, false);
            ViewHolder gridvh = new ViewHolder(gridv);
            return gridvh;
        }else{
            View listv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leads, parent, false);
            ViewHolder listvh = new ViewHolder(listv);
            return listvh;
        }


    }

    @Override
    public void onBindViewHolder(LeadsAdapter.ViewHolder holder, final int position) {
        holder.companyname.setText("Company Name : " + data.get(position).getCompanyName());
        holder.firstname.setText("First Name : " + data.get(position).getFirstName());
        holder.lastname.setText("Last Name : " + data.get(position).getLastName());
        holder.phone.setText("Phone : " + data.get(position).getPhone());
        holder.leadssource.setText("Lead Source : " + data.get(position).getLeadSource());
        holder.leadsstatus.setText("Lead Status : " + data.get(position).getLeadStatus());
        //holder.contactname.setText("Contact Name : "+data.get(position).getContactName());
        //holder.contactnumber.setText("Contact Phone Number : "+data.get(position).getContactPhoneNumber());
        Onclicks(holder, position);


    }

    private void Onclicks(ViewHolder holder, final int position) {

        holder.leadpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.editlead = true;
                DB.setLeadmodel(data.get(position));
                mContext.startActivity(new Intent(mContext, AddLeads.class));
            }
        });

        holder.deletelead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyname, firstname, lastname, phone, leadssource, leadsstatus, contactname, contactnumber;
        ImageView deletelead;
        RelativeLayout leadpane;

        public ViewHolder(View v) {
            super(v);
            companyname = (TextView) v.findViewById(R.id.newcompanyname);
            firstname = (TextView) v.findViewById(R.id.newfirstname);
            lastname = (TextView) v.findViewById(R.id.newlastname);
            phone = (TextView) v.findViewById(R.id.newphonenumber);
            leadssource = (TextView) v.findViewById(R.id.leadssource);
            leadsstatus = (TextView) v.findViewById(R.id.leadsstatus);
            leadpane = (RelativeLayout) v.findViewById(R.id.leadpane);
            deletelead = (ImageView) v.findViewById(R.id.deletelead);
            //contactname=(TextView)v.findViewById(R.id.contactpersonname);
            //contactnumber=(TextView)v.findViewById(R.id.contactpersonnumber);

        }
    }
}
