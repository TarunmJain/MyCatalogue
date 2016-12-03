package com.centura_technologies.mycatalogue.Shortlist.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.centura_technologies.mycatalogue.Catalogue.Model.Products;
import com.centura_technologies.mycatalogue.Catalogue.Model.ShortlistProductModel;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-09-2016.
 */
public class SlideShow extends AppCompatActivity{
    Toolbar toolbar;
    ViewFlipper myViewFlipper;
    float initialXPoint, finalx;
    ArrayList<ShortlistProductModel> model;
    ArrayList<String> images;
    Button play;
    SeekBar seekBar1;
    private boolean isTouchEnable;
    boolean playing;
    int SpeedTime=3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("SlideShow");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        play=(Button)findViewById(R.id.play);
        seekBar1=(SeekBar)findViewById(R.id.seekBar1);
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        model=new ArrayList<ShortlistProductModel>();
        model= DB.getShortlistproductmodel();
        images=new ArrayList<String>();
        for(int j=0;j<model.size();j++){
            images.add(model.get(j).getImageUrl());
        }
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        for (int i = 0; i <images.size(); i++) {
            ImageView imageView = new ImageView(SlideShow.this);
            GenericData.setImage(images.get(i), imageView, SlideShow.this);
            myViewFlipper.addView(imageView);
        }
        seekBar1.setProgress(SpeedTime);
        playing=true;
        play.setBackgroundResource(R.drawable.ic_media_pause);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                SpeedTime = progresValue;
                playing=true;
                play.setBackgroundResource(R.drawable.ic_media_pause);
                play.performClick();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SpeedTime=seekBar.getMax();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing){
                    myViewFlipper.setAutoStart(true);
                    myViewFlipper.setFlipInterval(SpeedTime);
                    myViewFlipper.startFlipping();
                    playing=false;
                    play.setBackgroundResource(R.drawable.ic_media_pause);
                    setTouchEnable(false);
                }else {
                    myViewFlipper.stopFlipping();
                    playing=true;
                    play.setBackgroundResource(R.drawable.ic_media_play);
                    setTouchEnable(true);
                }

            }
        });
        play.performClick();
    }
    public void setTouchEnable(boolean isTouchEnable) {
        this.isTouchEnable = isTouchEnable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTouchEnable){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialXPoint = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    finalx = event.getX();
                    if (initialXPoint > finalx) {
                        if (myViewFlipper.getDisplayedChild() == images.size())
                            break;
                        myViewFlipper.showNext();
                    } else {
                        if (myViewFlipper.getDisplayedChild() == 0)
                            break;
                        myViewFlipper.showPrevious();
                    }
                    break;
            }
        }else {
            return false;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1=menu.findItem(R.id.slideshow);
        register1.setVisible(false);
        MenuItem register2=menu.findItem(R.id.shortlist);
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
