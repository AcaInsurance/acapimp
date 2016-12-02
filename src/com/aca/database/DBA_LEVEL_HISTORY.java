package com.aca.database;

import java.text.NumberFormat;
import java.text.ParseException;

import com.aca.pimp.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA_LEVEL_HISTORY{

	public final String U_ID = "U_ID";
    public final String ID_LEVEL = "ID_LEVEL";    
    public final String BEGIN_DATE = "BEGIN_DATE";  
    public final String APPROVED_DATE = "APPROVED_DATE"; 
    public final String APPROVED_BY = "APPROVED_BY"; 
    public final String ALASAN_REVIEW = "ALASAN_REVIEW"; 
     
     
    private static final String DATABASE_NAME = "AMM_VERSION_2";
    private static final String DATABASE_TABLE = "MASTER_LEVEL_HISTORY";

     static final String DATABASE_CREATE =
        "CREATE TABLE MASTER_LEVEL_HISTORY (" +
        "U_ID TEXT, " +
        "ID_LEVEL TEXT, " +
        "BEGIN_DATE TEXT, " +
        "APPROVED_DATE TEXT, " +
        "APPROVED_BY TEXT, " +
        "ALASAN_REVIEW TEXT);";
     
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBA_LEVEL_HISTORY(Context ctx) 
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
    public DBA_LEVEL_HISTORY open() throws SQLException 
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
    public long insert(
    		String U_ID , 
	        String ID_LEVEL , 
	        String BEGIN_DATE , 
	        String APPROVED_DATE ,  
	        String APPROVED_BY ,   
	        String ALASAN_REVIEW 
    		) 
    {
        ContentValues initialValues = new ContentValues();
       
        
        initialValues.put(this.U_ID, U_ID);
        initialValues.put(this.ID_LEVEL, ID_LEVEL);    
        initialValues.put(this.BEGIN_DATE, BEGIN_DATE);  
        initialValues.put(this.APPROVED_DATE, APPROVED_DATE); 
        initialValues.put(this.APPROVED_BY, APPROVED_BY); 
        initialValues.put(this.ALASAN_REVIEW, ALASAN_REVIEW); 
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteAll() 
    {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }
     
    public Cursor getByIDLevel(String userID, String idLevel) 
    {
    	return db.query(DATABASE_TABLE,  new String[] {
    			U_ID , 
    	        ID_LEVEL , 
    	        BEGIN_DATE , 
    	        APPROVED_DATE ,  
    	        APPROVED_BY ,   
    	        ALASAN_REVIEW 
    	}, "U_ID = '" + userID + "' and ID_LEVEL = '" + idLevel + "'", null, null, null, null);    			
    } 
    
    public Cursor getAll() 
    {
    	return db.query(DATABASE_TABLE,  new String[] {
    			U_ID , 
    	        ID_LEVEL , 
    	        BEGIN_DATE , 
    	        APPROVED_DATE ,  
    	        APPROVED_BY ,   
    	        ALASAN_REVIEW 
    	}, null, null, null, null, null);    			
    } 
}
