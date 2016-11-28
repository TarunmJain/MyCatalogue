package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 22-08-2016.
 */
public class Sections {
    private String Title;
    private String ImageUrl;
    private String Id;
    private String Priority;
    private int Version;
    public boolean Selected;


    public Sections(){
        Title="";
        ImageUrl="";
        Id="";
        Version=0;
        Selected=false;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
