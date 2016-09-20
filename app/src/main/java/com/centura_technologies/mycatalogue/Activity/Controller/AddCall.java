package com.centura_technologies.mycatalogue.Activity.Controller;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Activity.Model.ActivitiesModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 16-08-2016.
 */
public class AddCall extends AppCompatActivity {
    Toolbar toolbar;
    static EditText subject, contact, description, callresult, status;
    static RadioButton inbound, outbound, type;
    RadioGroup calltype;
    Button save, saveandnew, cancel;
    FloatingActionButton fab;
    LinearLayout footer;
    public static ActivitiesModel activitymodel;
    int selectedId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcall);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Create Call");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subject = (EditText) findViewById(R.id.subject);
        contact = (EditText) findViewById(R.id.contact);
        description = (EditText) findViewById(R.id.description);
        callresult = (EditText) findViewById(R.id.callresult);
        status = (EditText) findViewById(R.id.status);
        calltype = (RadioGroup) findViewById(R.id.calltype);
        inbound = (RadioButton) findViewById(R.id.inbound);
        outbound = (RadioButton) findViewById(R.id.outbound);

        save = (Button) findViewById(R.id.save);
        saveandnew = (Button) findViewById(R.id.saveandnew);
        cancel = (Button) findViewById(R.id.cancel);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        footer = (LinearLayout) findViewById(R.id.footer);

        inbound.setChecked(true);
        selectedId = calltype.getCheckedRadioButtonId();
        type = (RadioButton) findViewById(selectedId);

        Onclicks();

        if (StaticData.editcall) {
            Renderdata();
            FieldDisable();
            toolbar.setTitle("Edit Call");
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void Onclicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(subject.getText().toString().trim()))) {
                    AddLead();
                    CleartextFields();
                    finish();
                    Toast.makeText(AddCall.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
                } else subject.setError("Field cannot be empty");
            }
        });

        saveandnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLead();
                CleartextFields();
                startActivity(new Intent(AddCall.this, AddTask.class));
                finish();
                Toast.makeText(AddCall.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
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

    public static void AddLead() {
        activitymodel = new ActivitiesModel();
        activitymodel.setEventTitle(subject.getText().toString());
        activitymodel.setFromDate(contact.getText().toString());
        activitymodel.setCallType(type.getText().toString());
        activitymodel.setDescription(description.getText().toString());
        activitymodel.setResult(callresult.getText().toString());
        activitymodel.setStatus(status.getText().toString());
        activitymodel.setActivityType("Call");
        DB.AddActivity(activitymodel);
    }

    public static void CleartextFields() {
        subject.setText("");
        contact.setText("");
        inbound.setChecked(true);
        description.setText("");
        callresult.setText("");
        status.setText("");
    }

    private void Renderdata() {
        subject.setText(DB.getActivitymodel().getEventTitle());
        contact.setText(DB.getActivitymodel().getFromDate());
        if (DB.getActivitymodel().getCallType().matches("Inbound"))
            inbound.setChecked(true);
        else
            outbound.setChecked(true);
        description.setText(DB.getActivitymodel().getDescription());
        callresult.setText(DB.getActivitymodel().getResult());
        status.setText(DB.getActivitymodel().getStatus());
    }

    private void FieldDisable() {
        subject.setEnabled(false);
        contact.setEnabled(false);
        inbound.setEnabled(false);
        outbound.setEnabled(false);
        description.setEnabled(false);
        callresult.setEnabled(false);
        status.setEnabled(false);
        footer.setVisibility(View.GONE);
    }

    private void FieldEnable() {
        subject.setEnabled(true);
        contact.setEnabled(true);
        inbound.setEnabled(true);
        outbound.setEnabled(true);
        description.setEnabled(true);
        callresult.setEnabled(true);
        status.setEnabled(true);
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
            startActivity(new Intent(AddCall.this, ActivityList.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
