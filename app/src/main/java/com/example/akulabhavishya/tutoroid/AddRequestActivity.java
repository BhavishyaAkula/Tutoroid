package com.example.akulabhavishya.tutoroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  AddRequestActivity extends AppCompatActivity {
TextView from,to;
EditText query;
SharedPreferences sharedPreferences;
List<Jobsbeans> data = new ArrayList<>();
String tuname,stname;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        from = findViewById(R.id.userid);
        to = findViewById(R.id.tutorid);
        query = findViewById(R.id.query);
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        tuname = sharedPreferences.getString("TutorName","");
        stname = sharedPreferences.getString("FROM","");
        to.setText(tuname);
        from.setText(stname);
        btn = findViewById(R.id.addbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdesc = query.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("request",sdesc);
                editor.commit();
                String querys = query.getText().toString();
                if(querys.equals("")){
                    Toast.makeText(AddRequestActivity.this, "Enter your Request", Toast.LENGTH_SHORT).show();
                }else{
                supload();
                //Toast.makeText(getApplicationContext(),"Your Query Submitted",Toast.LENGTH_SHORT).show();
            }}
        });
    }

    private void supload() {


        String serverurl = API.addrequest ;


        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");
                    Toast.makeText(getApplicationContext(), "msg"+res, Toast.LENGTH_SHORT).show();

                    if (res.equals("success"))
                    {
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        query.setText(null);
                       Toast.makeText(getApplicationContext(),"Your Query Submitted, Thank you!..",Toast.LENGTH_SHORT).show();


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
                String sfrom = from.getText().toString();
                String sto = to.getText().toString();
                String sdesc = query.getText().toString();


                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("cname", sfrom);
                data.put("tname", sto);
                data.put("request",sdesc);
                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
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
