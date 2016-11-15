package com.centura_technologies.mycatalogue.Catalogue.Model;

import android.webkit.MimeTypeMap;

/**
 * Created by Centura on 07-11-2016.
 */

public class AttchmentClass {
    public static int TYPE_IMAGE = 0;
    public static int TYPE_VEDIO = 1;
    public static int TYPE_WEB = 2;
    public static int TYPE_PDF = 3;
    public static int TYPE_PPT = 4;
    public static int TYPE_Panorama = 5;

    public String AttachmentUrl;
    public String AttachmentTitle;
    public String Type;
    public String Group;
    public int MediaType=-1;

    public AttchmentClass(){
        AttachmentUrl="";
        AttachmentTitle="";
        Type="";
        Group="";
    }

    public String getAttachmentUrl() {
        return AttachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        AttachmentUrl = attachmentUrl;
    }

    public String getAttachmentTitle() {
        return AttachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        AttachmentTitle = attachmentTitle;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
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
