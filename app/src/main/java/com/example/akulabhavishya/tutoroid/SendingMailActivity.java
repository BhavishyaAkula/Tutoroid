package com.example.akulabhavishya.tutoroid;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class SendingMailActivity extends AppCompatActivity {
    EditText tomail,submail,msgmail;
    Button sendmail,backbtn;
    String semail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_mail);
        tomail = findViewById(R.id.mailto);
        submail = findViewById(R.id.mailsubject);
        msgmail = findViewById(R.id.mailmsg);
        sendmail=findViewById(R.id.mailbtn);
        backbtn=findViewById(R.id.mailbackbtn);


        semail = getIntent().getStringExtra("Mail");
        tomail.setText(semail);
        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submail = findViewById(R.id.mailsubject);
                msgmail = findViewById(R.id.mailmsg);
                String toadd = tomail.getText().toString();
                String subj = submail.getText().toString();
                String messag = msgmail.getText().toString();
               if(subj.equals("")&&messag.equals("")){
                   Toast.makeText(SendingMailActivity.this, "Empty submission not allowed, fill the fields please", Toast.LENGTH_SHORT).show();
               }else{
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ toadd});
                email.putExtra(Intent.EXTRA_SUBJECT, subj);
                email.putExtra(Intent.EXTRA_TEXT, messag);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

                submail.setText("");
                msgmail.setText("");

            }}
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
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
