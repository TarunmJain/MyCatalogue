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
    ArrayList<Products> model;
    ArrayList<String> images;
    Button play;
    SeekBar seekBar1;
    private boolean isTouchEnable;
    boolean playing;
    int SpeedTime=3000;
    //ArrayList<Integer> myImageList;
    //TextView product_title;
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

        //product_title=(TextView)findViewById(R.id.product_title);
        model=new ArrayList<Products>();
        model= DB.getShortlistedlist();
        images=new ArrayList<String>();
        for(int j=0;j<model.size();j++){
            images.add(model.get(j).getImageUrl());
        }
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        for (int i = 0; i <images.size(); i++) {
            ImageView imageView = new ImageView(SlideShow.this);
            //product_title.setText(model.get(i).getTitle().toString());
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
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SpeedTime=seekBar.getMax();

                //textView.setText("Covered: " + progress + "/" + seekBar.getMax());
               // Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

       /* myImageList = new ArrayList<>();
        myImageList.add(R.drawable.background_1);
        myImageList.add(R.drawable.background_2);
        myImageList.add(R.drawable.background_3);
        myImageList.add(R.drawable.background_4);
        myImageList.add(R.drawable.background_5);
        myImageList.add(R.drawable.background_6);
        myImageList.add(R.drawable.background_7);
        for (int i = 0; i < myImageList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(myImageList.get(i));
            myViewFlipper.addView(imageView);
        }*/
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
        /*myViewFlipper.setAutoStart(true);
        myViewFlipper.setFlipInterval(5000);
        myViewFlipper.startFlipping();*/
    }

   /* @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SpeedTime=progress;
        //Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        SpeedTime=seekBar.getMax();
        //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
    }*/

    public void setTouchEnable(boolean isTouchEnable) {
        this.isTouchEnable = isTouchEnable;
    }

    public boolean isTouchEnable() {
        return isTouchEnable;
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
