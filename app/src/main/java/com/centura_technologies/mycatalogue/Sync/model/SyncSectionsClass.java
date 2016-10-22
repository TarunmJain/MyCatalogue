package com.centura_technologies.mycatalogue.Sync.Model;

import java.util.ArrayList;

/**
 * Created by Centura on 30-09-2016.
 */
public class SyncSectionsClass {
    public String StoreCode;
    public ArrayList<String> SectionIds;

    public  SyncSectionsClass (){
        StoreCode="";
        SectionIds=new ArrayList<String>();
    }
}
