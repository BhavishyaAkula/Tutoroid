package com.example.akulabhavishya.tutoroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.akulabhavishya.tutoroid.Network.API;
import com.example.akulabhavishya.tutoroid.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    EditText usernames, passwords, fullnames, mobiles, eemails, classtime, aadharnum, place, adress, teach, expe, subinterest;
    SharedPreferences sharedPreferences;
    String userids;
    Button subm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        usernames = findViewById(R.id.uname);
        passwords = findViewById(R.id.pass);
        fullnames = findViewById(R.id.fname);
        mobiles = findViewById(R.id.mobile);
        eemails = findViewById(R.id.email);
        classtime = findViewById(R.id.time);
        aadharnum = findViewById(R.id.anum);
        place = findViewById(R.id.location);
        adress = findViewById(R.id.address);
        expe = findViewById(R.id.experience);
        subinterest = findViewById(R.id.sub);
        teach = findViewById(R.id.cl);
        subm = findViewById(R.id.submit);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        userids = sharedPreferences.getString("from", null);
        Toast.makeText(getApplicationContext(),userids,Toast.LENGTH_SHORT).show();
        getdetails();

        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cuserid = usernames.getText().toString();
                String cpass = passwords.getText().toString();
                String cfulnam = fullnames.getText().toString();
                String cmob = mobiles.getText().toString();
                String cemai = eemails.getText().toString();
                String ctime = classtime.getText().toString();
                String cadhar = aadharnum.getText().toString();
                String ccity = place.getText().toString();
                String cadres = adress.getText().toString();
                String cexp = expe.getText().toString();
                String csubj = subinterest.getText().toString();
                String cteaching = teach.getText().toString();
                if (cuserid.equals("") && cpass.equals("") && cfulnam.equals("") && cmob.equals("") && cemai.equals("") && ctime.equals("") &&
                        cadhar.equals("") && ccity.equals("") && cadres.equals("") && cexp.equals("") && csubj.equals("") && cteaching.equals("")) {
                    usernames.setError("Not Empty");
                    passwords.setError("Not Empty");
                    fullnames.setError("Not Empty");
                    mobiles.setError("Not Empty");
                    eemails.setError("Not Empty");
                    classtime.setError("Not Empty");
                    aadharnum.setError("Not Empty");
                    place.setError("Not Empty");
                    adress.setError("Not Empty");
                    expe.setError("Not Empty");
                    subinterest.setError("Not Empty");
                    teach.setError("Not Empty");

                } else {
                    update();
                }

            }
        });
    }

    private void update() {
        //calling url
        String serverurl = API.updateprofile;
        //sending request to url for response Or Request Constructer with 4 parameters
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getApplicationContext(), "Update status : "+res, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Log.e("ERROR", "Exception");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                //To Read data from Edit fields and convert to string

                String cuserid = usernames.getText().toString();
                String cpass = passwords.getText().toString();
                String cfulnam = fullnames.getText().toString();
                String cmob = mobiles.getText().toString();
                String cemai = eemails.getText().toString();
                String ctime = classtime.getText().toString();
                String cadhar = aadharnum.getText().toString();
                String ccity = place.getText().toString();
                String cadres = adress.getText().toString();
                String cexp = expe.getText().toString();
                String csubj = subinterest.getText().toString();
                String cteaching = teach.getText().toString();
                // String sradiobutton = ((RadioButton) findViewById(r1.getCheckedRadioButtonId())).getText().toString();


                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds

                data.put("userid", cuserid);
                data.put("password", cpass);
                data.put("fullname", cfulnam);
                data.put("mobile", cmob);
                data.put("email", cemai);
                data.put("timings", ctime);
                data.put("adharno", cadhar);
                data.put("city", ccity);
                data.put("address", cadres);
                data.put("experience", cexp);
                data.put("subject", csubj);
                data.put("class", cteaching);
                //data.put("gender", sradiobutton);

                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }

    public void getdetails() {
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverurl = API.gettutor;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("tutor");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        // String eid=jsonObject1.getString("id");
                        String eusername = jsonObject1.getString("userid");
                        String epassword = jsonObject1.getString("password");
                        String efullname = jsonObject1.getString("fullname");
                        String emobile = jsonObject1.getString("mobile");
                        String eemail = jsonObject1.getString("email");
                        String etimings = jsonObject1.getString("timings");
                        String eadhar = jsonObject1.getString("adharno");
                        String ecity = jsonObject1.getString("city");
                        String eaddress = jsonObject1.getString("address");
                        String eexper = jsonObject1.getString("experience");
                        String eclass = jsonObject1.getString("class");
                        String esubject = jsonObject1.getString("subject");
                        usernames.setText(eusername);
                        passwords.setText(epassword);
                        fullnames.setText(efullname);
                        mobiles.setText(emobile);
                        eemails.setText(eemail);
                        classtime.setText(etimings);
                        aadharnum.setText(eadhar);
                        place.setText(ecity);
                        adress.setText(eaddress);
                        expe.setText(eexper);
                        teach.setText(eclass);
                        subinterest.setText(esubject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressBar.setVisibility(View.GONE);
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("userid", userids);
                return data;
            }
        };
        // progressBar.setVisibility(View.VISIBLE);
        queue.add(stringRequest);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.logout:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            case R.id.aboutus:
                i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.share:
                ApplicationInfo app = getApplicationContext().getApplicationInfo();
                String filePath = app.sourceDir;
                Intent shareintent = new Intent(Intent.ACTION_SEND);
// MIME of .apk is "application/vnd.android.package-archive".
// but Bluetooth does not accept this. Let's use "*/*" instead.
                shareintent.setType("*/*");
// Append file and send Intent
                shareintent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                startActivity(Intent.createChooser(shareintent, "Share app via"));

                return true;
            case R.id.contact:
                i = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
