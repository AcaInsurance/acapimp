package com.aca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.aca.pimp.Utility;

import java.text.NumberFormat;
import java.text.ParseException;

public class DBA_MASTER_OTOMATE_RATE {

    public static final String KATEGORI = "KATEGORI";
    public static final String JNP = "JNP";
    public static final String WILAYAH = "WILAYAH";
    public static final String RATE = "RATE";
    public static final String KODE_PRODUK = "KODE_PRODUK";
    static final String DATABASE_CREATE =
            "CREATE TABLE MASTER_OTOMATE_RATE (KATEGORI TEXT," +
                    " JNP TEXT, " +
                    "WILAYAH TEXT, " +
                    "KODE_PRODUK TEXT, " +
                    "RATE NUMERIC);";
    private static final String TAG = "DBA_MASTER_OTOMATE_RATE";
    private static final String DATABASE_NAME = "AMM_VERSION_BIG";
    private static final String DATABASE_TABLE = "MASTER_OTOMATE_RATE";
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBA_MASTER_OTOMATE_RATE(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    //---opens the database---
    public DBA_MASTER_OTOMATE_RATE open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insert(String kategori,
                       String jnp,
                       String wilayah,
                       double rate,
                       String kodeProduct) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATEGORI, kategori);
        initialValues.put(JNP, jnp);
        initialValues.put(WILAYAH, wilayah);
        initialValues.put(RATE, rate);
        initialValues.put(KODE_PRODUK, kodeProduct);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteAll() {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    public Cursor getAll() {
        return db.query(DATABASE_TABLE, new String[]{KATEGORI, JNP, WILAYAH, RATE, KODE_PRODUK}, null, null, null, null, null);
    }

    public Cursor getRate(String TSI, String Wilayah, String kodeProduct) throws ParseException {
        NumberFormat nf;
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);


        String whereClause = String.format(" where wilayah = '%s' " +
                "and cast(jnp as integer) >= %s " +
                "and KODE_PRODUK = '%s' ", Wilayah, String.valueOf(nf.parse(TSI).doubleValue()), kodeProduct);

        String sql = "SELECT KATEGORI, JNP, RATE FROM MASTER_OTOMATE_RATE" +
                whereClause + " LIMIT 1;";
        return db.rawQuery(sql, null);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, Utility.getDatabaseVersionBig());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
        	try {
        		db.execSQL(DATABASE_CREATE);
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            //        + newVersion + ", which will destroy all old data");
            //db.execSQL("DROP TABLE IF EXISTS MASTER_CAR_BRAND");
            //onCreate(db);
        }
    }
}
