package com.centura_technologies.mycatalogue.test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Centura User1 on 07-11-2016.
 */

public class GridViewItem extends GridView {
    public GridViewItem(Context context) {
        super(context);
    }

    public GridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec/2); // This is the key that will make the height equivalent to its width
    }
}
