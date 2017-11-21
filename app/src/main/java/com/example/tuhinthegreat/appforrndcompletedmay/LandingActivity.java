package com.example.tuhinthegreat.appforrndcompletedmay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



    }


//    Method to call button action from layout and define only the action

   public void btnLocationClick(View view)
    {
//        Toast.makeText(this,"btnLocationClick",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void btnDBActionClick(View view)
    {
      //  Toast.makeText(this,"btnDBActionClick",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }


    public void btnWebserviceClick(View view)
    {
        Toast.makeText(this,"WebService",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MobileInfoandWebservice.class);
        startActivity(intent);
    }

    public void btnBackgroundServiceClick(View view)
    {

        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR);


        Toast.makeText(this,"bk srvc",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MyService.class);
        startService(intent);

    }

    public void btnMaptoJsonclick(View view)
    {
        Toast.makeText(this,"Map with Json",Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this, PathGoogleMapActivity.class);
        startActivity(intent);
    }



}
