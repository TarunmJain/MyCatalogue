package com.centura_technologies.mycatalogue.configuration;

/**
 * Created by Centura on 26-11-2016.
 */

public class DataVersion {
    public static int SectionVersion=0;
    public static int CategoryVersion=0;
    public static int ProductVersion=0;
    public static int CollectionVersion=0;

    public static void SetData(int sectionVersion,int categoryVersion, int productVersion, int collectionVersion){
        SectionVersion=sectionVersion;
        CategoryVersion= categoryVersion;
        ProductVersion = productVersion;
        CollectionVersion= collectionVersion;
    }
}
