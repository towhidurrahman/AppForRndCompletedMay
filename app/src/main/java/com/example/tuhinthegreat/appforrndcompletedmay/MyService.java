package com.example.tuhinthegreat.appforrndcompletedmay;

/**
 * Created by TUHIN THE GREAT on 5/31/2017.
 */

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MyService extends Service {
    Double latitude=0.0;
    Double longitude=0.0;

    final Handler handler= new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       // Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        doToast();

        /////



        return START_STICKY;
    }

    public void doToast()
    {

        final int timer;

        timer = 60000;
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                /////




                GPSTracker gps = new GPSTracker(MyService.this);
                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Geocoder geocoder;
                    try {
                        List<Address> addresses;
                        geocoder = new Geocoder(MyService.this, Locale.getDefault());

                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1)+ ", " + addresses.get(0).getAddressLine(2);
                        //add.setText(""+address);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        // Log.w("My loction address", "Canont get Address!");

                    }
                    //lat.setText(""+latitude);
                    //lon.setText(""+longitude);

                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                // doWebservice();



               // Toast.makeText(MyService.this,""+latitude+"::"+""+longitude,Toast.LENGTH_LONG).show();

                test();



                //////////////////////////////////

                handler.postDelayed(this, timer);
            }

        }, timer);

    }

    public void test()
    {

        ///////////////////////////////////////////////
        SoapObject request = new SoapObject("http://webservice.trenterprisebiz.com/", "location");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;

        PropertyInfo prop = new PropertyInfo();
        prop.setName("lat");
        prop.setValue(""+latitude);
        prop.setType(String.class);

        request.addProperty(prop);

        PropertyInfo prop2 = new PropertyInfo();
        prop2.setName("longi");
        prop2.setValue(""+longitude);
        prop2.setType(String.class);

        request.addProperty(prop2);

//        prop = new PropertyInfo();
//        prop.setName("fileName");
//        prop.setValue(fileName);
//        prop.setType(String.class);
//        request.addProperty(prop);

        HttpTransportSE androidHttpTransport = new HttpTransportSE("http://webservice.trenterprisebiz.com/main.asmx");

        try {
            androidHttpTransport.call("http://webservice.trenterprisebiz.com/location", envelope);
            envelope.getResponse();
        } catch (final Exception e) {
            //error = true;
            Toast.makeText(MyService.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        //////////////////////////////////////



     //   Toast.makeText(MyService.this,"test working",Toast.LENGTH_LONG).show();
    }



    public void doWebservice()
    {
        int a=1000;
        final Handler handler1= new Handler();
        final boolean b = handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                String NAMESPACE = "http://webservice.trenterprisebiz.com/";
                String URL = "http://webservice.trenterprisebiz.com/main.asmx";
                String METHOD_NAME = "mobiletracker";
                String SOAP_ACTION = "http://webservice.trenterprisebiz.com/mobiletracker";
                final SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("name", "120");
                final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    //SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                    //response = result.toString();

                } catch (IOException e) {
                    Log.d("IO", e.toString());
                    //response = "1";
                } catch (XmlPullParserException e) {
                    //response = "2";
                }


            }

        }, a);
    }


    @Override
    public void onDestroy() {
        doToast();

        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
