package com.example.akulabhavishya.tutoroid;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.akulabhavishya.tutoroid.Network.API;
import com.example.akulabhavishya.tutoroid.Network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Findlog extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    TextView t1;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findlog);
        sharedPreferences = getSharedPreferences("pref",Context.MODE_PRIVATE);
        b1=findViewById(R.id.login);
        e1=findViewById(R.id.name);
        e2=findViewById(R.id.pass);
        t1=findViewById(R.id.reg);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),Findreg.class);
                startActivity(in);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=e1.getText().toString();
                String pass=e2.getText().toString();
                if(uname.equals(""))
                {
                    e1.setError("enter username");
                    e1.requestFocus();
                }
                else{
                    if(pass.equals(""))
                    {
                        e2.setError("enter password");
                        e2.requestFocus();
                    }
                    else
                    {
                        String sname = e1.getText().toString();
                        overridePendingTransition(R.anim.left_out, R.anim.right_in);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("FROM",sname);
                        editor.commit();
                        supload();
                    }
                }
            }


        });
    }


    private void supload() {

        String serverurl = API.feedbackloginurl ;

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
                        e1.setText(null);
                        e2.setText(null);
                        Intent intent=new Intent(getApplicationContext(),Tutor_RetreiveActivity.class);
                        startActivity(intent);


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
                String sname = e1.getText().toString();
                String spassword = e2.getText().toString();

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("userid", sname);
                data.put("password", spassword);

                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.menu_new_content_twitter) {
//            // do something
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
//        }
//        if (id == R.id.contact) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }
//        if (R.id.menu_new_content_facebook == id) {
//            Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i1);
//        }
//        return super.onOptionsItemSelected(item);
//    }

}