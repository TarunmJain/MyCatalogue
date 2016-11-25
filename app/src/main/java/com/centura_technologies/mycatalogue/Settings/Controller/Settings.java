package com.centura_technologies.mycatalogue.Settings.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Login.Controller.Login;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Sync.Controller.SyncClass;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class Settings extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout syncall,syncsections,synccollections,others,taxpane;
    public static ImageView allicon,sectionicon,collectionicon,othersicon;
    RotateAnimation rotate;
    TextView taxval;
    Button set,cancel;
    EditText enterval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        syncall=(RelativeLayout)findViewById(R.id.syncall);
        syncsections=(RelativeLayout)findViewById(R.id.syncsections);
        synccollections=(RelativeLayout)findViewById(R.id.synccollections);
        others=(RelativeLayout)findViewById(R.id.others);
        taxpane=(RelativeLayout)findViewById(R.id.taxpane);
        allicon=(ImageView)findViewById(R.id.allicon);
        sectionicon=(ImageView)findViewById(R.id.sectionicon);
        collectionicon=(ImageView)findViewById(R.id.collectionicon);
        othersicon=(ImageView)findViewById(R.id.othersicon);
        taxval=(TextView)findViewById(R.id.taxval);
        OnClicks();
        rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());

    }

    private void OnClicks() {
        syncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allicon.startAnimation(rotate);
                Sync.initialapi(Settings.this);
                syncall.setClickable(false);
                syncsections.setClickable(false);
                synccollections.setClickable(false);
                others.setClickable(false);
            }
        });
        syncsections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, SyncClass.class));
                syncall.setClickable(true);
                syncsections.setClickable(true);
                synccollections.setClickable(true);
                others.setClickable(true);
            }
        });
        synccollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, SyncClass.class));
                syncall.setClickable(true);
                syncsections.setClickable(true);
                synccollections.setClickable(true);
                others.setClickable(true);
            }
        });
        taxpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Settings.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_taxval);
                enterval=(EditText)dialog.findViewById(R.id.enterval);
                set=(Button)dialog.findViewById(R.id.set);
                cancel=(Button)dialog.findViewById(R.id.cancel);
                dialog.show();
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taxval.setText(enterval.getText().toString());
                        dialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1 = menu.findItem(R.id.slideshow);
        register1.setVisible(false);
        MenuItem register2 = menu.findItem(R.id.shortlist);
        register2.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
