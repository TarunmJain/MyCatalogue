package com.centura_technologies.mycatalogue.Catalogue.Model;

/**
 * Created by Centura on 28-04-2016.
 */
public class AttributeClass {

    String AttributeName;
    String AttributeValue;

    public AttributeClass(){
        AttributeName="";
        AttributeValue="";
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeValue() {
        return AttributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        AttributeValue = attributeValue;
    }
}
