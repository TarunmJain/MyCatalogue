package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.ArrayList;

/**
 * Created by Centura on 12-11-2016.
 */

public class AttachmentGroup {
    public String GroupTitle;
    public ArrayList<AttchmentClass> attchments;

    public AttachmentGroup(){
        GroupTitle="";
        attchments=new ArrayList<AttchmentClass>();
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
