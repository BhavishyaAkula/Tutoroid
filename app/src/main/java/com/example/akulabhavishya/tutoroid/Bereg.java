package com.example.akulabhavishya.tutoroid;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.example.akulabhavishya.tutoroid.R;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Bereg extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12;
    Button b1;
    RadioButton rb1, rb2;
    RadioGroup r1;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bereg);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        e1 = findViewById(R.id.uname);
        e2 = findViewById(R.id.pass);
        e3 = findViewById(R.id.fname);
        e4 = findViewById(R.id.mobile);
        e5 = findViewById(R.id.email);
        e6 = findViewById(R.id.time);
        e7 = findViewById(R.id.anum);
        e8 = findViewById(R.id.location);
        e9 = findViewById(R.id.address);
        e10 = findViewById(R.id.experience);
        e11 = findViewById(R.id.sub);
        e12 = findViewById(R.id.cl);
        r1 = findViewById(R.id.radiogroup);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        b1 = findViewById(R.id.button);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.mobile,
                "^[6-9]{1}[0-9]{9}$", R.string.mobiletxt);
        awesomeValidation.addValidation(this, R.id.email,
                Patterns.EMAIL_ADDRESS, R.string.emailtxt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = e1.getText().toString();
                String Password = e2.getText().toString();
                String Fullname = e3.getText().toString();
                String Mobile = e4.getText().toString();
                String Email = e5.getText().toString();
                String Timing = e6.getText().toString();
                String Adharnumber = e7.getText().toString();
                String Location = e8.getText().toString();
                String Address = e9.getText().toString();
                String Expereince = e10.getText().toString();
                String Subjects = e11.getText().toString();
                String Class = e12.getText().toString();
                int selectedId = r1.getCheckedRadioButtonId();
                rb1 = findViewById(selectedId);
                rb2 = findViewById(selectedId);
                if (UserName.equals("")&&Password.equals("")&&Fullname.equals("")&&Mobile.equals("")&&Email.equals("")
                        && Timing.equals("")&&Adharnumber.equals("")&&Location.equals("")&&Address.equals("")&&Expereince.equals("")
                        &&Subjects.equals("")&&Class.equals("")) {
                    e1.setError("enter username");
                    e2.setError("enter password");
                    e3.setError("enter fullname");
                    e4.setError("enter mobile number");
                    e5.setError("enter email");
                    e6.setError("enter timing");
                    e7.setError("enter adharnumber");
                    e8.setError("enter location");
                    e9.setError("enter address");
                    e10.setError("enter expereince");
                    e11.setError("enter subject");
                    e12.setError("enter calss");
                }else {
                    supload();
                }

            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void supload() {
        //calling url
        String serverurl = API.addtutorurl;
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
                        e8.setText(null);
                        e9.setText(null);
                        e10.setText(null);
                        e11.setText(null);
                        e12.setText(null);
                        rb1.setText(null);
                        rb2.setText(null);


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

                String suname = e1.getText().toString();
                String spassword = e2.getText().toString();
                String sfname = e3.getText().toString();
                String smobile = e4.getText().toString();
                String semail = e5.getText().toString();
                String stiming = e6.getText().toString();
                String sadharnum = e7.getText().toString();
                String slocation = e8.getText().toString();
                String saddress = e9.getText().toString();
                String sexperence = e10.getText().toString();
                String ssubject = e11.getText().toString();
                String sclass = e12.getText().toString();
                String sradiobutton = ((RadioButton) findViewById(r1.getCheckedRadioButtonId())).getText().toString();
                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds

                data.put("userid", suname);
                data.put("password", spassword);
                data.put("fullname", sfname);
                data.put("mobile", smobile);
                data.put("email", semail);
                data.put("timings", stiming);
                data.put("adharno", sadharnum);
                data.put("city", slocation);
                data.put("address", saddress);
                data.put("experience", sexperence);
                data.put("subject", ssubject);
                data.put("class", sclass);
                data.put("gender", sradiobutton);

                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
    }


