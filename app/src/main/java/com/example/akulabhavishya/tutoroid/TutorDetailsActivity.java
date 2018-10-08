package com.example.akulabhavishya.tutoroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class TutorDetailsActivity extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    String nt1,nt2,nt3,nt4,nt5,nt6,nt7,nt8,nt9;
    ImageView calls,msg,mail,loca;
    ImageView addreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_details);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        t1=findViewById(R.id.fullname);
        t2=findViewById(R.id.mob);
        t3=findViewById(R.id.time);
        t4=findViewById(R.id.anum);
        t5=findViewById(R.id.location);
        t6=findViewById(R.id.address);
        t7=findViewById(R.id.email);
        t8=findViewById(R.id.cla);
        t9=findViewById(R.id.sub);
        addreq=findViewById(R.id.addrequest);
        calls=findViewById(R.id.call);
        msg = findViewById(R.id.m);
        mail = findViewById(R.id.ema);
        loca = findViewById(R.id.map);


        nt1=getIntent().getStringExtra("NAME");
        nt2=getIntent().getStringExtra("MOBILE");
        nt3=getIntent().getStringExtra("TIME");
        nt4=getIntent().getStringExtra("ADHAR");
        nt5=getIntent().getStringExtra("CITY");
        nt6=getIntent().getStringExtra("ADDRESS");
        nt7=getIntent().getStringExtra("EMAIL");
        nt8=getIntent().getStringExtra("CLASS");
        nt9=getIntent().getStringExtra("SUBJECT");

        t1.setText(nt1);
        t2.setText(nt2);
        t3.setText(nt3);
        t4.setText(nt4);
        t5.setText(nt5);
        t6.setText(nt6);
        t7.setText(nt7);
        t8.setText(nt8);
        t9.setText(nt9);

        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+nt2));
                if (ActivityCompat.checkSelfPermission(TutorDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       String numb = t2.getText().toString();
                       Intent i =new Intent(getApplicationContext(),SendingSmsActivity.class);
                       i.putExtra("Number",numb);
                       startActivity(i);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid = t7.getText().toString();
                Intent i =new Intent(getApplicationContext(),SendingMailActivity.class);
                i.putExtra("Mail",mailid);
                startActivity(i);
            }
        });
        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = t6.getText().toString();
                Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                i.putExtra("MapLocation",address);
                startActivity(i);

            }
        });

addreq.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),AddRequestActivity.class);
        startActivity(intent);
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


