package com.aca.database;

import com.aca.pimp.Utility;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA_MASTER_AGENT {
	public static final String BRANCH_ID = "BRANCH_ID";  
	public static final String USER_ID = "USER_ID"; 
	public static final String OFFICE_ID  = "OFFICE_ID";
	public static final String MKT_CODE  = "MKT_CODE";
	public static final String SIGN_PLACE  = "SIGN_PLACE";
	public static final String POPUP = "POPUP";
	public static final String MAX_ROW = "MAX_ROW";
	public static final String STATUS = "STATUS";
	
	public static final String STATUS_USER = "STATUS_USER";
	public static final String USER_EXP_DATE = "USER_EXP_DATE";
	public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
	public static final String PHONE_NO = "PHONE_NO";
	public static final String USER_NAME = "USER_NAME";
	public static final String U_NAME = "U_NAME";
	public static final String U_PASS = "U_PASS";
	
	public static final String ID_LEADER = "ID_LEADER";
	public static final String ID_LEVEL = "ID_LEVEL";
	public static final String IS_ACHIEVED = "IS_ACHIEVED";
	public static final String STATUS_LEVEL = "IS_ACHIEVED";
	public static final String ID_ROLE = "ID_ROLE"; 
	
	public static final String LIMIT_ASRI = "LIMIT_ASRI";
	public static final String LIMIT_OTOMATE = "LIMIT_OTOMATE";
	public static final String LIMIT_MOTORCAR = "LIMIT_MOTORCAR";
	public static final String LIMIT_TS_DOM = "LIMIT_TS_DOM";
	public static final String LIMIT_TS_INT = "LIMIT_TS_INT";
	public static final String LIMIT_MEDISAFE = "LIMIT_MEDISAFE";
	public static final String LIMIT_WELLWOMAN = "LIMIT_WELLWOMAN";
	public static final String LIMIT_DNO = "LIMIT_DNO";
	public static final String LIMIT_MARINE_CARGO = "LIMIT_MARINE_CARGO";
	public static final String LIMIT_OTOMATE_SYARIAH = "LIMIT_OTOMATE_SYARIAH";
	public static final String LIMIT_ASRI_SYARIAH = "LIMIT_ASRI_SYARIAH";
	public static final String LIMIT_PA_AMANAH = "LIMIT_PA_AMANAH";
	
	private static final String DATABASE_NAME = "AMM_VERSION_2";
	private static final String DATABASE_TABLE = "MASTER_AGENT";

    static final String DATABASE_CREATE =
        "CREATE TABLE MASTER_AGENT (BRANCH_ID TEXT," +
							        "USER_ID TEXT," +
							        "OFFICE_ID TEXT," +
							        "MKT_CODE TEXT," +
							        "SIGN_PLACE TEXT, " +
							        "POPUP TEXT, " +
							        "MAX_ROW NUMERIC," +
							        "STATUS TEXT, " +
							        "STATUS_USER TEXT, " +
							        "USER_EXP_DATE TEXT, " +
							        "EMAIL_ADDRESS TEXT, " +
							        "PHONE_NO TEXT, " +
							        "USER_NAME TEXT, " +
							        "U_NAME TEXT, " +
							        "U_PASS TEXT, " +
							        "ID_LEADER TEXT, " +
							        "ID_LEVEL TEXT, " +
							        "IS_ACHIEVED TEXT, " +
							        "STATUS_LEVEL TEXT, " +
							        "ID_ROLE TEXT," +
							        "LIMIT_ASRI TEXT," +
					        	      "LIMIT_OTOMATE TEXT," +
					        	      "LIMIT_MOTORCAR TEXT," +
					        	      "LIMIT_TS_DOM TEXT," +
					        	      "LIMIT_TS_INT TEXT," +
					        	      "LIMIT_MEDISAFE TEXT," +
					        	      "LIMIT_WELLWOMAN TEXT," +
					        	      "LIMIT_DNO TEXT," +
					        	      "LIMIT_MARINE_CARGO TEXT," +
					        	      "LIMIT_OTOMATE_SYARIAH TEXT," +
					        	      "LIMIT_ASRI_SYARIAH TEXT," +
					        	      "LIMIT_PA_AMANAH TEXT" +
							        ");";
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBA_MASTER_AGENT(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, Utility.getDatabaseVersion());
        }
        

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
//        	try {
//        		db.execSQL(DATABASE_CREATE);	
//        	} catch (SQLException e) {
//        		e.printStackTrace();
//        	}
        }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
