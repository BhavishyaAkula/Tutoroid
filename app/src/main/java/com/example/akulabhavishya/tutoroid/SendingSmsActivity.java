package com.example.akulabhavishya.tutoroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SendingSmsActivity extends AppCompatActivity {
    EditText smsnumber,smstext;
    Button sends, backs;
    String snumb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_sms);

        smsnumber = findViewById(R.id.smsnum);
        smstext = findViewById(R.id.smstxt);
        sends = findViewById(R.id.sendbtn);
        backs = findViewById(R.id.backbtn);

        snumb = getIntent().getStringExtra("Number");
        smsnumber.setText(snumb);

        sends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smstext = findViewById(R.id.smstxt);
                String nu = smsnumber.getText().toString();
                String txt = smstext.getText().toString();

                if(txt.equals("")){
                    smstext.setError("Enter your message");
                }else {
                //Getting intent and PendingIntent instance
                Intent intent=new Intent(getApplicationContext(),SendingSmsActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(nu, null, txt, pi,null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
            }}
        });
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

