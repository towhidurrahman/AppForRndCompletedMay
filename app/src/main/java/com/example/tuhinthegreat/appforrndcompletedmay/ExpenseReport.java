package com.example.tuhinthegreat.appforrndcompletedmay;

import android.content.Context;
import android.database.Cursor;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.tuhinthegreat.appforrndcompletedmay.DatabaseHelper.dbHelper;

public class ExpenseReport extends AppCompatActivity {
    private List<ReportHeaderList> HeaderList = new ArrayList<>();
    private ListAdapter mAdapter;
    private RecyclerView recyclerView;
    ReportHeaderList header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        Context contxt = this;
        if (DatabaseHelper.getInstance() == null) {
            dbHelper = new DatabaseHelper(contxt);
            dbHelper.openDataBase();
        } else {
            dbHelper = DatabaseHelper.getInstance();
        }
        FetchData();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public void FetchData()
    {
        DataView();
        Cursor c;
        c = dbHelper.getQueryCursor("SELECT * FROM expensetracker ;");
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {


                    String Date = c.getString(c.getColumnIndex("Date"));
                    String particular = c.getString(c.getColumnIndex("Particular"));
                    String amount = c.getString(c.getColumnIndex("Amount"));
                    String type = c.getString(c.getColumnIndex("Type"));

                    // Toast.makeText(Personal_Tracking.this, txt1+"::"+txt2, Toast.LENGTH_SHORT).show();
                    prepareData(Date,particular,amount,type);
                } while (c.moveToNext());
            }
        }


        //  Toast.makeText(Report.this, "Fetch Done", Toast.LENGTH_SHORT).show();
    }

    public void DataView()
    {
        HeaderList.clear();
        mAdapter = new ListAdapter(HeaderList);
        recyclerView.removeAllViews();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.removeAllViews();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        Toast.makeText(this, "Data View Done", Toast.LENGTH_SHORT).show();
    }
    private void prepareData(String Date,String particular,String amount,String type) {
        header = new ReportHeaderList(Date,particular,amount,type );
        HeaderList.add(header);
        mAdapter.notifyDataSetChanged();
    }


}
