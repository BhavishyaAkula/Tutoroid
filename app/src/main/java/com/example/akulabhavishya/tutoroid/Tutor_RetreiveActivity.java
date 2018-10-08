package com.example.akulabhavishya.tutoroid;

import android.annotation.SuppressLint;
import android.app.SearchManager;
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
import android.widget.ImageView;
import android.widget.SearchView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tutor_RetreiveActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    int id;
    RecyclerView recyclerView;
    RecyAdapter madapter;
    List<Beans> data = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SearchView searchViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor__retreive);
        recyclerView = findViewById(R.id.rv);
        getfeedback();
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        searchViews = findViewById(R.id.searchView);
        searchViews.setOnQueryTextListener(this);
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
               onBackPressed();
                return true;
            case R.id.aboutus:
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
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
    public void getfeedback() {
        //request for getting data
        data.clear();
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverurl = API.viewaddtutorurl;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("tutors");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String etutorid = jsonObject1.getString("userid");
                        String ename = jsonObject1.getString("fullname");
                        String esubject = jsonObject1.getString("subject");
                        String emobile = jsonObject1.getString("mobile");
                        String etime = jsonObject1.getString("timings");
                        String eadharnumber = jsonObject1.getString("adharno");
                        String ecity = jsonObject1.getString("city");
                        String eclass = jsonObject1.getString("class");
                        String eaddress = jsonObject1.getString("address");
                        String eemail = jsonObject1.getString("email");

                        Beans jobsBean1 = new Beans();
                        jobsBean1.setId(etutorid);
                        jobsBean1.setMobile(emobile);
                        jobsBean1.setFullname(ename);
                        jobsBean1.setSubject(esubject);
                        jobsBean1.setTiming(etime);
                        jobsBean1.setAdharnumber(eadharnumber);
                        jobsBean1.setCity(ecity);
                        jobsBean1.setClasses(eclass);
                        jobsBean1.setAddress(eaddress);
                        jobsBean1.setEmail(eemail);
                        data.add(jobsBean1);
                    }

                    madapter = new RecyAdapter(data, getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(madapter);

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
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                return null;
            }

        };
        queue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText.toLowerCase();
        if (text.length() == 0) {
            getfeedback();
        } else {
            madapter.filter(text);
        }
        return false;
    }

    public class RecyAdapter extends RecyclerView.Adapter<Tutor_RetreiveActivity.RecyAdapter.MyViewHolder> {
        List<Beans> data;
        Context context;

        RecyAdapter(List<Beans> data, Context context) {
            this.context = context;
            this.data = data;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView st1, st2, st3;
            ImageView icons;
            CardView cardViews;

            MyViewHolder(View itemView) {
                super(itemView);
                st1 = itemView.findViewById(R.id.t1);
                st2 = itemView.findViewById(R.id.t2);
                st3 = itemView.findViewById(R.id.t3);
                cardViews = itemView.findViewById(R.id.card);
                icons = itemView.findViewById(R.id.img);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.st1.setText(data.get(position).getFullname());
            holder.st2.setText(data.get(position).getMobile());
            holder.st3.setText(data.get(position).getSubject());
            holder.cardViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userid = data.get(position).getId();

                    String fname = data.get(position).getFullname();
                    String fmobile = data.get(position).getMobile();
                    String ftime = data.get(position).getTiming();
                    String fadhar = data.get(position).getAdharnumber();
                    String fcity = data.get(position).getCity();
                    String faddress = data.get(position).getAddress();
                    String femail = data.get(position).getEmail();
                    String fclass = data.get(position).getClasses();
                    String fsubject = data.get(position).getSubject();

                    Intent i = new Intent(getApplicationContext(), TutorDetailsActivity.class);
                    i.putExtra("NAME", fname);
                    i.putExtra("MOBILE", fmobile);
                    i.putExtra("TIME", ftime);
                    i.putExtra("ADHAR", fadhar);
                    i.putExtra("CITY", fcity);
                    i.putExtra("ADDRESS", faddress);
                    i.putExtra("EMAIL", femail);
                    i.putExtra("CLASS", fclass);
                    i.putExtra("SUBJECT", fsubject);
                    startActivity(i);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TutorName", userid);
                    editor.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void filter(String text) {
            List<Beans> filterdNames = new ArrayList<>();
            //looping through existing elements
            for (Beans s : data) {
                //if the existing elements contains the search input
                if (s.getSubject().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }
            //calling a method of the adapter class and passing the filtered list
            madapter.filterList(filterdNames);
        }

        public void filterList(List<Beans> data) {
            this.data = data;
            notifyDataSetChanged();
        }

    }
}
