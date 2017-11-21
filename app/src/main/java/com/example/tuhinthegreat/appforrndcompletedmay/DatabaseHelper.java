package com.example.tuhinthegreat.appforrndcompletedmay;

/**
 * Created by TUHIN THE GREAT on 5/9/2017.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper
{
//	private static String DB_PATH = "/data/data/"+CommonStaticClass.pName+"/databases/";
//
//    private static String DB_NAME = "nutritionalstatus";

    private static String DB_PATH = "/mnt/sdcard/ExpenseTracker/";
    private static String SDDB_PATH = "/mnt/sdcard/external_sd/ExpenseTracker/";
    private static String DB_NAME = "ExpenseTracker.sqlite";

    private static SQLiteDatabase myDataBase = null;

    private final Context myContext;

    public static DatabaseHelper dbHelper;

    private SQLiteStatement stmt,stmt1;

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        dbHelper = this;
        this.myContext = context;
        try {
            createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static DatabaseHelper getInstance(){
        return dbHelper;
    }

    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if(dbExist)
        {
            //do nothing - database already exist
//            Toast.makeText(DatabaseHelper.this, "File Creation Done", Toast.LENGTH_SHORT).show();
        }
        else
        {
/*
*     By calling this method and empty database will be created into the default system path
*     of your application so we are gonna be able to overwrite that database with our database.
*/
            this.getReadableDatabase().close();

            try {
                Log.d("DHUKE","NAI");
                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
    public boolean IsDataExists(String s){
        String sql = "";

        sql = s;


        String data = "";
        Cursor mCursor = null;
        try {
            mCursor = dbHelper.getQueryCursor(sql);
            if(mCursor.getCount()>0){
                if(mCursor.moveToFirst()){
                    do{
                        return true;
                    }while(mCursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e("ero", e.toString());
        }
        return false;
    }


    public Boolean IsDataExistsAndNotNull(String s){
        String sql = "";

        sql = s;

        String data = "";
        Cursor mCursor = null;
        try {
            mCursor = dbHelper.getQueryCursor(sql);
            if(mCursor.getCount()>0){
                if(mCursor.moveToFirst()){
                    do{
                        data = mCursor.getString(0);
                    }while(mCursor.moveToNext());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(data.equalsIgnoreCase("0") || data.equalsIgnoreCase("null") || data.length()<=0)
            return false;
        else
            return true;
    }

    public Integer GatValueFromSql(String s){
        String sql = "";

        sql = s;

        String data = "";
        Cursor mCursor = null;
        try {
            mCursor = dbHelper.getQueryCursor(sql);
            if(mCursor.getCount()>0){
                if(mCursor.moveToFirst()){
                    do{
                        data = mCursor.getString(0);
                    }while(mCursor.moveToNext());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(data.equalsIgnoreCase("null") || data.length()<=0)
            return 0;
        else
            return Integer.parseInt(data);
    }

    private boolean checkDataBase()
    {
        boolean t = false;
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        }catch(SQLiteException e)
        {

            //database does't exist yet.

        }finally{
            if(checkDB != null){

                t = true;
                checkDB.close();

            }
        }
        return t;
    }
    private void copyDataBase() throws IOException
    {
        InputStream databaseInput = null;
        String outFileName = DB_PATH + DB_NAME;
        OutputStream databaseOutput = new FileOutputStream(outFileName);
        Log.d("DHUKE","NAI copy te");
        byte[] buffer = new byte[1024];
        int length;

//        databaseInput = myContext.getResources().openRawResource(R.raw.HUS);
        while((length = databaseInput.read(buffer)) > 0)
        {
            databaseOutput.write(buffer, 0, length);
            databaseOutput.flush();
        }
        databaseInput.close();

        databaseOutput.flush();
        databaseOutput.close();
    }

    public void copyDataBaseToSdcard() throws IOException{

        final Calendar c = Calendar.getInstance();
        int dateYear = c.get(Calendar.YEAR);
        int dateMonth = c.get(Calendar.MONTH)+1;
        int dateDay = c.get(Calendar.DAY_OF_MONTH);
        long mil = System.currentTimeMillis();

        InputStream databaseInput = null;

        String inFileName = DB_PATH + DB_NAME;

        String dDirName = dateDay+"-"+dateMonth+"-"+dateYear;

        String outFileName = Environment.getExternalStorageDirectory()
                + "/"+dDirName+"/"+DB_NAME+"_("+dateDay+"-"+dateMonth+"-"+dateYear+")_"+mil;

        String outDir = Environment.getExternalStorageDirectory()
                + "/";

        String outDatedDir = Environment.getExternalStorageDirectory()
                + "/"+dDirName;

        File dir = new File(outDir);
        File dDir = new File(outDatedDir);
        if(dir.exists()){
            if(dDir.exists()){
                File file = new File(outFileName);
                if(file.exists()){
                    if(file.delete()){
                        Log.e("deleting","file");
                    }
                }
            }else{
                if(dDir.mkdirs()){
                    Log.e("making",outDatedDir+"dir");
                }
            }
        }else{
            if(dir.mkdirs()){
                Log.e("making","dir");
                if(dDir.mkdirs()){
                    Log.e("making",outDatedDir+"dir");
                }
            }
        }

        OutputStream databaseOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        File f = new File(inFileName);
        if(f.exists()){
            databaseInput = new FileInputStream(f);
            while((length = databaseInput.read(buffer)) > 0)
            {
                databaseOutput.write(buffer, 0, length);
                databaseOutput.flush();
            }
            databaseInput.close();

            databaseOutput.flush();
            databaseOutput.close();
        }


        databaseInput = null;

        inFileName = DB_PATH + DB_NAME;

        outFileName = Environment.getExternalStorageDirectory()+"/external_sd"
                + "/icddrbDB/"+dDirName+"/"+DB_NAME+"_("+dateDay+"-"+dateMonth+"-"+dateYear+")_"+mil;

        outDir = Environment.getExternalStorageDirectory()+"/external_sd"
                + "/icddrbDB/";

        outDatedDir = Environment.getExternalStorageDirectory()+"/external_sd"
                + "/icddrbDB/"+dDirName;

        dir = new File(outDir);
        dDir = new File(outDatedDir);
        if(dir.exists()){
            if(dDir.exists()){
                File file = new File(outFileName);
                if(file.exists()){
                    if(file.delete()){
                        Log.e("deleting","file");
                    }
                }
            }else{
                if(dDir.mkdirs()){
                    Log.e("making",outDatedDir+"dir");
                }
            }
        }else{
            if(dir.mkdirs()){
                Log.e("making","dir");
                if(dDir.mkdirs()){
                    Log.e("making",outDatedDir+"dir");
                }
            }
        }
        databaseOutput = new FileOutputStream(outFileName);

        buffer = new byte[1024];
        f = new File(inFileName);
        if(f.exists()){
            databaseInput = new FileInputStream(f);
            while((length = databaseInput.read(buffer)) > 0)
            {
                databaseOutput.write(buffer, 0, length);
                databaseOutput.flush();
            }
            databaseInput.close();

            databaseOutput.flush();
            databaseOutput.close();
        }
    }

    public void openDataBase() throws SQLException{

        //Open the database
        if(myDataBase==null){
            String myPath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            //myDataBase=dbHelper.getWritableDatabase(); //Blocked by zaman
        }
    }
    @Override
    public synchronized void close() {
        if(myDataBase != null){
            myDataBase.close();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("update","began");
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM tblHousehold");
//            List<String> columns = GetColumns(db, DATABASE_TABLE);
//            db.execSQL("ALTER table " + DATABASE_TABLE + " RENAME TO 'temp_" + DATABASE_TABLE + "'");
//            db.execSQL("create table " + DATABASE_UPGRADE);
//            columns.retainAll(GetColumns(db, DATABASE_TABLE));
//            String cols = join(columns, ",");
//            db.execSQL(String.format( "INSERT INTO %s (%s) SELECT %s from temp_%s", DATABASE_TABLE, cols, cols, DATABASE_TABLE));
//            db.execSQL("DROP table 'temp_" + DATABASE_TABLE + "'");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            Log.e("update","end");
        }
    }

    public Cursor getQueryCursor(String sql)
    {
        Log.e("sql",sql);
        Cursor mCursor =myDataBase.rawQuery(sql, null);
        return mCursor;
    }

    public boolean executeDMLQuery(String sql)
    {
        //SQLiteDatabase SDcardDB = null;
        Log.e("sql",sql);
        try{
            myDataBase.execSQL(sql);
            //Addition
//			String myPath = SDDB_PATH + DB_NAME;
//	   		SDcardDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
//	   		SDcardDB.execSQL(sql);
            //end of Addition
        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }




    public void populateWithCompileStatementOneTransaction () {
        try {
            myDataBase.beginTransaction();
            SQLiteStatement stmt = myDataBase.compileStatement("INSERT INTO tableName VALUES(?,?)");
//            int i = 0;
//            for (String name : sCheeseNames) {
//                String origin = sCheeseOrigins[i++];
//                stmt.clearBindings();
//                stmt.bindString(1, name); // replace first question mark with name
//                stmt. bindString(2, origin); // replace second question mark with origin
//                stmt.executeInsert();
//            }
            myDataBase.setTransactionSuccessful(); // remove that call and none of the changes will be committed!
        } catch (Exception e) {
            // handle exception here
        } finally {
            myDataBase.endTransaction(); // this must be in the finally block
        }
    }
}

