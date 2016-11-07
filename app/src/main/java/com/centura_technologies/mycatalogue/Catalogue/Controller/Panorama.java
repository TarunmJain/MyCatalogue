package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.centura_technologies.mycatalogue.R;
import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;

import java.io.IOException;
import java.net.URL;

import javax.microedition.khronos.opengles.GL10;

public class Panorama extends AppCompatActivity {
    PLManager plManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        plManager = new PLManager(this);
        plManager.setContentView(R.layout.activity_panorama);
        plManager.onCreate();
        Intent i = getIntent();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        String Url=i.getStringExtra("url");
        Bitmap bitmap = BitmapFactory.decodeFile(Url, options);
        /*
        int[] maxSize = new int[1];
         GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);*/
        if(bitmap.getHeight()>=2048||bitmap.getWidth()>=2048){
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            bitmap =Bitmap.createScaledBitmap(bitmap, width, height, true);
        }
        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);
        panorama.setImage(new PLImage(bitmap, false));
        plManager.setPanorama(panorama);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onResume() {
        super.onResume();
        plManager.onResume();
    }

    @Override
    protected void onPause() {
        plManager.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        plManager.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return plManager.onTouchEvent(event);
    }
}
