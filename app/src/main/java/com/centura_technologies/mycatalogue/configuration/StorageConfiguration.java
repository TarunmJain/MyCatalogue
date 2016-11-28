package com.centura_technologies.mycatalogue.configuration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.StorageAdapter;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.test.StorageUtils;

import java.util.ArrayList;
import java.util.List;

public class StorageConfiguration extends AppCompatActivity {

    RecyclerView StorageOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_configuration);
        overridePendingTransition(R.anim.fade_in_config, R.anim.fadeout);
        StorageOptions = (RecyclerView) findViewById(R.id.storegeOptions);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        List<StorageUtils.StorageInfo> lists = StorageUtils.getStorageList();
        ConfigData.StorageList = new ArrayList<>();
        for (int x = 0; x < lists.size(); x++)
            ConfigData.StorageList.add(lists.get(x));
        StorageOptions.setLayoutManager(new LinearLayoutManager(StorageConfiguration.this));
        StorageOptions.setAdapter(new StorageAdapter(StorageConfiguration.this));
    }
}
