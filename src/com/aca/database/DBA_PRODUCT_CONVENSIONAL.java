package com.aca.database;


import com.aca.pimp.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA_PRODUCT_CONVENSIONAL {
	public static final String PRODUCT_NAME			= "PRODUCT_NAME"; 
	public static final String CUSTOMER_NAME 		= "CUSTOMER_NAME"; 
	public static final String PRODUCT_MAIN_ID 		= "PRODUCT_MAIN_ID"; 
	public static final String _id 					= "_id"; 
	public static final String PRODUCT_MAKE 		= "PRODUCT_MAKE"; 
	public static final String PRODUCT_TYPE 		= "PRODUCT_TYPE"; 
	public static final String YEAR 				= "YEAR"; 
	public static final String NOPOL_1				= "NOPOL_1"; 
	public static final String NOPOL_2 				= "NOPOL_2"; 
	public static final String NOPOL_3 				= "NOPOL_3"; 
	public static final String COLOR 				= "COLOR"; 
	public static final String CHASSIS_NO 			= "CHASSIS_NO"; 
	public static final String MACHINE_NO 			= "MACHINE_NO"; 
	public static final String PERLENGKAPAN			= "PERLENGKAPAN"; 
	public static final String SEAT_NUMBER 			= "SEAT_NUMBER";  
	public static final String PENGGUNAAN 			= "PENGGUNAAN"; 
	public static final String JANGKA_WAKTU_EFF		= "JANGKA_WAKTU_EFF"; 
	public static final String JANGKA_WAKTU_EXP 	= "JANGKA_WAKTU_EXP"; 
	public static final String NILAI_PERTANGGUNGAN  = "NILAI_PERTANGGUNGAN"; 
	public static final String NILAI_PERTANGGUNGAN_PERLENGKAPAN = "NILAI_PERTANGGUNGAN_PERLENGKAPAN"; 
	public static final String FLOOD 				= "FLOOD"; 
	public static final String EQ 					= "EQ"; 
	public static final String SRCC					= "SRCC"; 
	public static final String TS 					= "TS"; 
	public static final String TJH 					= "TJH"; 
	public static final String PA 					= "PA"; ; 
	public static final String JENIS_RATE 			= "JENIS_RATE"; 
	public static final String TIPE_KONVENSIONAL 	= "TIPE_KONVENSIONAL";
	
	public static final String RATE 				= "RATE"; 
	public static final String PREMI 				= "PREMI"; 
	public static final String POLIS				= "POLIS"; 
	public static final String MATERAI 				= "MATERAI"; 
	public static final String TOTAL 				= "TOTAL";
	
	public static final String PRODUCT_MODEL		= "PRODUCT_MODEL"; 
	public static final String PERLENGKAPAN_TYPE	= "PERLENGKAPAN_TYPE"; 
	public static final String PRODUCT_MAKE_DESC	= "PRODUCT_MAKE_DESC"; 
	public static final String PRODUCT_TYPE_DESC 	= "PRODUCT_TYPE_DESC";

	public static final String RISIKO_SENDIRI		 = "RISIKO_SENDIRI";
	public static final String KERUSAKAN 			 = "KERUSAKAN";
	public static final String KETERANGAN_KERUSAKAN  = "KETERANGAN_KERUSAKAN";
	
	public static final String PILIHAN_LOADING  	 = "PILIHAN_LOADING";
	
	
    private static final String TAG = "DBA_PRODUCT_CONVENSIONAL";
    
    private static final String DATABASE_NAME = "AMM_VERSION_2";
    private static final String DATABASE_TABLE = "PRODUCT_CONVENSIONAL";

    static final String DATABASE_CREATE =
            "CREATE TABLE PRODUCT_CONVENSIONAL (" +
            "PRODUCT_MAIN_ID NUMERIC, " +
            "_id INTEGER PRIMARY KEY, " +
            "PRODUCT_MAKE NUMERIC," +
            " PRODUCT_TYPE NUMERIC, " +
            "YEAR NUMERIC," +
            " NOPOL_1 TEXT, NOPOL_2 TEXT, NOPOL_3 TEXT," +
            " COLOR TEXT," +
            " CHASSIS_NO TEXT, " +
            "MACHINE_NO TEXT, " +
            " PERLENGKAPAN TEXT, " +
            "SEAT_NUMBER NUMERIC, " +
            "PENGGUNAAN TEXT, " +
            "JANGKA_WAKTU_EFF TEXT, JANGKA_WAKTU_EXP TEXT," +
            " NILAI_PERTANGGUNGAN NUMERIC, NILAI_PERTANGGUNGAN_PERLENGKAPAN NUMERIC, " +
            "FLOOD TEXT, " +
            "EQ TEXT, " + 
            "SRCC TEXT, " + 
            "TS TEXT, " + 
            "TJH TEXT, " + 
            "PA TEXT, " +
            "JENIS_RATE TEXT, " +
            "TIPE_KONVENSIONAL TEXT," +
            "RATE NUMERIC, " +
            "PREMI NUMERIC," +
            " POLIS NUMERIC, " +
            "MATERAI NUMERIC, " +
            "TOTAL NUMERIC, " +
            
            "CUSTOMER_NAME TEXT, " +
            "PRODUCT_NAME TEXT, " + 
            "PRODUCT_MODEL TEXT," +
            " PERLENGKAPAN_TYPE TEXT," +
            " PRODUCT_MAKE_DESC TEXT, " +
            "PRODUCT_TYPE_DESC TEXT," +
            "RISIKO_SENDIRI NUMERIC," +
            "KERUSAKAN TEXT," +
            "KETERANGAN_KERUSAKAN TEXT," +
            "PILIHAN_LOADING TEXT" +
            ");";
    
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBA_PRODUCT_CONVENSIONAL(Context ctx) 
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        { 
        }
    }    

    //---opens the database---
    public DBA_PRODUCT_CONVENSIONAL open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    public void clearData () {
    	this.open(); 

    	String sql = "_id not in ( SELECT MAX(_id) FROM " + DATABASE_TABLE + " GROUP BY product_main_id)";
    	db.delete(DATABASE_TABLE, sql, null );
    	this.close();
    }
    
    //---insert a contact into the database---
    public long initialInsert(
			    		long product_main_id,  
			    		String make, 
			    		String type, 
			    		String model, 
			    		String year, 
			    		String nopol1, 
			    		String nopol2, 
			    		String nopol3, 
			    		String color, 
			    		String chassis_no, 
			    		String machine_no, 
			    		String perlengkapan_type, 
			    		String perlengkapan,
			    		int seat, 
			    		String penggunaan,
			    		String jangka_waktu_eff, 
			    		String jangka_waktu_exp, 
			    		double nilai_pertanggungan,
			    		double nilai_perlengkapan, 
			    		String flood, 
			    		String eq, 
			    		String srcc, 
			    		String ts,
			    		String tjh, 
			    		String pa, 
			    		String jenis_rate,  
			    		String tipeKonvensional,
			    		double rate, 
			    		double premi, 
			    		double materai, 
			    		double polis, 
			    		double total, 
			    		String make_desc, 
			    		String type_desc, 
			    		String product_name, 
			    		String customer_name,
			    		double risiko, 
			    		String kerusakan, 
			    		String keteranganKerusakan,
			    		int pilihanLoading
    		 )  
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUCT_MAIN_ID, product_main_id); 
        initialValues.put(PRODUCT_MAKE, make); 
        initialValues.put(PRODUCT_TYPE, type); 
        initialValues.put(YEAR, year); 
        initialValues.put(NOPOL_1, nopol1); 
        initialValues.put(NOPOL_2, nopol2); 
        initialValues.put(NOPOL_3, nopol3); 
        initialValues.put(COLOR, color); 
        initialValues.put(CHASSIS_NO, chassis_no); 
        initialValues.put(MACHINE_NO, machine_no); 
        initialValues.put(PERLENGKAPAN, perlengkapan); 
        initialValues.put(SEAT_NUMBER, seat); 
        initialValues.put(PENGGUNAAN, penggunaan); 
        initialValues.put(JANGKA_WAKTU_EFF, jangka_waktu_eff); 
        initialValues.put(JANGKA_WAKTU_EXP, jangka_waktu_exp);
        initialValues.put(NILAI_PERTANGGUNGAN, nilai_pertanggungan); 
        initialValues.put(NILAI_PERTANGGUNGAN_PERLENGKAPAN, nilai_perlengkapan); 
        initialValues.put(FLOOD, flood); 
        initialValues.put(EQ, eq); 
        initialValues.put(SRCC, srcc); 
        initialValues.put(TS, ts); 
        initialValues.put(TJH, tjh); 
        initialValues.put(PA, pa); 
        initialValues.put(JENIS_RATE, jenis_rate); 
        initialValues.put(TIPE_KONVENSIONAL, tipeKonvensional); 
        
        initialValues.put(RATE, rate);
        initialValues.put(PREMI, premi);
        initialValues.put(POLIS, polis);
        initialValues.put(MATERAI, materai);
        initialValues.put(TOTAL, total);
        initialValues.put(PRODUCT_NAME, "CONVENSIONAL");
        initialValues.put(CUSTOMER_NAME, customer_name);
        
        initialValues.put(PRODUCT_MODEL, model); 
        initialValues.put(PERLENGKAPAN_TYPE, perlengkapan_type); 
        initialValues.put(PRODUCT_MAKE_DESC, make_desc); 
        initialValues.put(PRODUCT_TYPE_DESC, type_desc); 


        initialValues.put(RISIKO_SENDIRI, risiko); 
        initialValues.put(KERUSAKAN, kerusakan); 
        initialValues.put(KETERANGAN_KERUSAKAN, keteranganKerusakan); 
        initialValues.put(PILIHAN_LOADING, pilihanLoading); 
 
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean nextInsert(long product_main_id,  
    		String make, 
    		String type, 
    		String model, 
    		String year, 
    		String nopol1, 
    		String nopol2, 
    		String nopol3, 
    		String color, 
    		String chassis_no, 
    		String machine_no, 
    		String perlengkapan_type, 
    		String perlengkapan,
    		int seat, 
    		String penggunaan,
    		String jangka_waktu_eff, 
    		String jangka_waktu_exp, 
    		double nilai_pertanggungan,
    		double nilai_perlengkapan, 
    		String flood, 
    		String eq, 
    		String srcc, 
    		String ts,
    		String tjh, 
    		String pa, 
    		String jenis_rate,  
    		String tipeKonvensional,
    		double rate, 
    		double premi, 
    		double materai, 
    		double polis, 
    		double total, 
    		String make_desc, 
    		String type_desc, 
    		String product_name, 
    		String customer_name,
    		double risiko, 
    		String kerusakan, 
    		String keteranganKerusakan,
    		int pilihanLoading )  
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUCT_MAKE, make); 
        initialValues.put(PRODUCT_TYPE, type); 
        initialValues.put(YEAR, year); 
        initialValues.put(NOPOL_1, nopol1); 
        initialValues.put(NOPOL_2, nopol2); 
        initialValues.put(NOPOL_3, nopol3); 
        initialValues.put(COLOR, color); 
        initialValues.put(CHASSIS_NO, chassis_no); 
        initialValues.put(MACHINE_NO, machine_no); 
        initialValues.put(PERLENGKAPAN, perlengkapan); 
        initialValues.put(SEAT_NUMBER, seat); 
        initialValues.put(PENGGUNAAN, penggunaan); 
        initialValues.put(JANGKA_WAKTU_EFF, jangka_waktu_eff); 
        initialValues.put(JANGKA_WAKTU_EXP, jangka_waktu_exp);
        initialValues.put(NILAI_PERTANGGUNGAN, nilai_pertanggungan); 
        initialValues.put(NILAI_PERTANGGUNGAN_PERLENGKAPAN, nilai_perlengkapan); 
        initialValues.put(FLOOD, flood); 
        initialValues.put(EQ, eq); 
        initialValues.put(SRCC, srcc); 
        initialValues.put(TS, ts); 
        initialValues.put(TJH, tjh); 
        initialValues.put(PA, pa); 
        initialValues.put(JENIS_RATE, jenis_rate); 
        initialValues.put(TIPE_KONVENSIONAL, tipeKonvensional); 
        
        initialValues.put(RATE, rate);
        initialValues.put(PREMI, premi);
        initialValues.put(POLIS, polis);
        initialValues.put(MATERAI, materai);
        initialValues.put(TOTAL, total);
        initialValues.put(PRODUCT_NAME, product_name);
        initialValues.put(CUSTOMER_NAME, customer_name);
        
        initialValues.put(PRODUCT_MODEL, model); 
        initialValues.put(PERLENGKAPAN_TYPE, perlengkapan_type);
        initialValues.put(PRODUCT_MAKE_DESC, make_desc); 
        initialValues.put(PRODUCT_TYPE_DESC, type_desc);  
        

        initialValues.put(RISIKO_SENDIRI, risiko); 
        initialValues.put(KERUSAKAN, kerusakan); 
        initialValues.put(KETERANGAN_KERUSAKAN, keteranganKerusakan); 
        initialValues.put(PILIHAN_LOADING, pilihanLoading); 
        
        return db.update(DATABASE_TABLE, initialValues, PRODUCT_MAIN_ID + "=" + product_main_id, null) > 0;
    }

    //---deletes a particular contact---
    public boolean delete(long product_main_id) 
    {
        return db.delete(DATABASE_TABLE, PRODUCT_MAIN_ID + "=" + product_main_id, null) > 0;
    }
    
    public boolean deleteAll() 
    {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getRows() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		PRODUCT_MAIN_ID , 					//0
        		_id , 								//1
        		PRODUCT_MAKE , 					//2
        		PRODUCT_TYPE , 					//3
        		YEAR , 							//4
        		NOPOL_1 , 						//5
        		NOPOL_2 , 						//6
        		NOPOL_3 , 						//7
        		COLOR , 						//8
        		CHASSIS_NO , 					//9
        		MACHINE_NO , 					//10
        		PERLENGKAPAN ,					//11
        		SEAT_NUMBER , 					//12
        		JANGKA_WAKTU_EFF ,				//13
        		JANGKA_WAKTU_EXP ,				//14
        		NILAI_PERTANGGUNGAN ,			//15
        		NILAI_PERTANGGUNGAN_PERLENGKAPAN,//16
        		FLOOD ,							//17
        		EQ, 							//18
        		SRCC,							//19
        		TS,								//20
        		TJH,							//21
        		PA,								//22
        		JENIS_RATE,						//23
        		TIPE_KONVENSIONAL,				//24
        		RATE , 							//25
        		PREMI ,							//26
        		POLIS ,							//27
        		MATERAI ,						//28
        		TOTAL,							//29
        		PRODUCT_NAME,					//30
        		CUSTOMER_NAME,					//31
        		PRODUCT_MODEL,					//32
        		PERLENGKAPAN_TYPE,				//33
        		PRODUCT_MAKE_DESC , 			//34
        		PRODUCT_TYPE_DESC,				//35
        		PENGGUNAAN,						//36
        		RISIKO_SENDIRI,					//37
        		KERUSAKAN,						//38
        		KETERANGAN_KERUSAKAN,			//39
        		PILIHAN_LOADING					//40

        		}, null, null, null, null, null);
    }
    
    public Cursor getRow(long product_main_id) 
    {
    	return db.query(DATABASE_TABLE, new String[] {
    			PRODUCT_MAIN_ID , 					//0
        		_id , 								//1
        		PRODUCT_MAKE , 					//2
        		PRODUCT_TYPE , 					//3
        		YEAR , 							//4
        		NOPOL_1 , 						//5
        		NOPOL_2 , 						//6
        		NOPOL_3 , 						//7
        		COLOR , 						//8
        		CHASSIS_NO , 					//9
        		MACHINE_NO , 					//10
        		PERLENGKAPAN ,					//11
        		SEAT_NUMBER , 					//12
        		JANGKA_WAKTU_EFF ,				//13
        		JANGKA_WAKTU_EXP ,				//14
        		NILAI_PERTANGGUNGAN ,			//15
        		NILAI_PERTANGGUNGAN_PERLENGKAPAN,//16
        		FLOOD ,							//17
        		EQ, 							//18
        		SRCC,							//19
        		TS,								//20
        		TJH,							//21
        		PA,								//22
        		JENIS_RATE,						//23
        		TIPE_KONVENSIONAL,				//24
        		RATE , 							//25
        		PREMI ,							//26
        		POLIS ,							//27
        		MATERAI ,						//28
        		TOTAL,							//29
        		PRODUCT_NAME,					//30
        		CUSTOMER_NAME,					//31
        		PRODUCT_MODEL,					//32
        		PERLENGKAPAN_TYPE,				//33
        		PRODUCT_MAKE_DESC , 			//34
        		PRODUCT_TYPE_DESC,				//35
        		PENGGUNAAN,						//36
        		RISIKO_SENDIRI,					//37
        		KERUSAKAN,						//38
        		KETERANGAN_KERUSAKAN,			//39
        		PILIHAN_LOADING					//40
    			}, 
        		PRODUCT_MAIN_ID + "=" + product_main_id, null, null, null, null);
    }
}

