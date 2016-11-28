package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class CollectionModel {

    private String Id;
    private String Title;
    private String ImageUrl;
    private int Version;
    private ArrayList<String> ProductIds;

    public CollectionModel(){
        Id="";
        Title="";
        ImageUrl="";
        Version=0;
        ProductIds=new ArrayList<String>();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public ArrayList<String> getProductIds() {
        return ProductIds;
    }

    public void setProductIds(ArrayList<String> productIds) {
        ProductIds = productIds;
    }
}
