package com.example.tuhinthegreat.appforrndcompletedmay;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.tuhinthegreat.appforrndcompletedmay.DatabaseHelper.dbHelper;

public class DatabaseActivity extends AppCompatActivity {

    EditText dtpicker;

    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;

    Context contxt;


    Spinner spinnerparticular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);


        dtpicker = (EditText)findViewById(R.id.dtpicker) ;



        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_WEEK);

        showDialogOnTextClick();

        spinnerparticular = (Spinner)findViewById(R.id.spinnerparticular);

        List<String> list = new ArrayList<String>();
        list.add("Select..");
        list.add("Transport");
        list.add("Food");
        list.add("Medicine");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerparticular.setAdapter(adapter);

        spinnerparticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                spinnerparticular.setSelection(adapter.getPosition(spinnerparticular.getSelectedItem().toString()));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


    }

    public void showDialogOnTextClick()
    {

        dtpicker.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        showDialog(DIALOG_ID);
                    }
                }
        );

    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return new DatePickerDialog(this,dpickerlistner,year_x,month_x,day_x);
        }
        else{
            return null;
        }

    }

    private DatePickerDialog.OnDateSetListener dpickerlistner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x = year;
            month_x = month+1;
            day_x=dayOfMonth;

            dtpicker.setText(""+year_x+"/"+""+month_x+"/"+""+day_x);

        }
    };




    public void radio1Click(View view){


    }

    public void radio2Click(View view){

    }

    public void btnSaveClick(View view){
        DBFileandTableCreation();
        DataInsertion();

    }

    public void DBFileandTableCreation()
    {
        File f = new File(Environment.getExternalStorageDirectory() + "/ExpenseTracker");
        if(!f.isDirectory()) {
            f.mkdir();
            File f1 = new File(Environment.getExternalStorageDirectory() + "/ExpenseTracker/ExpenseTracker.sqlite");
            try {
                f1.createNewFile();
                contxt = this;
                if (DatabaseHelper.getInstance() == null) {
                    dbHelper = new DatabaseHelper(contxt);
                    dbHelper.openDataBase();
                } else {
                    dbHelper = DatabaseHelper.getInstance();
                }
                dbHelper.executeDMLQuery("CREATE TABLE ExpenseTracker(" +
                        "Iden INTEGER PRIMARY KEY   AUTOINCREMENT,"+
                        "Date TEXT NOT NULL, " +
                        "Particular TEXT NOT NULL, " +
                        "Amount NUMERIC NOT NULL, " +
                        "Type INTEGER NOT NULL);");

//
            } catch (IOException e) {
                e.printStackTrace();

            }
        }else
        {
            contxt = this;
            if (DatabaseHelper.getInstance() == null) {
                dbHelper = new DatabaseHelper(contxt);
                dbHelper.openDataBase();
            } else {
                dbHelper = DatabaseHelper.getInstance();
            }
        }
//        Toast.makeText(Personal_Tracking.this, "File Creation Done", Toast.LENGTH_SHORT).show();
    }
    public void DataInsertion()
    {
        EditText amount=(EditText)findViewById(R.id.txtAmount);
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String sql = "INSERT INTO ExpenseTracker([Date],Particular,Amount,[Type]) VALUES ('"+dtpicker.getText()+"','"+""+spinnerparticular.getSelectedItem()+"','"+amount.getText()+"','1');";
        dbHelper.executeDMLQuery(sql);
       Toast.makeText(this, "Insertion done", Toast.LENGTH_SHORT).show();
    }



    public void btnShowClick(View view){
        Intent intent = new Intent(this, ExpenseReport.class);
//        intent.putExtra("fltr", TXTNAME.getText().toString());
        startActivity(intent);
    }




}
