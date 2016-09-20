package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 29-04-2016.
 */
public class FilterItem {
    private String Title;
    public ArrayList<Valuepair> Value;

    public void FilterItem() {

        Title = "";
        Value = new ArrayList<Valuepair>();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Valuepair> getValue() {
        return Value;
    }

    public void setValue(ArrayList<Valuepair> value) {
        Value = value;
    }


}

