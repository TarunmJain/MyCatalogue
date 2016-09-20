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
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Activity.Model.ActivitiesModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 16-08-2016.
 */
public class AddEvent extends AppCompatActivity {
    Toolbar toolbar;
    static EditText eventname, fromdate, todate, host, participants, status, description;
    Button save, saveandnew, cancel;
    FloatingActionButton fab;
    LinearLayout footer;
    public static ActivitiesModel activitymodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Create Event");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventname = (EditText) findViewById(R.id.eventname);
        fromdate = (EditText) findViewById(R.id.fromdate);
        todate = (EditText) findViewById(R.id.todate);
        host = (EditText) findViewById(R.id.host);
        participants = (EditText) findViewById(R.id.participants);
        status = (EditText) findViewById(R.id.status);
        description = (EditText) findViewById(R.id.description);

        save = (Button) findViewById(R.id.save);
        saveandnew = (Button) findViewById(R.id.saveandnew);
        cancel = (Button) findViewById(R.id.cancel);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        footer = (LinearLayout) findViewById(R.id.footer);

        Onclicks();

        if (StaticData.editevent) {
            Renderdata();
            FieldDisable();
            toolbar.setTitle("Edit Event");
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void Onclicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(fromdate.getText().toString().trim()))) {
                    if (!("".equals(todate.getText().toString().trim()))) {
                        AddLead();
                        CleartextFields();
                        finish();
                        Toast.makeText(AddEvent.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
                    } else todate.setError("Field cannot be empty");
                } else fromdate.setError("Field cannot be empty");
            }
        });

        saveandnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(fromdate.getText().toString().trim()))) {
                    if (!("".equals(todate.getText().toString().trim()))) {
                        AddLead();
                        CleartextFields();
                        Toast.makeText(AddEvent.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
                    } else todate.setError("Field cannot be empty");
                } else fromdate.setError("Field cannot be empty");
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
        activitymodel.setEventTitle(eventname.getText().toString());
        activitymodel.setFromDate(fromdate.getText().toString());
        activitymodel.setToDate(todate.getText().toString());
        activitymodel.setOwnedBy(host.getText().toString());
        activitymodel.setParticipants(participants.getText().toString());
        activitymodel.setDescription(description.getText().toString());
        activitymodel.setStatus(status.getText().toString());
        activitymodel.setActivityType("Event");
        DB.AddActivity(activitymodel);
    }

    public static void CleartextFields() {
        eventname.setText("");
        fromdate.setText("");
        todate.setText("");
        host.setText("");
        participants.setText("");
        status.setText("");
        description.setText("");
    }

    private void Renderdata() {
        eventname.setText(DB.getActivitymodel().getEventTitle());
        fromdate.setText(DB.getActivitymodel().getFromDate());
        todate.setText(DB.getActivitymodel().getToDate());
        host.setText(DB.getActivitymodel().getOwnedBy());
        participants.setText(DB.getActivitymodel().getParticipants());
        status.setText(DB.getActivitymodel().getStatus());
        description.setText(DB.getActivitymodel().getDescription());
    }

    private void FieldDisable() {
        eventname.setEnabled(false);
        fromdate.setEnabled(false);
        todate.setEnabled(false);
        host.setEnabled(false);
        participants.setEnabled(false);
        status.setEnabled(false);
        description.setEnabled(false);
        footer.setVisibility(View.GONE);
    }

    private void FieldEnable() {
        eventname.setEnabled(true);
        fromdate.setEnabled(true);
        todate.setEnabled(true);
        host.setEnabled(true);
        participants.setEnabled(true);
        status.setEnabled(true);
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
            startActivity(new Intent(AddEvent.this, ActivityList.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