//			
			
		}
    }    

    //---opens the database---
    public DBA_MASTER_AGENT open() throws SQLException 
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
    		String branchId, 
    		String userId, 
    		String signPlace, 
    		String mktCode, 
    		String officeId, 
    		String MaxRow,
    		String statusUser,
    		String userExpDate,
    		String email,
    		String phoneNo,
    		String userName,
    		String uName, 
    		String uPass,
    		String idLeader,
    		String idLevel,
    		String isAchieved,
    		String statusLevel,
    		String idRole,
    		String limit_asri,
    		String limit_otomate,
    		String limit_motorcar,
    		String limit_ts_dom,
    		String limit_ts_int,
    		String limit_medisafe,
    		String limit_wellwoman,
    		String limit_dno,
    		String limit_marine_cargo,
    		String limit_otomate_syariah,
    		String limit_asri_syariah,
    		String limit_pa_amanah
    		) 
    {
    	 ContentValues initialValues = new ContentValues();
         initialValues.put(BRANCH_ID, branchId);
         initialValues.put(USER_ID, userId);
         initialValues.put(SIGN_PLACE, signPlace);
         initialValues.put(MKT_CODE, mktCode);
         initialValues.put(OFFICE_ID, officeId);
         initialValues.put(POPUP, "0");
         initialValues.put(MAX_ROW, MaxRow);
         initialValues.put(STATUS, "LOGIN");
         
         initialValues.put(STATUS_USER, statusUser );
         initialValues.put(USER_EXP_DATE, userExpDate );
         initialValues.put(EMAIL_ADDRESS, email);
         initialValues.put(PHONE_NO, phoneNo);
         initialValues.put(USER_NAME, userName);
         initialValues.put(U_NAME, uName);
         initialValues.put(U_PASS, uPass);
         
         initialValues.put(ID_LEADER, idLeader);
         initialValues.put(ID_LEVEL, idLevel);
         initialValues.put(IS_ACHIEVED, isAchieved);
         initialValues.put(STATUS_LEVEL, statusLevel);
         initialValues.put(ID_ROLE, idRole); 
         initialValues.put(LIMIT_ASRI, limit_asri);
	     initialValues.put(LIMIT_OTOMATE, limit_otomate);
	     initialValues.put(LIMIT_MOTORCAR, limit_motorcar);
	     initialValues.put(LIMIT_TS_DOM, limit_ts_dom);
	     initialValues.put(LIMIT_TS_INT, limit_ts_int);
	     initialValues.put(LIMIT_MEDISAFE, limit_medisafe);
	     initialValues.put(LIMIT_WELLWOMAN, limit_wellwoman);
	     initialValues.put(LIMIT_DNO, limit_dno);
	     initialValues.put(LIMIT_MARINE_CARGO, limit_marine_cargo);
	     initialValues.put(LIMIT_OTOMATE_SYARIAH, limit_otomate_syariah);
	     initialValues.put(LIMIT_ASRI_SYARIAH, limit_asri_syariah);
	     initialValues.put(LIMIT_PA_AMANAH, limit_pa_amanah);
          
         
         return db.insert(DATABASE_TABLE, null, initialValues);
    }
    
        
    
    public boolean updatePopup ()
    {
    	 ContentValues initialValues = new ContentValues();
         initialValues.put(POPUP, "1");
         
         return db.update(DATABASE_TABLE, initialValues, null, null) > 0;
    }

    public boolean updateStatusLogin () 
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put("STATUS", "LOGIN");
    	
    	return db.update(DATABASE_TABLE, initialValues, null, null) > 0 ;
    }
    
    public boolean updateStatusLogout () 
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put("STATUS", "LOGOUT");
    	
    	return db.update(DATABASE_TABLE, initialValues, null, null) > 0 ;
    }
    
    public Cursor getStatusLogin () {
    	return db.query(DATABASE_TABLE, new String[] {STATUS}, 
    			null, null, null, null, null);
    }
    
    public boolean delete(String userId) 
    {
        return db.delete(DATABASE_TABLE, USER_ID + "=" + userId, null) > 0;
    }
    
    public boolean deleteAll() 
    {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    //---retrieves all the contacts---
   
    public Cursor getRow() 
    {
    	return db.query(DATABASE_TABLE, new String[] {
    			BRANCH_ID,  //0
        		USER_ID,	//1
        		SIGN_PLACE,	//2
        		MKT_CODE,	//3
        		OFFICE_ID,	//4
        		POPUP,		//5
        		MAX_ROW,	//6
        		STATUS, 	//7
        		STATUS_USER, 	//8
        		USER_EXP_DATE, 	//9
        		EMAIL_ADDRESS, 	//10
        		PHONE_NO, 	//11
        		USER_NAME, 	//12
        		U_NAME, 		//13
        		ID_LEADER,  //14
		        ID_LEVEL,   //15
		        IS_ACHIEVED, //16
		        STATUS_LEVEL,  //17
		        ID_ROLE, 		//18
		        LIMIT_OTOMATE,			//19
				LIMIT_MOTORCAR,			//20
				LIMIT_TS_DOM,			//21
				LIMIT_TS_INT,			//22
				LIMIT_MEDISAFE,			//23
				LIMIT_WELLWOMAN,		//24
				LIMIT_DNO,				//25
				LIMIT_MARINE_CARGO,		//26
				LIMIT_OTOMATE_SYARIAH,	//27
				LIMIT_ASRI_SYARIAH,		//28
				LIMIT_PA_AMANAH,		//29
				LIMIT_ASRI				//30
        		
        		}, 	
    			null, null, null, null, null);
    }
    public int getExistingUser (String loginID) {
    	return db.query(DATABASE_TABLE, 
    			new String[] {USER_ID}, 
    			USER_ID 		+ "='" + loginID  + "' OR " +
    			EMAIL_ADDRESS 	+ "='" + loginID  + "' OR " +
    			USER_NAME 		+ "='" + loginID  + "'"
    			,	null, null, null, null, null).getCount();
    }
    

    public Cursor getRowByAgent(String userID) 
    {
    	return db.query(DATABASE_TABLE, new String[] {
        		USER_ID,	//0
        		STATUS}, 	//1
    			USER_ID + "='" + userID + "'" , null, null, null, null);
    }
    
    public int login (String userID, String hashPassword)  
    {
    	Cursor cursor = db.query(
			    			 DATABASE_TABLE
			    			,new String[] {USER_ID}
			    			,USER_ID + "='" + userID + "' AND " +
			    			 U_PASS  + "='" + hashPassword + "'"
			    			,null, null, null, null, null);
    	
    	return cursor.getCount();
    }
    
    public boolean updateUsername(String newUsername) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(USER_NAME, newUsername);
    	
    	return db.update(DATABASE_TABLE, initialValues, null, null) > 0 ;
	}
    
    public boolean updatePassword (String newPassword) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(U_PASS, newPassword);
    	
    	return db.update(DATABASE_TABLE, initialValues, null, null) > 0 ;
	}
    
    
    public int getCount (){
    	Cursor cursor = db.rawQuery("select count(USER_ID) FROM MASTER_AGENT", null);
    	Log.d("curosr count", String.valueOf(cursor.getInt(0)));
    	return cursor.getInt(0);
    }
}

