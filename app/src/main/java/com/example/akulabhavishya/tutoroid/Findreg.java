package com.example.akulabhavishya.tutoroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.akulabhavishya.tutoroid.Network.API;
import com.example.akulabhavishya.tutoroid.Network.VolleySingleton;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Findreg extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6, e7;
    Button b1;
    SharedPreferences sharedPreferences;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findreg);
       sharedPreferences = getSharedPreferences("pref",Context.MODE_PRIVATE);
        e1 = findViewById(R.id.fname);
        e2 = findViewById(R.id.uname);
        e3 = findViewById(R.id.pass);
        e4 = findViewById(R.id.address);
        e5 = findViewById(R.id.location);
        e6 = findViewById(R.id.mobile);
        e7 = findViewById(R.id.email);
        b1 = findViewById(R.id.but);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.mobile,
                "^[6-9]{1}[0-9]{9}$", R.string.mobiletxt);
        awesomeValidation.addValidation(this, R.id.email,
                Patterns.EMAIL_ADDRESS, R.string.emailtxt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fullname = e1.getText().toString();
                String UserName = e2.getText().toString();
                String Password = e3.getText().toString();
                String Address = e4.getText().toString();
                String Location = e5.getText().toString();
                String Mobile = e6.getText().toString();
                String Email = e7.getText().toString();

//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("StuName",Fullname);
//                editor.commit();

                if (Fullname.equals("")) {
                    e1.setError("enter fullname");
                    e1.requestFocus();
                } else if (UserName.equals("")) {
                    e2.setError("enter username");
                    e2.requestFocus();
                } else if (Password.equals("")) {
                    e3.setError("enter password");
                    e3.requestFocus();
                } else if (Address.equals("")) {
                    e4.setError("enter address");
                    e4.requestFocus();
                } else if (Location.equals("")) {
                    e5.setError("enter location");
                    e5.requestFocus();
                } else if (Mobile.equals("")) {
                    e6.setError("enter mobile number");
                    e6.requestFocus();
                } else if (Email.equals("")) {
                    e7.setError("enter email");
                    e7.requestFocus();
                } else {
                    supload();
                    overridePendingTransition(R.anim.left_out, R.anim.right_in);
                }
            }

        });
    }

    private void supload() {
        //calling url
        String serverurl = API.feedbackurl;
        //sending request to url for response Or Request Constructer with 4 parameters

        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        e1.setText(null);
                        e2.setText(null);
                        e3.setText(null);
                        e4.setText(null);
                        e5.setText(null);
                        e6.setText(null);
                        e7.setText(null);
                        Intent i = new Intent(getApplicationContext(), Findlog.class);
                        startActivity(i);
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
                String sfname = e1.getText().toString();
                String suname = e2.getText().toString();
                String spassword = e3.getText().toString();
                String saddress = e4.getText().toString();
                String slocation = e5.getText().toString();
                String smobile = e6.getText().toString();
                String semail = e7.getText().toString();


                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("fullname", sfname);
                data.put("userid", suname);
                data.put("password", spassword);
                data.put("address", saddress);
                data.put("location", slocation);
                data.put("mobile", smobile);
                data.put("email", semail);


                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
}

