package com.centura_technologies.mycatalogue.Login.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.centura_technologies.mycatalogue.Catalogue.Controller.SectionCatalogue;
import com.centura_technologies.mycatalogue.Dashboard.Controller.Dashboard;
import com.centura_technologies.mycatalogue.GCMClientManager;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.GenericData;
import com.centura_technologies.mycatalogue.Support.DBHelper.StaticData;
import com.centura_technologies.mycatalogue.Support.Apis.Sync;
import com.centura_technologies.mycatalogue.Support.Apis.Urls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Centura User1 on 06-08-2016.
 */
public class Login extends Activity {
    EditText username, password,companyid;
    Button login;
    RelativeLayout background;
    String DeviceId="";
    String PROJECT_NUMBER="965562513385";
    Button button,button1;
    SharedPreferences sharedPreferences;


    int images[] = {R.drawable.background_1,
            R.drawable.background_2,
            R.drawable.background_3,
            R.drawable.background_4,
            R.drawable.background_5,
            R.drawable.background_6,
            R.drawable.background_7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        companyid = (EditText) findViewById(R.id.companyid);
        login = (Button) findViewById(R.id.login);
        background = (RelativeLayout) findViewById(R.id.background);
        sharedPreferences = this.getSharedPreferences(GenericData.MyPref, this.MODE_PRIVATE);
        loginslide();
        setDeviceId();
        onclick();
    }
    private void setDeviceId() {
        GCMClientManager pushClientManager = new GCMClientManager(Login.this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                DeviceId = registrationId;
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }

    private void onclick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null) {
                    if (password.getText() != null) {
                        if (companyid.getText() != null) {
                            RequestQueue queue = Volley.newRequestQueue(Login.this);
                            Map<String, String> param = new HashMap<String, String>();
                            param.put("UserName", username.getText().toString().trim());
                            param.put("Password", password.getText().toString().replaceAll("\\s+", ""));
                            param.put("StoreCode", companyid.getText().toString().trim());
                            //StaticData.StoreCode = companyid.getText().toString();
                            param.put("DevideId", DeviceId);
                            GenericData.ShowDialog(Login.this, "Loading...", true);
                            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.Login, new JSONObject(param), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    GenericData.ShowDialog(Login.this, "Loading...", false);
                                    if (GenericData.sucess(response, Login.this)) {
                                        Sync.SyncSectionList(Login.this);

                                        // SyncClass.syncFilters(Login.this);
                                        // SyncClass.syncproducts(Login.this);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(GenericData.Sp_Username, username.getText().toString());
                                        editor.putString(GenericData.Sp_Password, password.getText().toString());
                                        editor.putString(GenericData.Sp_StoreCode, companyid.getText().toString());
                                        StaticData.CurrentSalesMan.Id=username.getText().toString();
                                        StaticData.CurrentSalesMan.Username=username.getText().toString();
                                        StaticData.CurrentSalesMan.Name=response.optJSONObject("Data").optString("UserName");
                                        StaticData.CurrentSalesMan.Phone=response.optJSONObject("Data").optString("UserPhone");
                                        StaticData.CurrentSalesMan.Email=response.optJSONObject("Data").optString("UserEmail");

                                        editor.putString(GenericData.Sp_Status, "LoggedIn");
                                        editor.commit();
                                        StaticData.Options = "Catalogue";
                                        startActivity(new Intent(Login.this, SectionCatalogue.class));
                                        finish();
                                    } else {
                                        //username.setError("Username or password is wrong");
                                        companyid.setError(response.optString("Errors"));
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    GenericData.ShowDialog(Login.this, "Loading...", false);
                                    GenericData.apiError(Login.this, volleyError);
                                }
                            });
                            // ApplicationClass.makerequest(jsonObjectRequest);
                            queue.add(jsonObjectRequest);
                        } else companyid.setError("Field cannot be empty");
                    } else password.setError("Field cannot be empty");
                } else username.setError("Field cannot be empty");

            }
        });

    }


    public void loginslide() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        linearLayout.startAnimation(animation1);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit "+sharedPreferences.getString(GenericData.Sp_StoreCode,"")+"?")
                .setMessage("Are you sure you want to exit "+sharedPreferences.getString(GenericData.Sp_StoreCode,"")+"?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                        sharedPreferences.edit().clear().commit();
                        username.setText("");
                        password.setText("");
                        companyid.setText("");
                        Login.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }
}
