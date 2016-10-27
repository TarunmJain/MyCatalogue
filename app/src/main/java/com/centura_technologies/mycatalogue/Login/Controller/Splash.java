package com.centura_technologies.mycatalogue.Login.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Dashboard.Controller.Dashboard;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.ApiData;
import com.centura_technologies.mycatalogue.Support.DBHelper.DbHelper;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class Splash extends Activity {
    static SharedPreferences sharedPreferences;
    static String Login_Status;
    static TextView info;
    static ProgressBar progressBar;
    public static final String MyPref = "MyPref";
    public static final int SPLASH_DISPLAY_LENGTH = 1000;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        info = (TextView) findViewById(R.id.info);
        db = new DbHelper(Splash.this);
        ApiData.renderCustomers();
        sharedPreferences = this.getSharedPreferences(GenericData.MyPref, this.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getString(GenericData.Sp_Status, "").matches("LoggedIn")) {
                    db.loadinitialmodel();
                    // SyncClass.syncFilters(Splash.this);
                    StaticData.Options = "Catalogue";
                    StaticData.DrawerTextDisable = "Catalogue";
                    startActivity(new Intent(Splash.this, SectionCatalogue.class));
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

        /*SplashNetCheck(Splash.this);
        GenericData.requestCamera(Splash.this);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(GenericData.Sp_DeviceId, "").matches(""))
            setDeviceId();
        else {
            StaticData.DeviceId = sharedPreferences.getString(GenericData.Sp_DeviceId, "");
        }*/
    }
     /*   private void setDeviceId() {
            info.setText("Gcm Registration In Process");
            GCMClientManager pushClientManager = new GCMClientManager(Splash.this, Urls.PROJECT_NUMBER);
            pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                @Override
                public void onSuccess(String registrationId, boolean isNewRegistration) {
                    StaticData.DeviceId = registrationId;
                    sharedPreferences.edit().putString(GenericData.Sp_DeviceId, registrationId).commit();
                }

                @Override
                public void onFailure(String ex) {
                    StaticData.DeviceId = "Registration Failed";
                    sharedPreferences.edit().putString(GenericData.Sp_DeviceId, "Registration Failed").commit();
                    super.onFailure(ex);
                }
            });
        }

    public static void checkversion(final Context context) {
        progressBar.setVisibility(View.VISIBLE);
        info.setText("Checking For Updates!");
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.ApiVersion, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ApplicationClass.sharedPreferences = context.getSharedPreferences(GenericData.MyPref, Context.MODE_PRIVATE);
                Login_Status = sharedPreferences.getString(GenericData.Sp_Status, "");
                if (response.optInt("ApiVersion") <= StaticData.ApiVersion) {
                    if (!Login_Status.matches("LoggedIn")) {
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                       *//* StaticData.cachesetting(context);
                        StaticData.cachecustomers(context);
                        StaticData.cachecatagories(context);*//*
                        SplashNetCheck(context);
                        Intent intent = new Intent(context, Dashboard.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                } else {
                    info.setText("Please Updates!");
                    Button update;
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.updatenow);
                    dialog.show();
                    update = (Button) dialog.findViewById(R.id.update);
                    *//*update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.centura_technologies.mystore_seller"));
                            context.startActivity(browserIntent);
                        }
                    });*//*
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                GenericData.apiError(context, volleyError);
            }
        });
        ApplicationClass.makerequest(jsonObjReq);
    }


    public static boolean SplashNetCheck(final Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected == false) {
            *//*Toast.makeText(context, "Please Check Internet Connectivity", Toast.LENGTH_LONG).show();
            return false;*//*
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
                        ((Activity)context).startActivity(new Intent(context, Splash.class));
                        ((Activity)context).finish();
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        progressBar.setVisibility(View.GONE);
        if (requestCode == GenericData.PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GenericData.requestStorage(Splash.this);

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                info.setText("Permission Is Mandatory. Please Clear App Data and try Again");
                GenericData.requestCamera(Splash.this);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        } else if (requestCode == GenericData.PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GenericData.requestContacts(Splash.this);

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                info.setText("Permission Is Mandatory. Please Clear App Data and try Again");
                GenericData.requestStorage(Splash.this);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        } else if (requestCode == GenericData.PERMISSION_REQUEST_CONTACT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GenericData.requestPermission(Splash.this);
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                info.setText("Permission Is Mandatory. Please Clear App Data and try Again");
                GenericData.requestContacts(Splash.this);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        } else if (requestCode == GenericData.PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkversion(Splash.this);
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                info.setText("Permission Is Mandatory. Please Clear App Data and try Again");
                GenericData.requestPermission(Splash.this);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
*/


}
