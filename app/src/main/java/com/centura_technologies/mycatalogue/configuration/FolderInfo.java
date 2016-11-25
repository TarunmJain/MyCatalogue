package com.centura_technologies.mycatalogue.configuration;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.Login.Controller.Splash;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.ConfigData;
import com.centura_technologies.mycatalogue.Support.GenericData;

import java.io.File;

public class FolderInfo extends AppCompatActivity {
    ImageView next, previous;
    EditText foldername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_info);
        overridePendingTransition(R.anim.fade_in_config, R.anim.fadeout);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        foldername = (EditText) findViewById(R.id.foldername);
        foldername.setText(ConfigData.selectedStoregefolder);
        GenericData.requestStorage(FolderInfo.this);
        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String folderNameStr = foldername.getText().toString();

                                        if (folderNameStr != null)
                                            if (!folderNameStr.matches(""))
                                                if (!folderNameStr.contains(" ")) {
                                                    //File folderDir = new File("/" + ConfigData.selectedStoregePath + "/" + folderNameStr);
                                                    ConfigData.selectedStoregePath += "/" + folderNameStr;
                                                    File folderDir = new File(ConfigData.selectedStoregePath);
                                                    String secStore = System.getenv("SECONDARY_STORAGE");
                                                    File file = new File(secStore + "/" + folderNameStr);
                                                    if (!file.exists())
                                                        proceed(folderDir);
                                                    else alert(file);
                                                } else
                                                    Toast.makeText(FolderInfo.this, "Error Occoured, Directory Name Should be a Single Word !", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(FolderInfo.this, "Error Occoured, Name Cannot be Empyt!", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(FolderInfo.this, "Error Occoured, Name Cannot be Null!", Toast.LENGTH_SHORT).show();


                                    }
                                }

        );

        previous.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(FolderInfo.this, StorageConfiguration.class));
                                            finish();
                                        }
                                    }

        );

    }

    private void proceed(File folderDir) {
        if (folderDir.exists()) {
            folderDir.delete();
        }
        if ((folderDir.mkdirs() || folderDir.isDirectory())) {
            ConfigData.selectedStoregefolder = foldername.getText().toString();
            startActivity(new Intent(FolderInfo.this, SyncAll.class));
            finish();
        } else
            Toast.makeText(FolderInfo.this, "Error Occoured, Check for Special Charecters!", Toast.LENGTH_SHORT).show();
    }

    public void alert(final File folderDir) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Directory Already Exist, Replace Data ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        proceed(folderDir);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog1 = alertDialogBuilder.create();
                        alertDialog1.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private boolean isExternalStoragePresent(Context context) {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if (!((mExternalStorageAvailable) && (mExternalStorageWriteable))) {
            Toast.makeText(context, "SD card not present", Toast.LENGTH_LONG)
                    .show();

        }
        return (mExternalStorageAvailable) && (mExternalStorageWriteable);
    }

}
