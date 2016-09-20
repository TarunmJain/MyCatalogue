package com.centura_technologies.mycatalogue.Catalogue.View;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ViewPagerAdapter extends PagerAdapter {

	Context mContext;
	PhotoViewAttacher mAttacher;
	LayoutInflater mLayoutInflater;
	ArrayList<String> mResources;
	ImageView imageView;

	public  ViewPagerAdapter(Context context, ArrayList<String> pager_items) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResources = pager_items;
	}

	@Override
	public int getCount() {
		return mResources.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);
		imageView = (ImageView) itemView.findViewById(R.id.imageView);
		if(!mResources.get(position).matches("")&&mResources.get(position)!=null)
			GenericData.setImage(mResources.get(position), imageView, mContext);
		container.addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}
}
