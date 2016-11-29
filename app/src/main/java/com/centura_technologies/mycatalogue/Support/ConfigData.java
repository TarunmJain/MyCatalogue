package com.centura_technologies.mycatalogue.Support;

import com.centura_technologies.mycatalogue.test.StorageUtils;

import java.util.ArrayList;

/**
 * Created by Centura on 23-11-2016.
 */

public class ConfigData {

    public static int selectedStoregePosition=0;
    public static String selectedStoregePath="";
    public static String selectedStoregefolder="";
    public static boolean SYNCNOW = false;
    public static boolean CloseConfigPage=false;
    public static ArrayList<StorageUtils.StorageInfo> StorageList = new ArrayList<>();
}
