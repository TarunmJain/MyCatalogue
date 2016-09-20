package com.centura_technologies.mycatalogue.Support;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by Centura on 12-05-2016.
 */
public class ImageCache {
    public String fileURL;
    public String fName;
    public Context context;
    public InputStream strean;

    public ImageCache(String fileURL, String fName, Context context) {
        this.fileURL = fileURL;
        this.fName = fName;
        this.context = context;
        this.strean = null;
    }
}
