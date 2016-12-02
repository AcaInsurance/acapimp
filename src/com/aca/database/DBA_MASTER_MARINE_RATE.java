package com.aca.database;

import com.aca.pimp.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBA_MASTER_MARINE_RATE {

	public static final String RATE_BAWAH = "RATE_BAWAH";
    public static final String RATE_TENGAH = "RATE_TENGAH";
    public static final String RATE_ATAS = "RATE_ATAS";
    
    private static final String TAG = "DBA_MASTER_MARINE_RATE";
    
    private static final String DATABASE_NAME = "AMM_VERSION_BIG";
    private static final String DATABASE_TABLE = "MASTER_RATE_MARINE";

     static final String DATABASE_CREATE =
        "CREATE TABLE MASTER_RATE_MARINE (RATE_BAWAH NUMERIC, RATE_TENGAH NUMERIC, RATE_ATAS NUMERIC);";
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBA_MASTER_MARINE_RATE(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, Utility.getDatabaseVersionBig());
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	 
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
           
        }
    }    

    //---opens the database---
    public DBA_MASTER_MARINE_RATE open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insert(double rateBawah, double rateTengah, double rateAtas) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(RATE_BAWAH, rateBawah);
        initialValues.put(RATE_TENGAH, rateTengah);
        initialValues.put(RATE_ATAS, rateAtas);
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteAll() 
    {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }
     
    
    public Cursor getAll() 
    {
    	return db.query(DATABASE_TABLE,  new String[] {
    			RATE_BAWAH, 		//0
    			RATE_TENGAH,		//1 
    			RATE_ATAS,			//2
	    		}, null, null, null, null, null);    			
    } 
}
