package com.centura_technologies.mycatalogue.Catalogue.Model;

import android.webkit.MimeTypeMap;

/**
 * Created by Centura on 24-10-2016.
 */
public class DescriptionMenuClass {
    public static int TYPE_IMAGE = 0;
    public static int TYPE_VEDIO = 1;
    public static int TYPE_WEB = 2;
    public static int TYPE_PDF = 3;
    public static int TYPE_PPT = 4;
    public static int TYPE_Panorama = 5;
    public boolean isAttachment;
    public String URL;
    public String MimeString;
    public int MediaType;

    public DescriptionMenuClass(String url, boolean attchment) {
        if (url != null)
            if (!url.matches("")) {
                URL = url;
                MimeString = getMimeType(URL);
                if (MimeString != null)
                    if (!MimeString.matches("")) {
                        if (MimeString.toLowerCase().contains("image"))
                            if (attchment)
                                MediaType = TYPE_Panorama;
                            else
                                MediaType = TYPE_IMAGE;
                        if (MimeString.toLowerCase().contains("video"))
                            MediaType = TYPE_VEDIO;

                        if (MimeString.toLowerCase().contains("web"))
                            MediaType = TYPE_WEB;

                        if (MimeString.toLowerCase().contains("pdf"))
                            MediaType = TYPE_PDF;

                        if (MimeString.toLowerCase().contains("ppt"))
                            MediaType = TYPE_PPT;
                    }

            }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
