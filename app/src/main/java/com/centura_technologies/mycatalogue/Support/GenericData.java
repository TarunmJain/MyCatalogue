package com.centura_technologies.mycatalogue.Support;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.centura_technologies.mycatalogue.AboutUs.Controller.AboutUs;
import com.centura_technologies.mycatalogue.Activity.Controller.ActivityList;
import com.centura_technologies.mycatalogue.Catalogue.Controller.Catalogue;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Dashboard.Controller.Dashboard;
import com.centura_technologies.mycatalogue.Leads.Controller.LeadsList;
import com.centura_technologies.mycatalogue.Login.Controller.Login;
import com.centura_technologies.mycatalogue.Order.Controller.Order;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Settings.Controller.Settings;
import com.centura_technologies.mycatalogue.Shortlist.Controller.Shortlist;
import com.centura_technologies.mycatalogue.Support.Apis.Urls;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class GenericData {
    public static boolean imagesChached = false;
    public static int PERMISSION_REQUEST_CAMERA = 1;
    public static int PERMISSION_REQUEST_STORAGE = 2;
    public static final int PERMISSION_REQUEST_CONTACT = 3;
    public static final int PERMISSION_REQUEST_CODE = 4;
    public static int GLIDESIGNATURE = 0;
    public static final String MyPref = "MyPref";
    public static final String Sp_Status = "Status";
    public static final String Sp_StoreCode = "StoreCode";
    public static final String Sp_Username = "Username";
    public static final String Sp_Password = "Password";
    public static final String Sp_DeviceId = "DeviceId";
    public static final String Sp_StoreProductType = "StoreProductType";
    public static ArrayList<Bundle> notificationList = new ArrayList<Bundle>();
    public static boolean progressAlive = false;
    public static Button button;
    public static Button button1;
    public static Activity a;
    public static SharedPreferences sharedPreferences;
    public static TextView maintext, subtext;
    static LinearLayout catalogues, products, shortlist, order, sync, aboutus, logout;
    static TextView dashboardtext;
    static DrawerLayout Drawer;
    static boolean downloadAlive = false;

    static ProgressDialog pDialog;

    public static void ShowdownloadingDialog(Context context, Boolean flag) {
        context = context.getApplicationContext();
        if (flag) {
            if (downloadAlive) {
                pDialog.cancel();
                downloadAlive = false;
            }
               /* pDialog=new Dialog(context);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setContentView(R.layout.customload);*/
            pDialog = new ProgressDialog(context);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setMessage("Downloading data...");
            downloadAlive = true;
            pDialog.show();
        } else {
            if (downloadAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                downloadAlive = false;
            }
        }
    }

    public static void ShowDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                pDialog.cancel();
                progressAlive = false;
            }
            {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage(message);
                pDialog.setCanceledOnTouchOutside(false);
                progressAlive = true;
                pDialog.show();
            }
        } else {
            if (progressAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                progressAlive = false;
            }
        }
    }

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
      /*  DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;*/
    }


    public static boolean NetCheck(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected == false) {
            /*Toast.makeText(context, "Please Check Internet Connectivity", Toast.LENGTH_LONG).show();
            return false;*/
            try {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkinternet);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btn = (Button) dialog.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        NetCheck(context);
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return true;
    }


    public static boolean sucess(JSONObject result, Context context) {
        ShowDialog(context, "", false);
        if (result.optString("IsSuccess").matches("true"))
            return true;
        else return false;
    }

    public static void apiError(final Context context, VolleyError volleyError) {
        if (volleyError instanceof NoConnectionError) {
            try {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkinternet);
                dialog.show();
                Button btn = (Button) dialog.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        //NetCheck(context);
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            Toast.makeText(context, "Server Down! Please get back Later", Toast.LENGTH_SHORT).show();
        }
    }


    public static Spanned formatHtml(String data) {
        if (data != null)
            return Html.fromHtml(data);
        else
            return Html.fromHtml("");
    }

    /*public static void setImage(String url, ImageView img,Context context) {
        if (imagesChached) {
            DbHelper db = new DbHelper(context);
            String image = db.returnImage(Urls.parentIP + url);
            if (image != null)
                if (!image.matches("")) {
                    image = Environment.getExternalStorageDirectory().getAbsolutePath() + image;
                    Bitmap bitmap = BitmapFactory.decodeFile(image);
                    if (bitmap != null) {
                        // bitmap = Bitmap.createBitmap(bitmap, 0, 0, 400, 1000);
                        img.setImageBitmap(bitmap);
                    }
                }
        }
    }*/

    public static void setImage(String url, ImageView image, Context context) {
        DbHelper dbHelper = new DbHelper(context);
        url = dbHelper.returnImage(Urls.parentIP + url);
        if (url != null)
            if (!url.matches("")) {
                url = Environment.getExternalStorageDirectory().getAbsolutePath() + url;
                Bitmap bitmap = BitmapFactory.decodeFile(url);
                if (bitmap != null) {
                    // bitmap = Bitmap.createBitmap(bitmap, 0, 0, 400, 1000);
                    image.setImageBitmap(bitmap);
                }
            } else image.setImageResource(R.drawable.noimage);
        else image.setImageResource(R.drawable.noimage);

                    /*String path = Environment.getExternalStorageDirectory() + image;
                    File imgFile = new File(path);
                    if (imgFile.exists()) {
                        String abx = imgFile.getAbsolutePath();
                        Bitmap myBitmap = BitmapFactory.decodeFile(abx);
                        myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, 400, 1000);
                        img.setImageBitmap(myBitmap);
                    }*/
       /* if (url != null)
            if (!url.matches(""))
                //Picasso.with(context).load(Urls.parentIP + url).into(image);
                Glide
                        .with(context)
                        .load(Urls.parentIP + url)
                        .signature(new StringSignature(GLIDESIGNATURE + ""))
                        .crossFade()
                        .into(image);
            else image.setImageResource(R.drawable.noimage);
        else image.setImageResource(R.drawable.noimage);*/
    }

    public static void GenerateSignatureGlide() {
        int min = 10;
        int max = 1000;
        Random r = new Random();
        GLIDESIGNATURE = r.nextInt(max - min + 1) + min;
    }

    public static String Concatinate(String data, int length) {
        if (data.length() > length)
            return data.substring(0, length) + "...";
        else
            return data;
    }

    public static void requestCamera(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        } else {
            GenericData.requestStorage(context);
        }
    }

    public static void requestPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        } else {
            // Splash.checkversion(context);
        }
    }

    public static void requestPermission_ACCESS_COARSE_LOCATION(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    public static void requestStorage(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            }
        } else {
            GenericData.requestContacts(context);
        }
    }

    public static String getError(JSONObject result) {
        String Error = result.optString("Errors");
        if (Error != null)
            if (Error.length() > 5)
                Error = Error.substring(2, Error.length() - 2);
        return Error;
    }

    public static void requestContacts(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_CONTACTS)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_CONTACTS}, PERMISSION_REQUEST_CONTACT);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_CONTACTS}, PERMISSION_REQUEST_CONTACT);
            }
        } else {
            GenericData.requestPermission(context);
        }
    }

    public static void logout(final Context context) {
        a = ((Activity) context);
        sharedPreferences = a.getSharedPreferences(GenericData.MyPref, a.MODE_PRIVATE);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.login_alert);
        maintext = (TextView) dialog.findViewById(R.id.maintext);
        subtext = (TextView) dialog.findViewById(R.id.subtext);
        button = (Button) dialog.findViewById(R.id.btn);
        button1 = (Button) dialog.findViewById(R.id.btn1);
        maintext.setText("Logout " + sharedPreferences.getString(GenericData.Sp_StoreCode, ""));
        subtext.setText("Are you sure you want to logout " + sharedPreferences.getString(GenericData.Sp_StoreCode, "") + "?");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                context.startActivity(new Intent(context, Login.class));
                //StaticData.clearcachedata();
                editor.clear().commit();
                a.finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public static void DrawerOnClicks(final Context context) {
        final Activity a = ((Activity) context);
        //catalogues = (LinearLayout) a.findViewById(R.id.catalogues);
        products = (LinearLayout) a.findViewById(R.id.products);
        shortlist = (LinearLayout) a.findViewById(R.id.shortlist);
        order = (LinearLayout) a.findViewById(R.id.Order);
        sync = (LinearLayout) a.findViewById(R.id.sync);
        aboutus = (LinearLayout) a.findViewById(R.id.aboutus);
        logout = (LinearLayout) a.findViewById(R.id.logout);
        Drawer = (DrawerLayout) a.findViewById(R.id.drawer);

        /*catalogues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });*/
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.SelectedCategoryId = "-1";
                a.startActivity(new Intent(context, Catalogue.class));
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
        shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.startActivity(new Intent(context, Shortlist.class));
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.startActivity(new Intent(context, Order.class));
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.startActivity(new Intent(context, Settings.class));
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.startActivity(new Intent(context, AboutUs.class));
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawer.closeDrawer(Gravity.LEFT);
                GenericData.logout(context);
            }
        });
    }


}
