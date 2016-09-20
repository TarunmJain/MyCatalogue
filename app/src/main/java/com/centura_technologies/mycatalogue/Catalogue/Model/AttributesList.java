package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 17-09-2016.
 */
public class AttributesList {
    private String attributeName;
    private ArrayList<String> attributeValues;

    public AttributesList(){
        attributeName="";
        attributeValues=new ArrayList<String>();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public ArrayList<String> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(ArrayList<String> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
