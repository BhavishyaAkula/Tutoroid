package com.example.akulabhavishya.tutoroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.akulabhavishya.tutoroid.Network.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TutorResponseActivity extends AppCompatActivity {
    private static final String TAG = null;
    Button res;
    ImageView i1,i2,i3;
    TextView f,t,mobi,emai;
    LinearLayout lv;
    EditText resmsgs;
    SharedPreferences sharedPreferences;
    String toname;
    String cusname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_response);
        i1=findViewById(R.id.me);
        i2=findViewById(R.id.cal);
        i3=findViewById(R.id.emal);
        res=findViewById(R.id.resp);
        f=findViewById(R.id.frorespond);
        t=findViewById(R.id.to);
        resmsgs=findViewById(R.id.resmsg);
        mobi = findViewById(R.id.mobilenum);
        emai = findViewById(R.id.emailed);
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        cusname =getIntent().getStringExtra("to");
        t.setText(cusname);
        lv=findViewById(R.id.l_layout);
        toname = getIntent().getStringExtra("from");
        f.setText(toname);

        getCusdetails();

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resmsgs=findViewById(R.id.resmsg);
                String nu = String.format("smsto: %s", mobi.getText().toString());
                String txt = resmsgs.getText().toString();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                // Set the data for the intent as the phone number.
                smsIntent.setData(Uri.parse(nu));
                // Add the message (sms) with the key ("sms_body").
                smsIntent.putExtra("sms_body", txt);
                // If package resolves (target app installed), send intent.
                if (smsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(smsIntent);
                } else {
                    Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent");
                }

            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mobi.getText().toString();
                Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                if (ActivityCompat.checkSelfPermission(TutorResponseActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);


            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toadd = emai.getText().toString();
                String messag = resmsgs.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ toadd});
//                email.putExtra(Intent.EXTRA_SUBJECT, subj);
                email.putExtra(Intent.EXTRA_TEXT, messag);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
    }

    public void getCusdetails() {
        //request for getting data
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverurl = API.viewcustomerurl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("customer");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String ecusname = jsonObject1.getString("mobile");
                        String eemail = jsonObject1.getString("email");
                        mobi.setText(ecusname);
                        emai.setText(eemail);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
            public Map<String, String> getParams() {
                // Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                data.put("userid",cusname);
                return data;
            }

        };
        queue.add(stringRequest);
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

