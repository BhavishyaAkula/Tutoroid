package com.example.akulabhavishya.tutoroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Viewrequest extends AppCompatActivity {
RecyclerView recyclerView;
RequestAdapter requestAdapter;
List<Jobsbeans> data = new ArrayList<>();
SharedPreferences sharedPreferences;
String tutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);
        sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
        tutor = sharedPreferences.getString("from",null);
        recyclerView = findViewById(R.id.recyviewreq);
        //List<Jobsbeans> data = fill_data();
        getfeedback();
    }

//    public List<Jobsbeans> fill_data(){
//        List<Jobsbeans> data = new ArrayList<>();
//        data.add(new Jobsbeans("ravi","dsafadgdg"));
//        data.add(new Jobsbeans("hari","adgdgdg"));
//        data.add(new Jobsbeans("raghu","sfsdfdaf"));
//
//        return data;
//    }

    public void getfeedback() {
        //request for getting data
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverurl = API.viewrequest;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("requests");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String ecusname = jsonObject1.getString("cname");
                        String etutname = jsonObject1.getString("tname");
                        String edesc = jsonObject1.getString("request");

                        Jobsbeans jobsBean1 = new Jobsbeans();
                        jobsBean1.setFrom(ecusname);
                        jobsBean1.setId(etutname);
                        jobsBean1.setDescription(edesc);
                        data.add(jobsBean1);
                    }

                    requestAdapter = new RequestAdapter(data,getApplicationContext());
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(requestAdapter);

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
                data.put("tname",tutor);
                return data;
            }

        };
        queue.add(stringRequest);
    }


    private class RequestAdapter extends RecyclerView.Adapter<Viewrequest.RequestAdapter.MyViewHolder>{
        List<Jobsbeans> data;
        Context applicationContext;
        public RequestAdapter(List<Jobsbeans> data, Context applicationContext) {
            this.data = data;
            this.applicationContext = applicationContext;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_request_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
         holder.sfro.setText(data.get(position).getFrom());
         holder.sdesc.setText(data.get(position).getDescription());
         holder.scards.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String rfro = data.get(position).getFrom();
                 String rdesc = data.get(position).getDescription();
                 String to =  data.get(position).getId();
                 Intent i =new Intent(getApplicationContext(),TutorResponseActivity.class);
                 i.putExtra("to",rfro);
                 i.putExtra("Descript",rdesc);
                 i.putExtra("from",to);

                 startActivity(i);
                 overridePendingTransition(R.anim.right_in,R.anim.left_out);
             }
         });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView sfro,sdesc;
            CardView scards;
            public MyViewHolder(View itemView) {
                super(itemView);
                sfro = itemView.findViewById(R.id.froonam);
                sdesc = itemView.findViewById(R.id.froodesc);
                scards = itemView.findViewById(R.id.gv);
            }
        }
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
