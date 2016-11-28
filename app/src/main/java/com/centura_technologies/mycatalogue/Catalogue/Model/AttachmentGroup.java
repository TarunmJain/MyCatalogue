package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura on 12-11-2016.
 */

public class AttachmentGroup {
    public String GroupTitle;
    public int Type=AttchmentClass.GROUP;
    public ArrayList<AttchmentClass> attchments;
    public AttchmentClass IndividualAttachment;

    public AttachmentGroup(){
        GroupTitle="";
        Type=AttchmentClass.GROUP;
        attchments=new ArrayList<AttchmentClass>();
        IndividualAttachment=new AttchmentClass();
    }

    public AttchmentClass getIndividualAttachment() {
        return IndividualAttachment;
    }

    public void setIndividualAttachment(AttchmentClass individualAttachment) {
        IndividualAttachment = individualAttachment;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getGroupTitle() {
        return GroupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        GroupTitle = groupTitle;
    }

    public ArrayList<AttchmentClass> getAttchments() {
        return attchments;
    }

    public void setAttchments(ArrayList<AttchmentClass> attchments) {
        this.attchments = attchments;
    }
}
