package com.example.tuhinthegreat.appforrndcompletedmay;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {


    public GoogleMap mMap;

    double latitude=0.0;
    double longitude =0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }


    public void btnGetLocationClick(View view)
    {

        // Creating object of GPSTrakcer.class

        GPSTracker gps = new GPSTracker(this);

        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Geocoder geocoder;
            try {
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1)+ ", " + addresses.get(0).getAddressLine(2);
                //add.setText(""+address);

//                Toast.makeText(this,""+address,Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
                // Log.w("My loction address", "Canont get Address!");

            }
            final TextView txtlat = (TextView)findViewById(R.id.txtLatitude);
            final TextView txtlong = (TextView)findViewById(R.id.txtLongitude);
            txtlat.setText(""+latitude);
            txtlong.setText(""+longitude);

//            Toast.makeText(this,""+latitude,Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,""+longitude,Toast.LENGTH_SHORT).show();

            // \n is for new line
            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }




    }

    public void btnClearLocationClick(View view)
    {
//        final TextView txtlat = (TextView)findViewById(R.id.txtLatitude);
//        final TextView txtlong = (TextView)findViewById(R.id.txtLongitude);
//        txtlat.setText("0.0");
//        txtlong.setText("0.0");

    }


    public void btnPlotLocationClick(View view)
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(23.7444,90.3882);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
