package com.centura_technologies.mycatalogue.Settings.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.Sync.Controller.SyncClass;
import com.centura_technologies.mycatalogue.test.StorageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Centura User1 on 23-09-2016.
 */
public class Settings extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout syncall,syncsections,taxpane,filestorage,foldername;
    LinearLayout sync,vat,filemanager;
    public static ImageView allicon,sectionicon;
    CardView cardview_vat,cardview_sync,cardview_folder;
    RotateAnimation rotate;
    TextView taxval;
    public static TextView name;
    Button set,cancel;
    TextView storagepath;
    EditText enterval,setfoldername;
    RecyclerView StorageOptions;
    public static String tempPath="";
    public static String StpragePathName="";
    public static int tempselectedStoregePosition=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        syncall=(RelativeLayout)findViewById(R.id.syncall);
        syncsections=(RelativeLayout)findViewById(R.id.syncsections);
        taxpane=(RelativeLayout)findViewById(R.id.taxpane);
        filestorage=(RelativeLayout)findViewById(R.id.filestorage);
        storagepath = (TextView)findViewById(R.id.storagepath);
        foldername=(RelativeLayout)findViewById(R.id.foldername);
        filemanager=(LinearLayout)findViewById(R.id.filemanager);
        sync=(LinearLayout)findViewById(R.id.sync);
        vat=(LinearLayout)findViewById(R.id.vat);
        cardview_vat=(CardView)findViewById(R.id.cardview_vat);
        cardview_sync=(CardView)findViewById(R.id.cardview_sync);
        cardview_folder=(CardView)findViewById(R.id.cardview_folder);
        allicon=(ImageView)findViewById(R.id.allicon);
        sectionicon=(ImageView)findViewById(R.id.sectionicon);
        taxval=(TextView)findViewById(R.id.taxval);
        name=(TextView)findViewById(R.id.name);
        name.setText("MyCatalogueData");
        ConfigData.selectedStoregefolder="MyCatalogueData";

        List<StorageUtils.StorageInfo> lists = StorageUtils.getStorageList();
        ConfigData.StorageList = new ArrayList<>();
        for (int x = 0; x < lists.size(); x++)
            ConfigData.StorageList.add(lists.get(x));
        storagepath.setText(StpragePathName);
        if (ConfigData.selectedStoregePath.matches("")) {
            ConfigData.selectedStoregePath = ConfigData.StorageList.get(0).path;
            storagepath.setText("Internal Storage");
            ConfigData.selectedStoregePosition = 0;
            tempselectedStoregePosition=0;
        }

        OnClicks();
        rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());

    }

    private void OnClicks() {
        syncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allicon.startAnimation(rotate);
                Sync.initialapi(Settings.this);
                syncall.setClickable(false);
                syncsections.setClickable(false);
            }
        });
        syncsections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, SyncClass.class));
                syncall.setClickable(true);
                syncsections.setClickable(true);
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_sync.setVisibility(View.VISIBLE);
                cardview_vat.setVisibility(View.GONE);
                cardview_folder.setVisibility(View.GONE);

            }
        });

        vat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_vat.setVisibility(View.VISIBLE);
                cardview_sync.setVisibility(View.GONE);
                cardview_folder.setVisibility(View.GONE);

            }
        });
        filemanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_folder.setVisibility(View.VISIBLE);
                cardview_vat.setVisibility(View.GONE);
                cardview_sync.setVisibility(View.GONE);
            }
        });

        filestorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog storagedialog=new Dialog(Settings.this);
                storagedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                storagedialog.setContentView(R.layout.dialog_filestorage);
                StorageOptions = (RecyclerView)storagedialog.findViewById(R.id.storegeOptions);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                tempselectedStoregePosition=ConfigData.selectedStoregePosition;
                StorageOptions.setLayoutManager(new LinearLayoutManager(Settings.this));
                StorageOptions.setAdapter(new StorageAdapter(Settings.this));
                set=(Button)storagedialog.findViewById(R.id.filestorageset);
                cancel=(Button)storagedialog.findViewById(R.id.filestoragecancel);
                storagedialog.show();
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfigData.selectedStoregePath=tempPath;
                        ConfigData.selectedStoregePosition=tempselectedStoregePosition;
                        storagepath.setText(StpragePathName);
                        storagedialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        storagedialog.cancel();
                    }
                });
            }
        });

        foldername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Dialog namedialog=new Dialog(Settings.this);
                namedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                namedialog.setContentView(R.layout.dialog_foldername);
                setfoldername=(EditText)namedialog.findViewById(R.id.setfoldername);
                set=(Button)namedialog.findViewById(R.id.folderset);
                cancel=(Button)namedialog.findViewById(R.id.foldercancel);
                namedialog.show();
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setText(setfoldername.getText().toString());
                        filename=setfoldername.getText().toString();
                        namedialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        namedialog.cancel();
                    }
                });*/
            }
        });

        taxpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Settings.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_taxval);
                enterval=(EditText)dialog.findViewById(R.id.enterval);
                set=(Button)dialog.findViewById(R.id.set);
                cancel=(Button)dialog.findViewById(R.id.cancel);
                dialog.show();
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taxval.setText(enterval.getText().toString());
                        dialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem register = menu.findItem(R.id.logout);
        register.setVisible(false);
        MenuItem register1 = menu.findItem(R.id.slideshow);
        register1.setVisible(false);
        MenuItem register2 = menu.findItem(R.id.shortlist);
        register2.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
