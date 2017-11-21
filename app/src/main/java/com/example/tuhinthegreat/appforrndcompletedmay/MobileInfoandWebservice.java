package com.example.tuhinthegreat.appforrndcompletedmay;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MobileInfoandWebservice extends AppCompatActivity {
    String imei;
    String Manufacturername;
    String Model;
    String mobileid;
    String res;


    EditText manufac;
    EditText model;
    EditText imi;
    EditText gmailid;
    EditText Mobileid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_infoand_webservice);
    }

    public void btnRefreshClick(View view)
    {

        String TelephonyService = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(TelephonyService);

        imei = mTelephonyMgr.getDeviceId();
        mobileid=mTelephonyMgr.getLine1Number();

        Manufacturername = android.os.Build.MANUFACTURER;

        Model = android.os.Build.MODEL;

        Toast.makeText(this,imei,Toast.LENGTH_LONG).show();

        manufac=(EditText)findViewById(R.id.manufacturer);
        model=(EditText)findViewById(R.id.model);
        imi = (EditText)findViewById(R.id.imei);


        manufac.setText(""+Manufacturername);
        model.setText(""+Model);
        imi.setText(""+imei);


                AccountManager accManager = AccountManager.get(getApplicationContext());

                Account acc[] = accManager.getAccountsByType("com.google");




        gmailid = (EditText)findViewById(R.id.gmailid);

        gmailid.setText(""+acc[0].name);

        Mobileid = (EditText)findViewById(R.id.mobileid);

        Mobileid.setText(""+mobileid);

        Toast.makeText(this,""+mobileid,Toast.LENGTH_SHORT).show();









    }

    public void btnInsertClick(View view)
    {

        Thread nt = new Thread() {
            @Override
            public void run() {
                String NAMESPACE = "http://webservice.trenterprisebiz.com/";
                // String URL = "http://192.168.0.102/SumDemoWebservice/WebServiceLogin.asmx";
                // String URL = "http://10.168.12.173/SumDemoWebservice/WebServiceLogin.asmx";
                String URL = "http://webservice.trenterprisebiz.com/main.asmx";
                String METHOD_NAME="mobiletracker";
                String SOAP_ACTION="http://webservice.trenterprisebiz.com/mobiletracker";

                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("name",Manufacturername);
                request.addProperty("password","erp");
                request.addProperty("deviceid",mobileid);
              //  request.addProperty("latitude",Model);
              //  request.addProperty("longitude",imei);
               // request.addProperty("devicestatus",gmailid);

                SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);
                try {
                    transporte.call(SOAP_ACTION,envelope);
                    SoapPrimitive resulttodo_xml = (SoapPrimitive) envelope.getResponse();
                    res = resulttodo_xml.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MobileInfoandWebservice.this,res,Toast.LENGTH_LONG).show();

                    }
                });

            }
        };
        nt.start();


        

    }
}
