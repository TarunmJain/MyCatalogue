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
public class AddTask extends AppCompatActivity {
    Toolbar toolbar;
    static EditText task_owner, subject, duedate, contact, status, priority, description;
    Button save, saveandnew, cancel;
    FloatingActionButton fab;
    LinearLayout footer;
    public static ActivitiesModel activitymodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Create Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StaticData.context = AddTask.this;

        task_owner = (EditText) findViewById(R.id.task_owner);
        subject = (EditText) findViewById(R.id.subject);
        duedate = (EditText) findViewById(R.id.duedate);
        contact = (EditText) findViewById(R.id.contact);
        status = (EditText) findViewById(R.id.status);
        priority = (EditText) findViewById(R.id.priority);
        description = (EditText) findViewById(R.id.description);

        save = (Button) findViewById(R.id.save);
        saveandnew = (Button) findViewById(R.id.saveandnew);
        cancel = (Button) findViewById(R.id.cancel);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        footer = (LinearLayout) findViewById(R.id.footer);

        Onclicks();

        if (StaticData.edittask) {
            Renderdata();
            FieldDisable();
            toolbar.setTitle("Edit Task");
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void Onclicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(task_owner.getText().toString().trim()))) {
                    if (!("".equals(subject.getText().toString().trim()))) {
                        AddTask();
                        CleartextFields();
                        finish();
                        Toast.makeText(AddTask.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
                    } else subject.setError("Field cannot be empty");
                } else task_owner.setError("Field cannot be empty");
            }
        });

        saveandnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(task_owner.getText().toString().trim()))) {
                    if (!("".equals(subject.getText().toString().trim()))) {
                        AddTask();
                        CleartextFields();
                        Toast.makeText(AddTask.this, "Waiting for Api...", Toast.LENGTH_SHORT).show();
                    } else subject.setError("Field cannot be empty");
                } else task_owner.setError("Field cannot be empty");
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

    public static void AddTask() {
        activitymodel = new ActivitiesModel();
        activitymodel.setOwnedBy(task_owner.getText().toString());
        activitymodel.setSubject(subject.getText().toString());
        activitymodel.setDueDate(duedate.getText().toString());
        activitymodel.setContact(contact.getText().toString());
        activitymodel.setStatus(status.getText().toString());
        activitymodel.setPriority(priority.getText().toString());
        activitymodel.setDescription(description.getText().toString());
        activitymodel.setActivityType("Task");
        DB.AddActivity(activitymodel);
    }

    public static void CleartextFields() {
        task_owner.setText("");
        subject.setText("");
        duedate.setText("");
        contact.setText("");
        status.setText("");
        priority.setText("");
        description.setText("");
    }

    private void Renderdata() {
        task_owner.setText(DB.getActivitymodel().getOwnedBy());
        subject.setText(DB.getActivitymodel().getSubject());
        duedate.setText(DB.getActivitymodel().getDueDate());
        contact.setText(DB.getActivitymodel().getContact());
        status.setText(DB.getActivitymodel().getStatus());
        priority.setText(DB.getActivitymodel().getPriority());
        description.setText(DB.getActivitymodel().getDescription());
    }

    private void FieldDisable() {
        task_owner.setEnabled(false);
        subject.setEnabled(false);
        duedate.setEnabled(false);
        contact.setEnabled(false);
        status.setEnabled(false);
        priority.setEnabled(false);
        description.setEnabled(false);
        footer.setVisibility(View.GONE);
    }

    private void FieldEnable() {
        task_owner.setEnabled(true);
        subject.setEnabled(true);
        duedate.setEnabled(true);
        contact.setEnabled(true);
        status.setEnabled(true);
        priority.setEnabled(true);
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
            startActivity(new Intent(AddTask.this, ActivityList.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
