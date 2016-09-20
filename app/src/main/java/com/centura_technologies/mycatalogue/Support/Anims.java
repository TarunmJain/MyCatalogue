package com.centura_technologies.mycatalogue.Support;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.centura_technologies.mycatalogue.R;


/**
 * Created by Centura User3 on 10-03-2016.
 */
public class Anims implements Animation.AnimationListener {
  public static Animation slidedown,bounce,blink,fadein,fadeout,zoomout,zoomin;
    private static Activity activity;
  private static void Initiate(final Context context){
      activity=(Activity)context;
      slidedown = AnimationUtils.loadAnimation(context, R.anim.sildedown);
      fadein= AnimationUtils.loadAnimation(context, R.anim.fadein);
      fadeout= AnimationUtils.loadAnimation(context, R.anim.fadeout);
      blink= AnimationUtils.loadAnimation(context, R.anim.blink);
      bounce= AnimationUtils.loadAnimation(context, R.anim.bounce);
      zoomout= AnimationUtils.loadAnimation(context, R.anim.zoomout);
      zoomin= AnimationUtils.loadAnimation(context, R.anim.zoomin);

  }

    public static void setSildeDown(Context context,View view){
        Initiate(context);
        view.startAnimation(slidedown);
    }
    public static void setfadein(Context context,View view){
        Initiate(context);
        view.startAnimation(fadein);
    }
    public static void setFadeout(Context context,View view){
        Initiate(context);
        view.startAnimation(fadeout);
    }
    public static void setBlink(Context context,View view){
        Initiate(context);
        view.startAnimation(blink);
    }
    public static void setBounce(Context context,View view){
        Initiate(context);
        view.startAnimation(bounce);
    }
    public static void setZoomout(Context context,View view){
        Initiate(context);
        view.startAnimation(zoomout);
    }
    public static void setZoomin(Context context,View view){
        Initiate(context);
        view.startAnimation(zoomin);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
