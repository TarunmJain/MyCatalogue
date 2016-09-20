package com.centura_technologies.mycatalogue.Leads.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Leads.Model.LeadsModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 09-08-2016.
 */
public class AddLeads extends AppCompatActivity {
    Toolbar toolbar;
    static EditText companyname,firstname,lastname,email,phone,mobile,website,leads_source,leads_status,contact_name,contact_email,contact_phonenumber,
            address,city,state,zipcode,country,description;
    Button save,saveandnew,cancel;
    FloatingActionButton fab;
    public static LeadsModel leadsModel;
    LinearLayout footer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addleads);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Create Lead");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Initialize Back arrow

        //EditText Initialization
        companyname=(EditText)findViewById(R.id.companyname);
        firstname=(EditText)findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        mobile=(EditText)findViewById(R.id.mobile);
        website=(EditText)findViewById(R.id.website);
        leads_source=(EditText)findViewById(R.id.leads_source);
        leads_status=(EditText)findViewById(R.id.leads_status);
        contact_name=(EditText)findViewById(R.id.contact_name);
        contact_email=(EditText)findViewById(R.id.contact_email);
        contact_phonenumber=(EditText)findViewById(R.id.contact_phonenumber);
        address=(EditText)findViewById(R.id.address);
        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        zipcode=(EditText)findViewById(R.id.zipcode);
        country=(EditText)findViewById(R.id.country);
        description=(EditText)findViewById(R.id.description);
        //Button Initialization
        save=(Button)findViewById(R.id.save);
        saveandnew=(Button)findViewById(R.id.saveandnew);
        cancel=(Button)findViewById(R.id.cancel);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        footer=(LinearLayout)findViewById(R.id.footer);
        //Button Onclicks
        Onclicks();

        if(StaticData.editlead){
            Renderdata();
            FieldDisable();
            toolbar.setTitle("Edit Lead");
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void Onclicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLead();
                CleartextFields();
                finish();
                Toast.makeText(AddLeads.this,"Waiting for Api...",Toast.LENGTH_SHORT).show();
            }
        });

        saveandnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLead();
                CleartextFields();
                Toast.makeText(AddLeads.this,"Waiting for Api...",Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FieldEnable();
                saveandnew.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        });
    }

    public static void AddLead(){
        leadsModel=new LeadsModel();
        leadsModel.setCompanyName(companyname.getText().toString());
        leadsModel.setFirstName(firstname.getText().toString());
        leadsModel.setLastName(lastname.getText().toString());
        leadsModel.setEmail(email.getText().toString());
        leadsModel.setPhone(phone.getText().toString());
        leadsModel.setMobile(mobile.getText().toString());
        leadsModel.setWebsite(website.getText().toString());
        leadsModel.setLeadSource(leads_source.getText().toString());
        leadsModel.setLeadStatus(leads_status.getText().toString());
        leadsModel.setContactName(contact_name.getText().toString());
        leadsModel.setContactEmail(contact_email.getText().toString());
        leadsModel.setContactPhoneNumber(contact_phonenumber.getText().toString());
        leadsModel.setAddress(address.getText().toString());
        leadsModel.setCity(city.getText().toString());
        leadsModel.setState(state.getText().toString());
        leadsModel.setZipCode(zipcode.getText().toString());
        leadsModel.setCountry(country.getText().toString());
        leadsModel.setDescription(description.getText().toString());
        DB.AddLead(leadsModel);
    }

    public static void CleartextFields(){
        companyname.setText("");
        firstname.setText("");
        lastname.setText("");
        email.setText("");
        phone.setText("");
        mobile.setText("");
        website.setText("");
        leads_source.setText("");
        leads_status.setText("");
        contact_name.setText("");
        contact_email.setText("");
        contact_phonenumber.setText("");
        address.setText("");
        city.setText("");
        state.setText("");
        zipcode.setText("");
        country.setText("");
        description.setText("");
    }

    private void Renderdata() {
        companyname.setText(DB.getLeadmodel().getCompanyName());
        firstname.setText(DB.getLeadmodel().getFirstName());
        lastname.setText(DB.getLeadmodel().getLastName());
        email.setText(DB.getLeadmodel().getEmail());
        phone.setText(DB.getLeadmodel().getPhone());
        mobile.setText(DB.getLeadmodel().getMobile());
        website.setText(DB.getLeadmodel().getWebsite());
        leads_source.setText(DB.getLeadmodel().getLeadSource());
        leads_status.setText(DB.getLeadmodel().getLeadStatus());
        contact_name.setText(DB.getLeadmodel().getContactName());
        contact_email.setText(DB.getLeadmodel().getContactEmail());
        contact_phonenumber.setText(DB.getLeadmodel().getContactPhoneNumber());
        address.setText(DB.getLeadmodel().getAddress());
        city.setText(DB.getLeadmodel().getCity());
        state.setText(DB.getLeadmodel().getState());
        zipcode.setText(DB.getLeadmodel().getZipCode());
        country.setText(DB.getLeadmodel().getCountry());
        description.setText(DB.getLeadmodel().getDescription());
    }

    private void FieldDisable(){
        companyname.setEnabled(false);
        firstname.setEnabled(false);
        lastname.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        mobile.setEnabled(false);
        website.setEnabled(false);
        leads_source.setEnabled(false);
        leads_status.setEnabled(false);
        contact_name.setEnabled(false);
        contact_email.setEnabled(false);
        contact_phonenumber.setEnabled(false);
        address.setEnabled(false);
        city.setEnabled(false);
        state.setEnabled(false);
        zipcode.setEnabled(false);
        country.setEnabled(false);
        description.setEnabled(false);
        footer.setVisibility(View.GONE);
    }

    private void FieldEnable(){
        companyname.setEnabled(true);
        firstname.setEnabled(true);
        lastname.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);
        mobile.setEnabled(true);
        website.setEnabled(true);
        leads_source.setEnabled(true);
        leads_status.setEnabled(true);
        contact_name.setEnabled(true);
        contact_email.setEnabled(true);
        contact_phonenumber.setEnabled(true);
        address.setEnabled(true);
        city.setEnabled(true);
        state.setEnabled(true);
        zipcode.setEnabled(true);
        country.setEnabled(true);
        description.setEnabled(true);
        footer.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
                if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
                    startActivity(new Intent(AddLeads.this, LeadsList.class));
                    finish();
                }
        return super.onOptionsItemSelected(item);
    }
}
