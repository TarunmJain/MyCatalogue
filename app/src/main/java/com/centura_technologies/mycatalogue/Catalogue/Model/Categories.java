package com.centura_technologies.mycatalogue.Catalogue.Model;

/**
 * Created by Centura User1 on 22-08-2016.
 */
public class Categories {
    private String Id;
    private String SectionId;
    private String Title;
    private String ImageUrl;

    public Categories(){
        Id="";
        SectionId="";
        Title="";
        ImageUrl="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String sectionId) {
        SectionId = sectionId;
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
}
